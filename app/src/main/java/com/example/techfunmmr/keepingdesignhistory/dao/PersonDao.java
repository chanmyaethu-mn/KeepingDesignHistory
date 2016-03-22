package com.example.techfunmmr.keepingdesignhistory.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.util.DatabaseHandler;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public interface PersonDao {
    long addPerson(ContentValues values);

    int updatePerson(ContentValues values);

    int deletePerson(String personId);

    Cursor getPersonList();

    Cursor getPerson(int personId);
}
