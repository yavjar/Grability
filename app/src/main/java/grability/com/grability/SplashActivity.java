package grability.com.grability;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import grability.com.grability.db.DataSource;
import grability.com.grability.entities.ContentEntity;
import grability.com.grability.entities.contentInfo.AuthorEntity;
import grability.com.grability.entities.contentInfo.CategoryEntity;
import grability.com.grability.entities.contentInfo.IdEntity;
import grability.com.grability.entities.contentInfo.ImArtistEntity;
import grability.com.grability.entities.contentInfo.ImReleaseDateEntity;
import grability.com.grability.entities.contentInfo.ImageEntity;
import grability.com.grability.entities.contentInfo.LinkEntity;
import grability.com.grability.entities.contentInfo.PriceEntity;

public class SplashActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 10;
    private String stringUrl = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    DownloadContentImages downloadContentImages;
    public static boolean running = true; // asynctask still running

    public static Context contextSplash;
    RelativeLayout spalshLayout;
    public ImageView rotatingImage;
    public Animation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        contextSplash = this;
        manageLayoutOrientation();
        spalshLayout = (RelativeLayout) findViewById(R.id.splash_layout);
        rotatingImage = (ImageView) findViewById(R.id.rotating_image);
        rotateAnimation = AnimationUtils.loadAnimation(contextSplash,
                R.anim.rotate);

        if (MonoleticApp.screenType == MonoleticApp.ScreenTypes.LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            spalshLayout.setBackground(getResources().getDrawable(R.drawable.rappi_screen480x480));
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            spalshLayout.setBackground(getResources().getDrawable(R.drawable.rappi));
        }

        MainApplication.dataSource.open();

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadJsonTask().execute(stringUrl);
        } else {
            MonoleticApp.contentEntityArrayList = MainApplication.dataSource.getAllContent();
            SPLASH_TIME_OUT = 2000;
            Toast.makeText(contextSplash, getText(R.string.web_absent), Toast.LENGTH_SHORT).show();

            for (ContentEntity contentEntity : MonoleticApp.contentEntityArrayList) {
                if (!MonoleticApp.categoryList.contains(contentEntity.getCategory().getLabel())) {
                    MonoleticApp.categoryList.add(contentEntity.getCategory().getLabel());
                }
            }

            runNewActivity();
        }
    }

    private void manageLayoutOrientation() {
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            MonoleticApp.screenType = MonoleticApp.ScreenTypes.LARGE;
        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            MonoleticApp.screenType = MonoleticApp.ScreenTypes.NORMAL;
        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            MonoleticApp.screenType = MonoleticApp.ScreenTypes.SMALL;
        } else {
//            Toast.makeText(this, "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();
        }
    }

    private class DownloadJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rotatingImage.setVisibility(View.VISIBLE);
            rotatingImage.startAnimation(rotateAnimation);
        }

        @Override
        protected void onCancelled() {
            running = false;
        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            return JsonAPI.getHtmlContent(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (running) {
                parseResponse(result);
                rotatingImage.setVisibility(View.GONE);
                new DownloadImagesTask().execute();
            } else {

            }
        }
    }

    private boolean parseResponse(String response) {
        Boolean status = true;
        JSONObject retrieveObject = null;

        if (response != null && response.length() > 0) {
            try {
                retrieveObject = new JSONObject(response);
            } catch (JSONException e) {
                Log.e("ServerApi", " retrieveObject = new JSONObject(response); ERROR");
            }
            MainApplication.dataSource.deleteAllContent();
            if (retrieveObject != null) {
                try {
                    JSONObject feed = retrieveObject.getJSONObject("feed");
                    JSONObject author = feed.getJSONObject("author");
                    String authorName = author.getString("name");
                    String authorUri = author.getString("uri");

                    AuthorEntity authorEntity = new AuthorEntity(authorName, authorUri);

                    JSONArray entryList = feed.getJSONArray("entry");
                    String imName, summary, contentTypeTerm, contentTypeLabel, rights, title;
                    JSONArray imageArray;
                    ImageEntity imageEntity;
                    PriceEntity priceEntity;
                    LinkEntity linkEntity;
                    IdEntity idEntity;
                    ImArtistEntity imArtistEntity;
                    CategoryEntity categoryEntity;
                    ImReleaseDateEntity imReleaseDateEntity;
                    ContentEntity contentEntity;
                    JSONObject tempObj;

                    for (int i = 0; i <= entryList.length() - 1; i++) {
                        JSONObject object = (JSONObject) entryList.get(i);

                        imName = object.getJSONObject("im:name").getString("label");
                        imageArray = object.getJSONArray("im:image");
                        imageEntity = new ImageEntity(((JSONObject) imageArray.get(0)).getString("label"),
                                ((JSONObject) imageArray.get(1)).getString("label"),
                                ((JSONObject) imageArray.get(2)).getString("label"));

                        summary = object.getJSONObject("summary").getString("label");

                        tempObj = object.getJSONObject("im:price");
                        priceEntity = new PriceEntity(tempObj.getString("label"),
                                BigDecimal.valueOf(tempObj.getJSONObject("attributes").getDouble("amount")),
                                tempObj.getJSONObject("attributes").getString("currency"));

                        tempObj = object.getJSONObject("im:contentType");
                        contentTypeTerm = tempObj.getJSONObject("attributes").getString("term");
                        contentTypeLabel = tempObj.getJSONObject("attributes").getString("label");

                        rights = object.getJSONObject("rights").getString("label");
                        title = object.getJSONObject("title").getString("label");

                        tempObj = object.getJSONObject("link");
                        linkEntity = new LinkEntity(tempObj.getJSONObject("attributes").getString("rel"),
                                tempObj.getJSONObject("attributes").getString("type"),
                                tempObj.getJSONObject("attributes").getString("href"));

                        tempObj = object.getJSONObject("id");
                        idEntity = new IdEntity(tempObj.getString("label"),
                                tempObj.getJSONObject("attributes").getInt("im:id"),
                                tempObj.getJSONObject("attributes").getString("im:bundleId"));

                        tempObj = object.getJSONObject("im:artist");
                        imArtistEntity = new ImArtistEntity(tempObj.getString("label"),
                                tempObj.getJSONObject("attributes").getString("href"));

                        tempObj = object.getJSONObject("category").getJSONObject("attributes");
                        categoryEntity = new CategoryEntity(tempObj.getInt("im:id"),
                                tempObj.getString("term"),
                                tempObj.getString("scheme"),
                                tempObj.getString("label"));

                        tempObj = object.getJSONObject("im:releaseDate");
                        imReleaseDateEntity = new ImReleaseDateEntity(tempObj.getString("label"),
                                tempObj.getJSONObject("attributes").getString("label"));

                        contentEntity = new ContentEntity(imName, imageEntity, summary, priceEntity, contentTypeTerm,
                                contentTypeLabel, rights, title, linkEntity, idEntity, imArtistEntity, categoryEntity, imReleaseDateEntity);

                        if (!MonoleticApp.categoryList.contains(categoryEntity.getLabel())) {
                            MonoleticApp.categoryList.add(categoryEntity.getLabel());
                        }
                        MonoleticApp.contentEntityArrayList.add(contentEntity);
                        MainApplication.dataSource.insertContent(contentEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
        return status;
    }

    private class DownloadImagesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rotatingImage.setVisibility(View.VISIBLE);
            rotatingImage.startAnimation(rotateAnimation);
        }

        @Override
        protected Void doInBackground(Void... params) {
            downloadContentImages = new DownloadContentImages();
            downloadContentImages.run();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rotatingImage.setVisibility(View.GONE);
            runNewActivity();
        }
    }

    public void runNewActivity() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}