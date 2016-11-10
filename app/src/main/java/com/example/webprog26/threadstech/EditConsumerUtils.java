package com.example.webprog26.threadstech;

import java.util.ArrayList;

/**
 * Created by webprog26 on 09.11.2016.
 */

class EditConsumerUtils {

    static boolean areThereConsumersToEdit(ArrayList<Long> iDs){
        return iDs.size() == 1;
    }
}
