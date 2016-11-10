package com.example.webprog26.threadstech;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by webprog26 on 08.11.2016.
 */

class MyHandlerThread extends HandlerThread {

    private static final String TAG = "MyHandlerThread";

    private static final String CONSUMER_BUNDLE = "com.example.webprog26.threadstech.consumer_bundle";
    private static final String CONSUMER_IDS_BUNDLE = "com.example.webprog26.threadstech.consumer_ids_bundle";

    static final int CONSUMER_ADDED = 100;
    public static final int CONSUMER_UPDATED = 102;
    static final int CONSUMER_DELETED = 103;


    private Handler mUiHandler;
    private Handler mWorkerHandler;
    private ConsumerProvider mConsumerProvider;

    MyHandlerThread(Handler mUiHandler, Activity activity) {
        super(TAG);
        this.mUiHandler = mUiHandler;
        this.mConsumerProvider = new ConsumerProvider(activity);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(this.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    Bundle consumersListBundle = new Bundle();
                    switch (msg.what){
                        case CONSUMER_ADDED:
                            mConsumerProvider.insertConsumerToDB((Consumer) msg.getData().getSerializable(CONSUMER_BUNDLE));
                            clearBundle(consumersListBundle);
                            Message addMessage = mUiHandler.obtainMessage(CONSUMER_ADDED);
                            consumersListBundle.putSerializable(UiHandler.CONSUMERS_LIST, mConsumerProvider.getConsumers());
                            addMessage.setData(consumersListBundle);
                            addMessage.sendToTarget();
                            break;
                        case CONSUMER_DELETED:
                            clearBundle(consumersListBundle);
                            for(long id: (ArrayList<Long>) msg.getData().getSerializable(CONSUMER_IDS_BUNDLE) != null ? (ArrayList<Long>) msg.getData().getSerializable(CONSUMER_IDS_BUNDLE) : null){
                                mConsumerProvider.deleteConsumerData(id);
                            }
                            Message delMessage = mUiHandler.obtainMessage(CONSUMER_DELETED);
                            consumersListBundle.putSerializable(UiHandler.CONSUMERS_LIST, mConsumerProvider.getConsumers());
                            delMessage.setData(consumersListBundle);
                            delMessage.sendToTarget();
                            break;

                    }
            }
        };
    }

    void addConsumer(Consumer consumer){
        Bundle consumerBundle = new Bundle();
        consumerBundle.putSerializable(CONSUMER_BUNDLE, consumer);

        Message message = mWorkerHandler.obtainMessage(CONSUMER_ADDED);
        message.setData(consumerBundle);
        message.sendToTarget();
    }

    void deleteConsumer(ArrayList<Long> consumerIDs){
        Bundle consumerIDSBundle = new Bundle();
        consumerIDSBundle.putSerializable(CONSUMER_IDS_BUNDLE, consumerIDs);

        Message message = mWorkerHandler.obtainMessage(CONSUMER_DELETED);
        message.setData(consumerIDSBundle);
        message.sendToTarget();
    }

    private void clearBundle(Bundle bundle){
        if(bundle.size() > 0){
            bundle.clear();
        }
    }
}
