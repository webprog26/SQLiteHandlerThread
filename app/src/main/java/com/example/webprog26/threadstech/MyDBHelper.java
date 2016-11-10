package com.example.webprog26.threadstech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webprog26 on 08.11.2016.
 */

class MyDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "my_db";
    private static final int DB_VERSION = 1;

    MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static final String TABLE_CUSTOMERS = "table_customers";
    static final String CUSTOMER_ID = "_id";
    static final String CUSTOMER_NAME = "customer_name";
    static final String CUSTOMER_CASH = "customer_cash";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_CUSTOMERS + "("
            + CUSTOMER_ID + " integer primary key autoincrement, "
            + CUSTOMER_NAME + " varchar(100), "
            + CUSTOMER_CASH + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Not needed yet
    }
}
