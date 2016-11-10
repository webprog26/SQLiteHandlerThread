package com.example.webprog26.threadstech;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by webprog26 on 08.11.2016.
 */

class UiHandler extends Handler {


    static final String CONSUMERS_LIST = "com.example.webprog26.threadstech.consumers_list";

    private ConsumersAdapter mAdapter;

    UiHandler(ConsumersAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case MyHandlerThread.CONSUMER_ADDED:
            case MyHandlerThread.CONSUMER_DELETED:
                mAdapter.updateList((ArrayList<Consumer>)msg.getData().getSerializable(CONSUMERS_LIST));
                break;
        }
    }
}
