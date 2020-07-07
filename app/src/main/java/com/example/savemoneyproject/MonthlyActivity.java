package com.example.savemoneyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.example.savemoneyproject.databinding.ActivityMonthlyBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.util.Calendar;

public class MonthlyActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private MonthIncomeFragment monthIncomeFragment;
    private MonthCostFragment monthCostFragment;
    private ActivityMonthlyBinding activityMonthlyBinding;
    private int year, month; // todo : avoid using static value -> Resolved
    private String selectedMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMonthlyBinding = ActivityMonthlyBinding.inflate(getLayoutInflater());
        View view = activityMonthlyBinding.getRoot();
        setContentView(view);

        monthIncomeFragment = new MonthIncomeFragment();
        monthCostFragment = new MonthCostFragment();

        setDefaultFragment();
        activityMonthlyBinding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();

            // todo : avoid creating a instance everytime -> Resolved
            switch (item.getItemId()) {
                case R.id.navigation_Income:
                    transaction.replace(R.id.content, monthIncomeFragment);
                    transaction.commit();
                    return true;

                case R.id.navigation_Cost:
                    transaction.replace(R.id.content, monthCostFragment);
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
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MonthlyActivity.this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        activityMonthlyBinding.yearMonth.setText("Selected Month : " + selectedYear + " / " + (selectedMonth + 1));
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
                .build().show();

    }

    //  Select month

    // todo : consider using regex
    public void toIntDate() {
        year = 2020;
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
        transaction.replace(R.id.content, monthIncomeFragment);
        transaction.commit();
    }

    public String sendMonth() {return selectedMonth;}
}
