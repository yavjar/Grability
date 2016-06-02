package grability.com.grability.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;

import grability.com.grability.entities.ContentEntity;
import grability.com.grability.entities.contentInfo.CategoryEntity;
import grability.com.grability.entities.contentInfo.IdEntity;
import grability.com.grability.entities.contentInfo.ImReleaseDateEntity;
import grability.com.grability.entities.contentInfo.PriceEntity;


/**
 * Created by Salsa on 29-May-16.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] contentAllColumns = { MySQLiteHelper.CONTENT_COLUMN_ID, MySQLiteHelper.CONTENT_NAME, MySQLiteHelper.CONTENT_SUMMARY, MySQLiteHelper.CONTENT_PRICE,
            MySQLiteHelper.CONTENT_ID, MySQLiteHelper.CONTENT_CATEGORY_LABEL, MySQLiteHelper.CONTENT_RELEASE_DATE_LABEL};
    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    ArrayList<ContentEntity> contentList;
    public ArrayList<ContentEntity> getAllContent() {

        contentList = new ArrayList<ContentEntity>();

        Cursor cursor = database.query(MySQLiteHelper.CONTENT_TABLE_NAME,
                contentAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContentEntity newRow = cursorToContent(cursor);

            contentList.add(newRow);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return contentList;
    }

    public void insertContent(ContentEntity contentEntity) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CONTENT_NAME, contentEntity.getImName());
        values.put(MySQLiteHelper.CONTENT_SUMMARY, contentEntity.getSummary());
        values.put(MySQLiteHelper.CONTENT_PRICE, String.valueOf(contentEntity.getImPrice().getAmount()));
        values.put(MySQLiteHelper.CONTENT_ID, contentEntity.getId().getImId());
        values.put(MySQLiteHelper.CONTENT_CATEGORY_LABEL, contentEntity.getCategory().getLabel());
        values.put(MySQLiteHelper.CONTENT_RELEASE_DATE_LABEL, contentEntity.getImReleaseDate().getLabel());
        database.insert(MySQLiteHelper.CONTENT_TABLE_NAME, null, values);
    }

    private ContentEntity cursorToContent(Cursor cursor) {
        ContentEntity content = new ContentEntity();
        content.setImName(cursor.getString(1));
        content.setSummary(cursor.getString(2));
        //TODO try catch
        PriceEntity priceEntity = new PriceEntity(" ", BigDecimal.valueOf(cursor.getDouble(3))," ");
        content.setImPrice(priceEntity);
        IdEntity idEntity = new IdEntity(" ",cursor.getInt(4) ," ");
        content.setId(idEntity);
        CategoryEntity categoryEntity = new CategoryEntity(0,"","",cursor.getString(5));
        content.setCategory(categoryEntity);
        ImReleaseDateEntity imReleaseDateEntity = new ImReleaseDateEntity(cursor.getString(6),"");
        content.getImReleaseDate();
        return content;
    }

    public void deleteAllContent() {
        database.delete(MySQLiteHelper.CONTENT_TABLE_NAME, null, null);
    }

}
