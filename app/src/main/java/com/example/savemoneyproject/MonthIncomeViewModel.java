package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

public class MonthIncomeViewModel extends AndroidViewModel {
    private HistoryRepository mRepository;

    public MonthIncomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
    }

    Flowable<Integer> getMonthlyIncomeTotal(String date) {
        return mRepository.getMonthlyIncomeTotal(date);
    }

    Flowable<List<History>> getMonthIncomeHistory(String date) {
        return mRepository.getMonthIncomeHistory(date);
    }
}
