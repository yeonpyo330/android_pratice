package com.example.savemoneyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.util.Calendar;

public class MonthlyActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private TextView yearMonth;
    private int year =2020 ,month;
    private String selectedMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);
        setDefaultFragment();
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_Income:
                    transaction.replace(R.id.content, new MonthIncomeFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_Cost:
                    transaction.replace(R.id.content, new MonthCostFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new MonthIncomeFragment());
        transaction.commit();
    }


    public void btnMonthYear(View view) {
        yearMonth = findViewById(R.id.year_month);
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MonthlyActivity.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        yearMonth.setText("Selected Month : " + selectedYear + " / " + (selectedMonth + 1));
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(0)
                .setMinYear(today.get(Calendar.YEAR))
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(today.get(Calendar.YEAR))
                .setTitle("Select Month Year")
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        month = selectedMonth + 1;
                        toIntDate();

                    }
                })
//                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
//                    @Override
//                    public void onYearChanged(int selectedYear) {
//                    }
//                })
                .build().show();

    }

    //  Select month
    public void toIntDate() {
        selectedMonth = "";
        if (month < 10 ) {
            selectedMonth = ("" + year + "0" + month);
        } else if (month >= 10) {
            selectedMonth = ("" + year + "" + month);
        } else if (month < 10) {
            selectedMonth = ("" + year + "0" + month);
        } else if (month >= 10) {
            selectedMonth = ("" + year + "" + month);
        }

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new MonthIncomeFragment());
        transaction.commit();
    }

    public String sendMonth() {return selectedMonth;}
}
