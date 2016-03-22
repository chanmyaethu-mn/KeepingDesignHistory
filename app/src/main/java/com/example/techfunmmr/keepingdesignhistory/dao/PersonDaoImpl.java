package com.example.techfunmmr.keepingdesignhistory.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.techfunmmr.keepingdesignhistory.constant.QueryConstant;
import com.example.techfunmmr.keepingdesignhistory.constant.TableInfoConstant;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class PersonDaoImpl implements PersonDao {

    private SQLiteDatabase db;
    public PersonDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }
    @Override
    public long addPerson(ContentValues values) {
        return db.insert(TableInfoConstant.PersonTable.TABLE_NAME, null, values);
    }

    @Override
    public int updatePerson(ContentValues values) {
        String id = String.valueOf(values.get(TableInfoConstant.PersonTable.FIELD_ID));
        return  db.update(TableInfoConstant.PersonTable.TABLE_NAME, values, QueryConstant.WHERE_CLAUSE_PERSON_ID, new String[]{id});
    }

    @Override
    public int deletePerson(String personId) {
        return db.delete(TableInfoConstant.PersonTable.TABLE_NAME, QueryConstant.WHERE_CLAUSE_PERSON_ID, new String [] {personId});
    }

    @Override
    public Cursor getPersonList() {
        return db.query(true, TableInfoConstant.PersonTable.TABLE_NAME, new String[] {TableInfoConstant.PersonTable.FIELD_ID, TableInfoConstant.PersonTable.FIELD_NAME, TableInfoConstant.PersonTable.FIELD_PHONE_NO, TableInfoConstant.PersonTable.FIELD_EMAIL, TableInfoConstant.PersonTable.FIELD_ADDRESS, TableInfoConstant.PersonTable.FIELD_IMAGE}, null, null, null, null, null, null);
        //return db.rawQuery(QueryConstant.SELECT_ALL_PERSON, null);
    }

    @Override
    public Cursor getPerson(int personId) {
        String query = QueryConstant.SELECT_PERSON_WITH_ID + personId;
        return db.rawQuery(query, null);
    }
}
