package com.example.webprog26.threadstech;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by webprog26 on 09.11.2016.
 */

public class FragmentAddConsumer extends DialogFragment implements View.OnClickListener{

    private EditText mEtConsumetName;
    private EditText mEtConsumerCash;

    private ConsumerAddictor mConsumerAddictor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_consumer, null);

        mEtConsumetName = (EditText) view.findViewById(R.id.etConsumerName);
        mEtConsumerCash = (EditText) view.findViewById(R.id.etConsumerCash);

        Button addConsumer = (Button) view.findViewById(R.id.btnAddConcumer);
        Button cancelAddConsumer = (Button) view.findViewById(R.id.btnCancelAddConcumer);

        addConsumer.setOnClickListener(this);
        cancelAddConsumer.setOnClickListener(this);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getActivity().getResources()
                        .getString(R.string.consumer_addition))
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddConcumer:
                String name = mEtConsumetName.getText().toString();
                String cash = mEtConsumerCash.getText().toString();

                if(name.length() < 1 ||
                        cash.length() < 1){
                    Toast.makeText(getActivity(), getActivity().getResources()
                                   .getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
                    return;
                }

                Consumer.Builder builder = Consumer.newBuilder();
                    builder.setConsumerName(name)
                            .setConsumerCash(Integer.parseInt(cash));
                mConsumerAddictor = (ConsumerAddictor) getActivity();
                mConsumerAddictor.addConsumer(builder.build());
                FragmentAddConsumer.this.dismiss();
                break;
            case R.id.btnCancelAddConcumer:
                FragmentAddConsumer.this.dismiss();
                break;
        }
    }
}
