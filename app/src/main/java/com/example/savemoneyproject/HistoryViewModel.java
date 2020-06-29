package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mRepository;

    private Flowable<List<History>> mAllHistory;
    private Flowable<List<History>> mTodayHistory;
    private Flowable<Integer> getIncomeTotal;
    private Flowable<Integer> getCostTotal;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
//        mAllHistory = mRepository.getAllHistory();
        mTodayHistory = mRepository.getTodayHistory();
        getIncomeTotal = mRepository.getIncomeTotal();
        getCostTotal = mRepository.getCostTotal();
    }

//    Flowable<List<History>> getAllHistory() {
//        return mAllHistory;
//    }

    Flowable<List<History>> getTodayHistory() {
        return mTodayHistory;
    }

    Flowable<List<History>> getSelectedDateHistory(String date) {
        return mRepository.getSelectedDateHistory(date);
    }

    Flowable<Integer> getIncomeTotal() { return getIncomeTotal; }

    Flowable<Integer> getCostTotal() {
        return getCostTotal;
    }

    public void insertHistory(History history) {
        mRepository.insertHistory(history);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}


