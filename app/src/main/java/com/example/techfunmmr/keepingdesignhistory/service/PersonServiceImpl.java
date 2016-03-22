package com.example.techfunmmr.keepingdesignhistory.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.example.techfunmmr.keepingdesignhistory.constant.CommonConstant;
import com.example.techfunmmr.keepingdesignhistory.dao.PersonDaoImpl;
import com.example.techfunmmr.keepingdesignhistory.dto.Person;
import com.example.techfunmmr.keepingdesignhistory.util.BitmapUtils;
import com.example.techfunmmr.keepingdesignhistory.util.DatabaseHandler;
import com.example.techfunmmr.keepingdesignhistory.constant.TableInfoConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public class PersonServiceImpl implements PersonService {

    private Context context;

    public PersonServiceImpl() {}

    public PersonServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public long addPerson(Person person) {

        ContentValues contentValues = getPersonContentValues(person, Boolean.FALSE);
        SQLiteDatabase db = getDBHandler().getWriteDatabase();
        long addResult =  0l;
        try {
            addResult = new PersonDaoImpl(db).addPerson(contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            //db.endTransaction();
        }
        return addResult;
    }

    @Override
    public int updatePerson(Person person) {
        ContentValues contentValues = getPersonContentValues(person, Boolean.TRUE);
        SQLiteDatabase db = getDBHandler().getWriteDatabase();
        int updateResult = 0;
        try {
            updateResult = new PersonDaoImpl(db).updatePerson(contentValues);
        } finally {
            db.close();
        }
        return updateResult;
    }

    @Override
    public int deletePerson(String personId) {
        SQLiteDatabase db = getDBHandler().getWriteDatabase();
        int deleteResult = 0;
        try {
            deleteResult = new PersonDaoImpl(db).deletePerson(personId);
        } finally {
            db.close();
        }
        return deleteResult;
    }


    @Override
    public List<Person> getPersonList() {

        List<Person> personList = new ArrayList<Person>();
        SQLiteDatabase db = getDBHandler().getWriteDatabase();
        Cursor cursor = null;
        try {
            cursor = new PersonDaoImpl(db).getPersonList();

            if (cursor.moveToFirst()) {
                do{
                    Person person = getPerson(cursor);
                    personList.add(person);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return personList;
    }

    @Override
    public Person getPersonWithId(int personId) {
        SQLiteDatabase db = getDBHandler().getWriteDatabase();
        Cursor cursor = null;
        Person person = new Person();
        try {
            cursor = new PersonDaoImpl(db).getPerson(personId);
            if (cursor != null) {
                cursor.moveToFirst();
                person = getPerson(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        return person;
    }

    private Person getPerson(Cursor cursor) {
        Person person = new Person();
        try {

            person.setPersonId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_ID))));
            person.setPersonName(cursor.getString(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_NAME)));
            person.setPersonPhoneNo(cursor.getString(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_PHONE_NO)));
            person.setPersonEmail(cursor.getString(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_EMAIL)));
            person.setPersonAddress(cursor.getString(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_ADDRESS)));
            person.setPersonPhoto(BitmapUtils.getByteArrayAsBitmap(cursor.getBlob(cursor.getColumnIndex(TableInfoConstant.PersonTable.FIELD_IMAGE))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }

    private ContentValues getPersonContentValues(Person person, boolean isUpdate) {
        ContentValues contentValues = new ContentValues();
        if (isUpdate) {
            contentValues.put(TableInfoConstant.PersonTable.FIELD_ID, person.getPersonId());
        }
        contentValues.put(TableInfoConstant.PersonTable.FIELD_NAME, person.getPersonName());
        contentValues.put(TableInfoConstant.PersonTable.FIELD_PHONE_NO, person.getPersonPhoneNo());
        contentValues.put(TableInfoConstant.PersonTable.FIELD_EMAIL, person.getPersonEmail());
        contentValues.put(TableInfoConstant.PersonTable.FIELD_ADDRESS, person.getPersonAddress());
        contentValues.put(TableInfoConstant.PersonTable.FIELD_IMAGE, BitmapUtils.getBitmapAsByteArray(person.getPersonPhoto()));
        return contentValues;
    }

    private DatabaseHandler getDBHandler() {
        return new DatabaseHandler(context);
    }
}
