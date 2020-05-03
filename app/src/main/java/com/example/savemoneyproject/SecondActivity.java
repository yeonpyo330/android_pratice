package com.example.savemoneyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {
    private EditText historyText;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        historyText = (EditText) findViewById(R.id.editText);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        final LayoutInflater inflater = getLayoutInflater();
        final View mTextView = inflater.inflate(R.layout.history_one, null);
        final TextView secondText = (TextView) mTextView.findViewById(R.id.listText);


        final AppDataBase dataBase = Room.databaseBuilder(this, AppDataBase.class, "History-db")
                .allowMainThreadQueries()
                .build();

        secondText.setText(dataBase.historyDao().getAll().toString());

        findViewById(R.id.editText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase.historyDao().insert(new History(historyText.getText().toString()));
                secondText.setText(dataBase.historyDao().getAll().toString());
                hideKeyboard();
                switch (v.getId())
                {
                    case R.id.secondActivity :
                        break;

                    case R.id.addBtn :
                        break;
                }
            }
        });

    }

    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(historyText.getWindowToken(), 0);
    }


    public void cancelBtn(View view) {
        Intent intent  = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
