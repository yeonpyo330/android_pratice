package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mRepository;

    private LiveData<List<History>> mAllHistory;
    private LiveData<List<History>> mTodayHistory;
    private LiveData<Integer> getIncomeTotal;
    private LiveData<Integer> getCostTotal;

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

    LiveData<List<History>> getTodayHistory() {
        return mTodayHistory;
    }

    LiveData<List<History>> getSelectedDateHistory(String date) {
        return mRepository.getSelectedDateHistory(date);
    }

    LiveData<Integer> getIncomeTotal() { return getIncomeTotal; }

    LiveData<Integer> getCostTotal() {
        return getCostTotal;
    }

    public Completable insertHistory(History history) {
        return mRepository.insertHistory(history);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}