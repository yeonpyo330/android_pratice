package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

public class MonthCostViewModel extends AndroidViewModel {
    private HistoryRepository mRepository;

    public MonthCostViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
    }


    LiveData<Integer> getMonthlyCostTotal(String date) {
        return mRepository.getMonthlyCostTotal(date);
    }

    LiveData<List<History>> getMonthCostHistory(String date) {
        return mRepository.getMonthCostHistory(date);
    }
}
