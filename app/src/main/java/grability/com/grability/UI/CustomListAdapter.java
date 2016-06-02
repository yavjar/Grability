package grability.com.grability.UI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import grability.com.grability.DownloadContentImages;
import grability.com.grability.MainActivity;
import grability.com.grability.MonoleticApp;
import grability.com.grability.R;
import grability.com.grability.entities.ContentEntity;

/**
 * Created by Salsa on 01-Jun-16.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity mContext;
    private final ArrayList<String> text;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    public static ArrayList<ContentEntity> tempContentList;

    public CustomListAdapter(Activity context,ArrayList<ContentEntity> contentList) {
        super(context, R.layout.list_single);
        // TODO Auto-generated constructor stub

        this.mContext=context;
        text = new ArrayList<>();
        String folder = MonoleticApp.imagesFolderList[0];
        BitmapFactory.Options options;
        Bitmap bitmap;

        tempContentList = contentList;

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

        for (ContentEntity contentEntity : tempContentList) {
            text.add(contentEntity.getImName());
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(MonoleticApp.imagesPath + folder + contentEntity.getId().getImId() + ".png",
                    options);
            images.add(bitmap);
        }
    }

    public static void setTempContentList(ArrayList<ContentEntity> tempContentList) {
        CustomListAdapter.tempContentList = tempContentList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return text.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return text.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View list;

        if (convertView == null) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        list=inflater.inflate(R.layout.list_single, null,true);

        } else {
            list = (View) convertView;
        }

        TextView txtTitle = (TextView) list.findViewById(R.id.list_item_name);
        ImageView imageView = (ImageView) list.findViewById(R.id.list_icon);
        txtTitle.setText(text.get(position));
        imageView.setImageBitmap(images.get(position));

        Animation animation = AnimationUtils.loadAnimation(MainActivity.context, R.anim.slide_top_to_bottom);
        animation.setDuration(1000);
        list.startAnimation(animation);
        animation = null;

        return list;
    };
}