package com.example.webprog26.threadstech;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by webprog26 on 09.11.2016.
 */

class ConsumersAdapter extends RecyclerView.Adapter<ConsumersAdapter.ConsumersViewHolder> {

    class ConsumersViewHolder extends RecyclerView.ViewHolder{

        private TextView mConsumerName;
        private TextView mConsumerCash;
        private CheckBox mIsChecked;

        ConsumersViewHolder(View itemView) {
            super(itemView);

            mConsumerName = (TextView) itemView.findViewById(R.id.tvName);
            mConsumerCash = (TextView) itemView.findViewById(R.id.tvCash);
            mIsChecked = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

        void bind(final Consumer consumer, final ConsumerClickListener listener, final OnLongClickListener longClickListener){
            mConsumerName.setText(mActivity.getString(R.string.consumer_name, consumer.getName()));
            mConsumerCash.setText(mActivity.getString(R.string.consumer_cash, consumer.getCash()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onConsumersListItemClick(consumer);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onLongClick(ConsumersViewHolder.this.getAdapterPosition());
                    mIsChecked.setChecked(!mIsChecked.isChecked());
                    return true;
                }
            });
        }
    }

    private ArrayList<Consumer> mConsumers;
    private ConsumerClickListener mClickListener;
    private Activity mActivity;
    private OnLongClickListener mLongClickListener;

    ConsumersAdapter(ArrayList<Consumer> mConsumers, ConsumerClickListener mListener, Activity activity, OnLongClickListener longClickListener) {
        this.mConsumers = mConsumers;
        this.mClickListener = mListener;
        this.mActivity = activity;
        this.mLongClickListener = longClickListener;
    }

    @Override
    public void onBindViewHolder(ConsumersViewHolder holder, int position) {
        holder.bind(mConsumers.get(position), mClickListener, mLongClickListener);
    }

    @Override
    public ConsumersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.consumers_list_item, parent, false);
        return new ConsumersViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mConsumers.size();
    }

    void updateList(ArrayList<Consumer> data){
        this.mConsumers = data;
        notifyDataSetChanged();
    }

    Consumer getItem(int position){
        return mConsumers.get(position);
    }
}
