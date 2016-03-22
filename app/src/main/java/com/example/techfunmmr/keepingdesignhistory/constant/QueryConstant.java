package com.example.techfunmmr.keepingdesignhistory.constant;

/**
 * Created by techfunmmr on 2/15/2016.
 */
public final class QueryConstant {

    public static final String [] CREATE_TABLE_QUERY = {"CREATE TABLE "+ TableInfoConstant.PersonTable.TABLE_NAME +"("+ TableInfoConstant.PersonTable.FIELD_ID+" INTEGER PRIMARY KEY, "+ TableInfoConstant.PersonTable.FIELD_NAME+" TEXT, "+ TableInfoConstant.PersonTable.FIELD_PHONE_NO+" TEXT, "+ TableInfoConstant.PersonTable.FIELD_EMAIL+" TEXT, " + TableInfoConstant.PersonTable.FIELD_ADDRESS+ " TEXT, "+ TableInfoConstant.PersonTable.FIELD_IMAGE +" blob)"
                                                        };
    public static final String DROP_TABLE_PREFIX = "DROP TABLE IF EXISTS ";

    /** PERSON **/
    public static final String SELECT_ALL_PERSON = "SELECT * FROM " + TableInfoConstant.PersonTable.TABLE_NAME;

    public static final String SELECT_PERSON_WITH_ID = "SELECT * FROM " + TableInfoConstant.PersonTable.TABLE_NAME + " WHERE id=";

    public static final String WHERE_CLAUSE_PERSON_ID = TableInfoConstant.PersonTable.FIELD_ID + " = ?";
}
