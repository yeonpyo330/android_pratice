package com.example.savemoneyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HistoryViewModel mHistoryViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private LinearLayoutManager mManager;
//    private TextView incomeView;
//    private TextView costView;
//    private TextView balanceView;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        incomeView = (TextView) findViewById(R.id.income_money);
//        costView = (TextView) findViewById(R.id.cost_money);
//        balanceView = (TextView) findViewById(R.id.balance_money) ;


        RecyclerView recyclerView = findViewById(R.id.recyclerviewOne);
        final MyAdapter adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        mManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mManager);


        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        mHistoryViewModel.getAllHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable final List<History> histories) {
                adapter.setHistory(histories);
            }
        });

        //TODO : Can not work yet cause historyDao Query is dose not work
//        incomeView.setText(mHistoryViewModel.getIncomeTotal().toString());
//        costView.setText(mHistoryViewModel.getCostTotal().toString());
//        balanceView.setText(Integer.parseInt(incomeView.getText().toString()) - Integer.parseInt(costView.getText().toString()));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing history data..",
                    Toast.LENGTH_SHORT).show();

            mHistoryViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String actionType = data.getStringExtra(SecondActivity.EXTRA_REPLY2);
            String moneyInfo = data.getStringExtra(SecondActivity.EXTRA_REPLY1);
            int moneyInt = Integer.parseInt(moneyInfo);
            String income= "Income";
            if (actionType != null && actionType.equals(income)) {
                money = "+" + moneyInfo;
            } else {
                money = "-" + moneyInfo;
            }

            History history = new History(time + "  " + actionType + "  " + money + " ¥ " , moneyInt , actionType);
            mHistoryViewModel.insertHistory(history);

        } else {
            Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_LONG).show();
        }
    }

}

