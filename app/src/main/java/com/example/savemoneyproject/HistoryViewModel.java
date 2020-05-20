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
    private LiveData<List<History>> mSelectedDateHistory;
    //TODO : Can not work yet
//    private LiveData<List<History>> mIncomeTotal;
//    private LiveData<List<History>> mCostTotal;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
        mAllHistory = mRepository.getAllHistory();
        mTodayHistory = mRepository.getTodayHistory();
        //TODO : Can not work yet
//        mIncomeTotal = mRepository.getIncomeTotal();
//        mCostTotal = mRepository.getCostTotal();
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

    int getIncomeTotal () {
        return mRepository.getIncomeTotal(); }



    public void insertHistory(History history) {
        mRepository.insertHistory(history);
    }

    public void deleteAll() {mRepository.deleteAll();}
}


