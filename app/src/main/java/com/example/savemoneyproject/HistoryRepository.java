package com.example.savemoneyproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryRepository {

    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mAllHistory;

    HistoryRepository(Application application){
        AppDataBase db = AppDataBase.getDatabase(application);
        mHistoryDao = db.historyDao();
        mAllHistory = mHistoryDao.getAll();
    }

    LiveData<List<History>> getAllHistory() {
        return mAllHistory;
    }

    public void insert (History history) {
        new insertAsyncTask(mHistoryDao).execute(history);
    }

    public void deleteAll()  {
        new deleteAllHistoryAsyncTask(mHistoryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<History, Void , Void> {
        private HistoryDao mAsyncTaskDao;

        insertAsyncTask(HistoryDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final History... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllHistoryAsyncTask extends AsyncTask<Void, Void, Void> {
        private HistoryDao mAsyncTaskDao;

        deleteAllHistoryAsyncTask(HistoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}