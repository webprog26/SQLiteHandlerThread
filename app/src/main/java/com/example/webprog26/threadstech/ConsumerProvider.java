package com.example.webprog26.threadstech;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by webprog26 on 08.11.2016.
 */

class ConsumerProvider {

    private MyDBHelper mDBHelper;

    ConsumerProvider(Activity activity) {
        this.mDBHelper = new MyDBHelper(activity);
    }

    long insertConsumerToDB(Consumer consumer){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.CUSTOMER_NAME, consumer.getName());
        contentValues.put(MyDBHelper.CUSTOMER_CASH, consumer.getCash());

        return mDBHelper.getWritableDatabase().insert(MyDBHelper.TABLE_CUSTOMERS, null, contentValues);
    }

    ArrayList<Consumer> getConsumers(){
        ArrayList<Consumer> consumersList = new ArrayList<>();

        Cursor cursor = mDBHelper.getReadableDatabase().query(mDBHelper.TABLE_CUSTOMERS, null, null, null, null, null, MyDBHelper.CUSTOMER_ID);

        while (cursor.moveToNext()){
            Consumer.Builder builder = Consumer.newBuilder();
                builder.setConsumerId(cursor.getLong(cursor.getColumnIndex(MyDBHelper.CUSTOMER_ID)))
                        .setConsumerName(cursor.getString(cursor.getColumnIndex(MyDBHelper.CUSTOMER_NAME)))
                        .setConsumerCash(cursor.getLong(cursor.getColumnIndex(MyDBHelper.CUSTOMER_CASH)));
            consumersList.add(builder.build());
        }
        cursor.close();
        return consumersList;
    }

    void deleteConsumerData(long consumerId){
        String whereClause = MyDBHelper.CUSTOMER_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(consumerId)};
        mDBHelper.getWritableDatabase().delete(mDBHelper.TABLE_CUSTOMERS, whereClause, whereArgs);
    }

    public void editConsumerData(Consumer consumer){
        String whereClause = MyDBHelper.CUSTOMER_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(consumer.getId())};

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBHelper.CUSTOMER_NAME, consumer.getName());
        contentValues.put(MyDBHelper.CUSTOMER_CASH, consumer.getCash());

        mDBHelper.getWritableDatabase().update(MyDBHelper.TABLE_CUSTOMERS, contentValues, whereClause, whereArgs);
    }
}
