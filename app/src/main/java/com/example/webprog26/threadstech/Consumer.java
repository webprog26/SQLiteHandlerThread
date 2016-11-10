package com.example.webprog26.threadstech;

import java.io.Serializable;

/**
 * Created by webprog26 on 08.11.2016.
 */

public class Consumer implements Serializable{

    private long mId;
    private String mName;
    private long mCash;

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    long getCash() {
        return mCash;
    }

    static Consumer.Builder newBuilder(){
        return new Consumer().new Builder();
    }

    class Builder{

        Consumer.Builder setConsumerId(long consumerId){
            Consumer.this.mId = consumerId;
            return this;
        }

        Consumer.Builder setConsumerName(String consumerName){
            Consumer.this.mName = consumerName;
            return this;
        }

        Consumer.Builder setConsumerCash(long consumerCash){
            Consumer.this.mCash = consumerCash;
            return this;
        }

        Consumer build(){
            return Consumer.this;
        }
    }


}
