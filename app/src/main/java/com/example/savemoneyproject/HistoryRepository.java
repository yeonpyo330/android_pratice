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
    private Flowable<List<History>> mAllHistory;
    private Flowable<List<History>> mTodayHistory;
    private Flowable<Integer> incomeTotal;
    private Flowable<Integer> costTotal;

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

    Flowable<List<History>> getTodayHistory() {
        return mTodayHistory;
    }

    Flowable<List<History>> getSelectedDateHistory(String date) {
        return mHistoryDao.getSelectedDateHistory(date);
    }

    Flowable<List<History>> getMonthIncomeHistory(String date) {
        return mHistoryDao.getSelectedMonthIncomeHistory(date);
    }

    Flowable<List<History>> getMonthCostHistory(String date) {
        return mHistoryDao.getSelectedMonthCostHistory(date);
    }


    Flowable<Integer> getMonthlyIncomeTotal(String date) {
        return mHistoryDao.getMonthlyIncomeTotal(date);
    }

    Flowable<Integer> getMonthlyCostTotal(String date) {
        return mHistoryDao.getMonthlyCostTotal(date);
    }

    Flowable<Integer> getIncomeTotal() { return incomeTotal; }

    Flowable<Integer> getCostTotal() {
        return costTotal;
    }


//    public void insertHistory(History history) {
//        new insertAsyncTask(mHistoryDao).execute(history);
//    }

    public Completable insertHistory (History history) {
    return mHistoryDao.insertHistory(history);
    }


    public void deleteAll() {
        mHistoryDao.deleteAll();
    }

//    public void deleteAll() {
//        new deleteAllHistoryAsyncTask(mHistoryDao).execute();
//    }


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
