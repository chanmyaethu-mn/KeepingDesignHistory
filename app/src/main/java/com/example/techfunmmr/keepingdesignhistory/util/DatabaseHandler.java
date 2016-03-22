package com.example.techfunmmr.keepingdesignhistory.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.techfunmmr.keepingdesignhistory.constant.QueryConstant;
import com.example.techfunmmr.keepingdesignhistory.constant.TableInfoConstant;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DesignHistoryDb";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String createTableQuery : QueryConstant.CREATE_TABLE_QUERY) {
            db.execSQL(createTableQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String dropTableName : TableInfoConstant.DROP_TABLE_NAMES) {
            db.execSQL(QueryConstant.DROP_TABLE_PREFIX + dropTableName);
        }
    }

    public SQLiteDatabase getWriteDatabase() {
        return this.getWritableDatabase();
    }

    public SQLiteDatabase getReadDatabase() {
        return this.getReadableDatabase();
    }
}
