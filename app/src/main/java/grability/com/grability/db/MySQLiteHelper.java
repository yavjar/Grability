package grability.com.grability.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grability.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Content table
     */
    public static final String CONTENT_TABLE_NAME = "contents";
    public static final String CONTENT_COLUMN_ID = "_id";
    public static final String CONTENT_NAME = "name";
    public static final String CONTENT_SUMMARY = "summary";
    public static final String CONTENT_PRICE = "price";
    public static final String CONTENT_ID = "id";
    public static final String CONTENT_CATEGORY_LABEL = "category_label";
    public static final String CONTENT_RELEASE_DATE_LABEL = "release_date";

    // CREATE query for Content table
    private static final String CONTENT_TABLE_CREATE = "create table "
            + CONTENT_TABLE_NAME + "("
            + CONTENT_COLUMN_ID + " integer primary key autoincrement, "
            + CONTENT_NAME + " text Default '', "
            + CONTENT_SUMMARY + " text Default '', "
            + CONTENT_PRICE + " text Default '', "
            + CONTENT_ID + " integer Default 0, "
            + CONTENT_CATEGORY_LABEL + " text Default '', "
            + CONTENT_RELEASE_DATE_LABEL + " text Default '' "
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbManipulating(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + CONTENT_TABLE_NAME);
            dbManipulating(db);

        }
    }

    public void dbManipulating(SQLiteDatabase database) {
        database.execSQL(CONTENT_TABLE_CREATE);
    }
}
