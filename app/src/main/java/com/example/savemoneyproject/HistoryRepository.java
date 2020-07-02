package com.example.savemoneyproject;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


public class HistoryRepository {

    private HistoryDao mHistoryDao;
    private LiveData<List<History>> mAllHistory;
    private LiveData<List<History>> mTodayHistory;
    private LiveData<Integer> incomeTotal;
    private LiveData<Integer> costTotal;

    HistoryRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mHistoryDao = db.historyDao();
//        mAllHistory = mHistoryDao.getAll();
        mTodayHistory = mHistoryDao.getTodayHistory();
        incomeTotal = mHistoryDao.getIncomeTotal();
        costTotal = mHistoryDao.getCostTotal();
    }


//    Flowable<List<History>> getAllHistory() {
//        return mAllHistory;
//    }

    LiveData<List<History>> getTodayHistory() {
        return mTodayHistory;
    }

    LiveData<List<History>> getSelectedDateHistory(String date) {
        return mHistoryDao.getSelectedDateHistory(date);
    }

    LiveData<List<History>> getMonthIncomeHistory(String date) {
        return mHistoryDao.getSelectedMonthIncomeHistory(date);
    }

    LiveData<List<History>> getMonthCostHistory(String date) {
        return mHistoryDao.getSelectedMonthCostHistory(date);
    }


    LiveData<Integer> getMonthlyIncomeTotal(String date) {
        return mHistoryDao.getMonthlyIncomeTotal(date);
    }

    LiveData<Integer> getMonthlyCostTotal(String date) {
        return mHistoryDao.getMonthlyCostTotal(date);
    }

    LiveData<Integer> getIncomeTotal() { return incomeTotal; }

    LiveData<Integer> getCostTotal() {
        return costTotal;
    }


//    public void insertHistory(History history) {
//        new insertAsyncTask(mHistoryDao).execute(history);
//    }

    public Completable insertHistory(History history) {
        return mHistoryDao.insertHistory(history);
    }


    public void deleteAll() {
        mHistoryDao.deleteAll();
    }


    //todo : AsyncTask is deprecated since API30. Please replace AsyncTask with other asynchronous task
    //todo : Something like RxJava / RxAndroid is the most popular one

//    private static class insertAsyncTask extends AsyncTask<History, Void, Void> {
//        private HistoryDao mAsyncTaskDao;
//
//        insertAsyncTask(HistoryDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//
//        @Override
//        protected Void doInBackground(final History... params) {
//            mAsyncTaskDao.insertHistory(params[0]);
//            return null;
//        }
//    }

//    private static class deleteAllHistoryAsyncTask extends AsyncTask<Void, Void, Void> {
//        private HistoryDao mAsyncTaskDao;
//
//        deleteAllHistoryAsyncTask(HistoryDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            mAsyncTaskDao.deleteAll();
//            return null;
//        }
//    }
}
