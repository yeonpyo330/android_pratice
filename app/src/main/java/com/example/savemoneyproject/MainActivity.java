package com.example.savemoneyproject;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// todo : use view binding
public class MainActivity extends AppCompatActivity {

    private HistoryViewModel mHistoryViewModel;
    private ConnectivityManager connectivityManager;
    public static final int NEW_ACTIVITY_REQUEST_CODE = 1;
    private LinearLayoutManager mManager;
    private TextView incomeView;
    private TextView costView;
    private TextView balanceView;
    private TextView weatherData;
    private String money;
    private int todayDate, todayMonth, todayYear;
    private String selectedDate;
    private String mTodayDate;
    private int income, cost = 0;
    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "0ee1e3eadd152b696e4e6773ea9b3c8c";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // todo : please get the data based on the user's current location using gps
        getCurrentData("Tokyo");


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

        Button monthly_history_btn = findViewById(R.id.monthly_history_button);
        monthly_history_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonthlyActivity.class);
                startActivity(intent);
            }
        });

        Button addBtn = findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }
        });

        Button alarmBtn = findViewById(R.id.btn_alarm);
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(NetworkReceiver, intentFilter);

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

    //  Select Date
    // todo : convert to regex
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

    //  Set adapter to recyclerview
    public void setAdapter() {
        RecyclerView recyclerView = findViewById(R.id.recyclerviewOne);

        // todo : create a new instance everytime it is called might leak memory
        // todo : please consider separating setting the view part and update logic
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
                balanceView.setText(String.valueOf(income - cost));
            }
        });

        mHistoryViewModel.getCostTotal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                costView.setText(String.valueOf(integer));
                cost = integer;
                balanceView.setText(String.valueOf(income - cost));
            }
        });


        if (selectedDate.equals(mTodayDate)) {
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

        if (requestCode == NEW_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
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

    // todo : not working
    private void getCurrentData(final String name) {
        weatherData = findViewById(R.id.weather_data);

        //todo : consider using this as singleton class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService weatherService = retrofit.create(WeatherService.class);


        // todo : consider using RxJava/RxAndroid
        Call<WeatherResponse> call = weatherService.getCurrentWeatherData(name);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = (WeatherResponse) response.body();
                    weatherData.setText(weatherResponse.getSys().getCountry()
                            + " " + "Tokyo : " + weatherResponse.getMain().getTemp() + "℃");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                if (NetworkAvailable()) {
                    weatherData.setText("No data");
                } else {
                    weatherData.setText("No Network");
                }

            }
        });
    }

    private BroadcastReceiver NetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkAvailable())
                NetworkState();
            else
                Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_SHORT).show();
        }
    };

    private boolean NetworkAvailable() {
        try {
            Thread.sleep(600);
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                // Get Network info
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                // Check network status
                if (networkInfo != null || networkInfo.isAvailable()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void NetworkState() {
        NetworkInfo.State mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        // Mobile status
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            Toast.makeText(this, "Current Network Status - Mobile", Toast.LENGTH_SHORT).show();
        }
        //Wifi status
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            Toast.makeText(this, "Current Network Status - Wi-Fi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(NetworkReceiver);
    }
}

