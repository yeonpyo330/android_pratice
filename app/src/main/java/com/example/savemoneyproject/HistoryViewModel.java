package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mRepository;

    private LiveData<List<History>> mAllHistory;
    private LiveData<List<History>> mTodayHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
        mAllHistory = mRepository.getAllHistory();
        mTodayHistory = mRepository.getTodayHistory();
    }

    LiveData<List<History>> getAllHistory() {
        return mAllHistory;
    }

    LiveData<List<History>> getTodayHistory() {
        return mTodayHistory;
    }

    LiveData<List<History>> getSelectedDateHistory(String date) {
        return mRepository.getSelectedDateHistory(date);
    }

//    public Integer getIncomeTotal() {
//        return mRepository.getIncomeTotal();
//    }
//
//    public Integer getCostTotal() {
//        return mRepository.getCostTotal();
//    }

    public void insertHistory(History history) {
        mRepository.insertHistory(history);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}


