package grability.com.grability;

import android.content.Context;
import android.os.Environment;

import java.util.ArrayList;

import grability.com.grability.db.DataSource;
import grability.com.grability.entities.ContentEntity;

/**
 * Created by Salsa on 28-May-16.
 */
public class MonoleticApp {
    private static MonoleticApp instance;
    public static ArrayList<ContentEntity> contentEntityArrayList;
    public static ArrayList<ContentEntity> tempContentEntityArrayList;
    public enum ScreenTypes{LARGE,NORMAL,SMALL};
    public static ScreenTypes screenType;
    public static String imagesPath= Environment.getExternalStorageDirectory() + "/" + "appData/grability/images/";
    public static String imagesFolderList[] = {"/im53/", "/im75/", "/im100/"};
    public static ArrayList<String> categoryList;

    private MonoleticApp() {

    }
    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new MonoleticApp();
        }
        contentEntityArrayList= new ArrayList<ContentEntity>();
        tempContentEntityArrayList = new ArrayList<ContentEntity>();
        categoryList = new ArrayList<String>();

    }
        public static MonoleticApp getInstance() {
        return instance;
    }
}
