package grability.com.grability;

import android.app.Application;

import grability.com.grability.db.DataSource;

/**
 * Created by tiga on 3/4/14.
 */
public class MainApplication extends Application {
    public static DataSource dataSource;
    @Override
    public void onCreate() {
        super.onCreate();
        dataSource= new DataSource(this);
        initSingleton();
    }

    protected void initSingleton() {
        MonoleticApp.initInstance(getBaseContext());
    }

}
