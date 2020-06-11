package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MonthIncomeViewModel extends AndroidViewModel {
    private HistoryRepository mRepository;

    public MonthIncomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
    }

    LiveData<Integer> getMonthlyIncomeTotal(String date) {
        return mRepository.getMonthlyIncomeTotal(date);
    }

    LiveData<List<History>> getMonthIncomeHistory(String date) {
        return mRepository.getMonthIncomeHistory(date);
    }
}
