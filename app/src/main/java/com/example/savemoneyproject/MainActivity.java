package com.example.savemoneyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private HistoryViewModel mHistoryViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private LinearLayoutManager mManager;
    private TextView incomeView;
    private TextView costView;
    private TextView balanceView;
    private String money;
    private int todayDate, todayMonth, todayYear;
    private String selectedDate;
    private String mTodayDate;
    private int income,cost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Calendar calendar = Calendar.getInstance();
        todayDate = calendar.get(Calendar.DATE);
        todayMonth = calendar.get(Calendar.MONTH) + 1;
        todayYear = calendar.get(Calendar.YEAR);

        mHistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        mTodayDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());


        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int d) {
                todayYear = y;
                todayMonth = m + 1;
                todayDate = d;
                toIntDate();
                setAdapter();
            }
        });



        toIntDate();
        setAdapter();

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

    public void toIntDate() {
        selectedDate = "";
        if (todayMonth < 10 && todayDate >= 10) {
            selectedDate = ("" + todayYear + "0" + todayMonth + "" + todayDate);
        } else if (todayMonth >= 10 && todayDate < 10) {
            selectedDate = ("" + todayYear + "" + todayMonth + "0" + todayDate);
        } else if (todayMonth < 10 && todayDate < 10) {
            selectedDate = ("" + todayYear + "0" + todayMonth + "0" + todayDate);
        } else if (todayMonth >= 10 && todayDate >= 10) {
            selectedDate = ("" + todayYear + "" + todayMonth + "" + todayDate);
        }
    }

    public String sendDate() {
        return selectedDate;
    }

    public void setAdapter() {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewOne);
        final MyAdapter adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        mManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mManager);

        incomeView = (TextView) findViewById(R.id.income_money);
        costView = (TextView) findViewById(R.id.cost_money);
        balanceView = (TextView) findViewById(R.id.balance_money);

        mHistoryViewModel.getIncomeTotal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                incomeView.setText(String.valueOf(integer));
                income = integer;
                balanceView.setText(String.valueOf(income-cost));
            }
        });

        mHistoryViewModel.getCostTotal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                costView.setText(String.valueOf(integer));
                cost = integer;
                balanceView.setText(String.valueOf(income-cost));
            }
        });


        if (selectedDate.equals(mTodayDate)) {
//            mHistoryViewModel.getAllHistory().observe(this, new Observer<List<History>>() {
//                @Override
//                public void onChanged(@Nullable final List<History> histories) {
//                    adapter.setHistory(histories);
            mHistoryViewModel.getTodayHistory().observe(this, new Observer<List<History>>() {
                @Override
                public void onChanged(@Nullable final List<History> histories) {
                    adapter.setHistory(histories);
                }
            });
        } else {
            mHistoryViewModel.getSelectedDateHistory(sendDate()).observe(this, new Observer<List<History>>() {
                @Override
                public void onChanged(@Nullable final List<History> histories) {
                    adapter.setHistory(histories);
                }
            });
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String actionType = data.getStringExtra(SecondActivity.EXTRA_REPLY2);
            String moneyInfo = data.getStringExtra(SecondActivity.EXTRA_REPLY1);
            int moneyInt = Integer.parseInt(moneyInfo);
            String income = "Income";
            if (actionType != null && actionType.equals(income)) {
                money = "+" + moneyInfo;
            } else {
                money = "-" + moneyInfo;
            }

            History history = new History(selectedDate + "  " + actionType + "  " + money + " ¥ ", moneyInt, actionType, selectedDate);
            mHistoryViewModel.insertHistory(history);

        } else {
            Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_LONG).show();
        }
    }

}

