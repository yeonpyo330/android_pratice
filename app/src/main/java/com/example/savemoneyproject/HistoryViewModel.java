package com.example.savemoneyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository mRepository;

    private LiveData<List<History>> mAllHistory;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HistoryRepository(application);
        mAllHistory = mRepository.getAllHistory();

    }

    LiveData<List<History>> getAllHistory() {
        return mAllHistory;
    }

    public void insert(History history) {
        mRepository.insert(history);
    }
}
