package com.example.webprog26.threadstech;


import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConsumerAddictor, View.OnClickListener{

    private static final String TAG = "MainActivity_TAG";

    private MyHandlerThread mHandlerThread;
    private ConsumerProvider mConsumerProvider;
    private ArrayList<Consumer> mConsumers;
    private ConsumersAdapter mConsumersAdapter;

    private ArrayList<Long> mIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConsumerProvider = new ConsumerProvider(this);
        mConsumers = new ArrayList<>();
        mIDs = new ArrayList<>();

        final ImageButton iBtnDeleteConsumer = (ImageButton) findViewById(R.id.iBtnDeleteConsumer);
        iBtnDeleteConsumer.setOnClickListener(this);

        final ImageButton iBtnEditConsumer = (ImageButton) findViewById(R.id.iBtnEditConsumer);
        iBtnEditConsumer.setOnClickListener(this);

        mConsumersAdapter = new ConsumersAdapter(mConsumers, new ConsumerClickListener() {
            @Override
            public void onConsumersListItemClick(Consumer consumer) {
                Log.i(TAG, "id: " + consumer.getId() + ", name: " + consumer.getName() + ", cash: " + consumer.getCash());
            }
        }, this, new OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                long consumerId = mConsumersAdapter.getItem(position).getId();
                if(!DeleteConsumerUtils.isConsumerAlreadyMarked(mIDs, consumerId)){
                    mIDs.add(consumerId);
                    Log.i(TAG, "mIDs.size(): " + mIDs.size());
                } else {
                    mIDs.remove(consumerId);
                    Log.i(TAG, "mIDs.size(): " + mIDs.size());
                }
                DeleteConsumerUtils.changeDeleteButtonVisibility(iBtnEditConsumer, EditConsumerUtils.areThereConsumersToEdit(mIDs));

                Log.i(TAG, "name: " + mConsumersAdapter.getItem(position).getName());
                DeleteConsumerUtils.changeDeleteButtonVisibility(iBtnDeleteConsumer, DeleteConsumerUtils.areThereConsumersToDelete(mIDs));
            }
        });

        new CustomersFirstTimeLoader().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        UiHandler uiHandler = new UiHandler(mConsumersAdapter);
        mHandlerThread = new MyHandlerThread(uiHandler, this);
        mHandlerThread.start();
        mHandlerThread.getLooper();

        FloatingActionButton btnAddConsumer = (FloatingActionButton) findViewById(R.id.btnAddNewConsumer);
        btnAddConsumer.setOnClickListener(this);

        RecyclerView consumersRecyclerView = (RecyclerView) findViewById(R.id.consumersRecyclerView);
        initRecyclerView(consumersRecyclerView, mConsumersAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddNewConsumer:
                FragmentAddConsumer addConsumer = new FragmentAddConsumer();
                addConsumer.show(getFragmentManager(), null);
                break;
            case R.id.iBtnDeleteConsumer:
                mHandlerThread.deleteConsumer(mIDs);
                mIDs = new ArrayList<>();
                break;
        }
    }

    @Override
    public void addConsumer(Consumer consumer) {
        Log.i(TAG, "name: " + consumer.getName() + ", cash: " + consumer.getCash());
        mHandlerThread.addConsumer(consumer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }

    private class CustomersFirstTimeLoader extends AsyncTask<Void, Void, ArrayList<Consumer>>{
        @Override
        protected ArrayList<Consumer> doInBackground(Void... voids) {

            return mConsumerProvider.getConsumers();
        }

        @Override
        protected void onPostExecute(ArrayList<Consumer> consumers) {
            super.onPostExecute(consumers);
            mConsumersAdapter.updateList(consumers);
        }
    }

    private void initRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
    }
}
