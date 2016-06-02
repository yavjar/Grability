package grability.com.grability;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import grability.com.grability.UI.CustomGridAdapter;
import grability.com.grability.UI.CustomListAdapter;
import grability.com.grability.entities.ContentEntity;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    LinearLayout loadLinearLayout;
    private Animation rotateAnimation;
    private ImageView rotatingImageView;
    Button categoryButton;
    Button searchButton;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterAutocomplete;
    CustomListAdapter customListAdapter;
    ListView listView;
    GridView mGridView;

    //    public MainThread mainThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (MonoleticApp.screenType == MonoleticApp.ScreenTypes.LARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;

        //Layout orientation SmartPhones: Portrait; Tablets: Landscape

        MainApplication.dataSource.open();

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_main);
        adapterAutocomplete = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        fillAutoComplete();

        listView = (ListView) findViewById(R.id.list_view);
        mGridView = (GridView) findViewById(R.id.grid);
        searchButton = (Button) findViewById(R.id.autocomplete_search_btn);

        CustomGridAdapter adapter = new CustomGridAdapter(MainActivity.this, MonoleticApp.contentEntityArrayList);
        customListAdapter = new CustomListAdapter(MainActivity.this, MonoleticApp.contentEntityArrayList);


        if (MonoleticApp.screenType == MonoleticApp.ScreenTypes.LARGE) {
            //Grid//

            mGridView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

            mGridView.setAdapter(adapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    dialogMaker(position);

                }
            });
        } else if (MonoleticApp.screenType == MonoleticApp.ScreenTypes.NORMAL || MonoleticApp.screenType == MonoleticApp.ScreenTypes.SMALL) {
            mGridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);


            listView.setAdapter(customListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    dialogMaker(position);
                }
            });
        }


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTempContent();
                hideKeyboard();
                if (MonoleticApp.screenType == MonoleticApp.ScreenTypes.LARGE) {
                    CustomGridAdapter adapterTemp = new CustomGridAdapter(MainActivity.this, MonoleticApp.tempContentEntityArrayList);
                    mGridView.setAdapter(adapterTemp);
                    mGridView.deferNotifyDataSetChanged();
                } else {
                    //reload content

                    customListAdapter = new CustomListAdapter(MainActivity.this, MonoleticApp.tempContentEntityArrayList);
                    listView.setAdapter(customListAdapter);
                    listView.deferNotifyDataSetChanged();
                }
            }
        });


        categoryButton = (Button) findViewById(R.id.button_category);
        categoryButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  final Dialog dialog = new Dialog(context);
                                                  dialog.setContentView(R.layout.dialog);
                                                  dialog.setTitle(getString(R.string.dialog_categories));
                                                  dialog.getWindow().getAttributes().windowAnimations = R.style.HyperspaceAnimation;

                                                  // set the custom dialog components - text, image and button
                                                  TextView textDialog = (TextView) dialog.findViewById(R.id.custom_dialog_text);
                                                  textDialog.setText(MonoleticApp.categoryList.toString());

                                                  Button dialogButton = (Button) dialog.findViewById(R.id.custom_dialog_button);
                                                  // if button is clicked, close the custom dialog
                                                  dialogButton.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          dialog.dismiss();
                                                      }
                                                  });

                                                  dialog.show();

                                              }
                                          }
        );

    }
    public void fillAutoComplete(){
        adapterAutocomplete.clear();
        for (String category : MonoleticApp.categoryList) {
            adapterAutocomplete.add(category);
        }
        autoCompleteTextView.setAdapter(adapterAutocomplete);
    }
    public void fillTempContent() {
        MonoleticApp.tempContentEntityArrayList.clear();
        adapterAutocomplete.clear();
        for (String category : MonoleticApp.categoryList) {
            adapterAutocomplete.add(category);
        }
        for (ContentEntity content : MonoleticApp.contentEntityArrayList) {
            if (content.getCategory().getLabel().equals(autoCompleteTextView.getText().toString())) {
                MonoleticApp.tempContentEntityArrayList.add(content);
            }
        }
    }

    public void dialogMaker(int position) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle(getString(R.string.dialog_detail));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        // set the custom dialog components - text, image and button
        TextView textDialog = (TextView) dialog.findViewById(R.id.custom_dialog_text);
        textDialog.setText(MonoleticApp.contentEntityArrayList.get(position).toString());

        Button dialogButton = (Button) dialog.findViewById(R.id.custom_dialog_button);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
