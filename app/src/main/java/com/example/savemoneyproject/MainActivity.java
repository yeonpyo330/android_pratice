package com.example.savemoneyproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savemoneyproject.databinding.ContentMainBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// todo : use view binding
public class MainActivity extends AppCompatActivity {

    private HistoryViewModel mHistoryViewModel;
    private ConnectivityManager connectivityManager;
    private MyAdapter adapter;
    public static final int NEW_ACTIVITY_REQUEST_CODE = 1;
    private ContentMainBinding bindingContent;
    private LinearLayoutManager mManager;
    private String money;
    private int todayDate, todayMonth, todayYear;
    private String selectedDate;
    private String mTodayDate;
    private int income, cost = 0;
    public static String BaseUrl = "http://api.openweathermap.org/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingContent = ContentMainBinding.inflate(getLayoutInflater());
        View contentView = bindingContent.getRoot();
        setContentView(contentView);

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

        bindingContent.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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
        getIncomeTotal();
        getCostTotal();
//        showCurrentHistory();

        bindingContent.monthlyHistoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MonthlyActivity.class);
                startActivity(intent);
            }
        });

        bindingContent.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }
        });

        bindingContent.btnAlarm.setOnClickListener(new View.OnClickListener() {
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
        // todo : create a new instance everytime it is called might leak memory
        // todo : please consider separating setting the view part and update logic
        adapter = new MyAdapter(this);
        bindingContent.recyclerviewOne.setAdapter(adapter);
        mManager = new LinearLayoutManager(this);
        bindingContent.recyclerviewOne.setLayoutManager(mManager);
    }


//        mHistoryViewModel.getIncomeTotal().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                bindingContent.incomeMoney.setText(String.valueOf(integer));
//                income = integer;
//                bindingContent.balanceMoney.setText(String.valueOf(income - cost));
//            }
//        });

    public void getIncomeTotal() {
        mHistoryViewModel.getIncomeTotal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer integer) throws Exception {
                                   bindingContent.incomeMoney.setText(String.valueOf(integer));
                                   income = integer;
                                   bindingContent.balanceMoney.setText(String.valueOf(income - cost));
                               }
                           }
                );

    }

//        mHistoryViewModel.getCostTotal().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                bindingContent.costMoney.setText(String.valueOf(integer));
//                cost = integer;
//                bindingContent.balanceMoney.setText(String.valueOf(income - cost));
//            }
//        });

    public void getCostTotal() {
        mHistoryViewModel.getCostTotal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer integer) throws Exception {
                                   bindingContent.costMoney.setText(String.valueOf(integer));
                                   cost = integer;
                                   bindingContent.balanceMoney.setText(String.valueOf(income - cost));
                               }
                           }
                );
    }


//    public void showCurrentHistory(){
//        if (selectedDate.equals(mTodayDate)) {
//            mHistoryViewModel.getTodayHistory().observe(this, new Observer<List<History>>() {
//                @Override
//                public void onChanged(@Nullable final List<History> histories) {
//                    adapter.setHistory(histories);
//                }
//            });
//        } else {
//            mHistoryViewModel.getSelectedDateHistory(sendDate()).observe(this, new Observer<List<History>>() {
//                @Override
//                public void onChanged(@Nullable final List<History> histories) {
//                    adapter.setHistory(histories);
//                }
//            });
//        }
//    }

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
                    bindingContent.weatherData.setText(weatherResponse.getSys().getCountry()
                            + " " + "Tokyo : " + weatherResponse.getMain().getTemp() + "℃");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                if (NetworkAvailable()) {
                    bindingContent.weatherData.setText("No data");
                } else {
                    bindingContent.weatherData.setText("No Network");
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

