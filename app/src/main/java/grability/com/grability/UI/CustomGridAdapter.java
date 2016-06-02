package grability.com.grability.UI;

/**
 * Created by Salsa on 30-May-16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import grability.com.grability.DownloadContentImages;
import grability.com.grability.MainActivity;
import grability.com.grability.MonoleticApp;
import grability.com.grability.R;
import grability.com.grability.entities.ContentEntity;

public class CustomGridAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> text;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    public static ArrayList<ContentEntity> tempContentList;

    BitmapFactory.Options options;
    Bitmap bitmap;

    public CustomGridAdapter(Context c , ArrayList<ContentEntity> contentList) {
        mContext = c;
        text = new ArrayList<>();
        String folder = MonoleticApp.imagesFolderList[0];

        switch (MonoleticApp.screenType) {
            case SMALL: {
                folder = MonoleticApp.imagesFolderList[0];
                break;
            }
            case NORMAL: {
                folder = MonoleticApp.imagesFolderList[1];
                break;
            }
            case LARGE: {
                folder = MonoleticApp.imagesFolderList[2];
                break;
            }
        }
        tempContentList = contentList;

        for (ContentEntity contentEntity : tempContentList) {
            text.add(contentEntity.getImName());
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(MonoleticApp.imagesPath + folder + contentEntity.getId().getImId() + ".png",
                    options);
            images.add(bitmap);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return text.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            grid = (View) convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(MainActivity.context, R.anim.slide_landscape);

        grid.setPadding(3, 5, 3, 5);
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        textView.setText(text.get(position));

        ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
        imageView.setImageBitmap(images.get(position));

        animation.setDuration(3000);
        grid.startAnimation(animation);
        animation = null;
        return grid;
    }
}
