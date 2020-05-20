package com.example.savemoneyproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryRepository {

    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mAllHistory;
    private LiveData<List<History>> mTodayHistory;

    //TODO : Can not work yet
//    private LiveData<List<History>> mIncomeTotal;
//    private LiveData<List<History>> mCostTotal;

    HistoryRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mHistoryDao = db.historyDao();
        mAllHistory = mHistoryDao.getAll();
        mTodayHistory = mHistoryDao.getTodayHistory();
//        mSelectedDayHistory = mHistoryDao.getSelectedDayHistory();
        //TODO : Can not work yet
//        mIncomeTotal = mHistoryDao.getIncomeTotal();
//        mCostTotal = mHistoryDao.getCostTotal();
    }




    LiveData<List<History>> getAllHistory() {
        return mAllHistory;
    }

    LiveData<List<History>> getTodayHistory() { return mTodayHistory; }

    LiveData<List<History>> getSelectedDateHistory(String date) { return mHistoryDao.getSelectedDateHistory(date); }

    int getIncomeTotal() { return mHistoryDao.getIncomeTotal(); }



//    LiveData<List<History>> getIncomeTotal() { return mIncomeTotal; }

//    LiveData<List<History>> getCostTotal() {
//        return mCostTotal;
//    }


    public void insertHistory(History history) {
        new insertAsyncTask(mHistoryDao).execute(history);
    }

    public void deleteAll() {
        new deleteAllHistoryAsyncTask(mHistoryDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<History, Void, Void> {
        private HistoryDao mAsyncTaskDao;

        insertAsyncTask(HistoryDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final History... params) {
            mAsyncTaskDao.insertHistory(params[0]);
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
