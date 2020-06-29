package com.example.savemoneyproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savemoneyproject.databinding.ActivitySecondBinding;


// todo : use view binding
public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivitySecondBinding bindingSecond;
    private EditText historyView;
    private Spinner mSpinner;
    public static final String EXTRA_REPLY1 =
            "com.example.android.content.REPLY1";
    public static final String EXTRA_REPLY2 =
            "com.example.android.content.REPLY2";
    private String spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingSecond = ActivitySecondBinding.inflate(getLayoutInflater());
        View secondView = bindingSecond.getRoot();
        setContentView(secondView);

       if (bindingSecond.planetsSpinner != null){
           bindingSecond.planetsSpinner.setOnItemSelectedListener(this);
       }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (bindingSecond.planetsSpinner != null) {
            bindingSecond.planetsSpinner.setAdapter(adapter);
        }



        final Button button = findViewById(R.id.addBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(bindingSecond.editText.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String moneyInfo = bindingSecond.editText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY1, moneyInfo);
                    replyIntent.putExtra(EXTRA_REPLY2, spinnerText);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message + " selected",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        spinnerText = spinnerLabel;
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



