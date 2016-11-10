package com.example.webprog26.threadstech;

import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by webprog26 on 09.11.2016.
 */

class DeleteConsumerUtils {

    static boolean areThereConsumersToDelete(ArrayList<Long> iDs){
        return iDs.size() > 0;
    }

    static boolean isConsumerAlreadyMarked(ArrayList<Long> iDs, long consumerId){
        return iDs.contains(consumerId);
    }

    static void changeDeleteButtonVisibility(ImageButton imageButton, boolean isNeeded){
        if(isNeeded){
            imageButton.setVisibility(View.VISIBLE);
        } else{
            imageButton.setVisibility(View.GONE);
        }
    }
}
