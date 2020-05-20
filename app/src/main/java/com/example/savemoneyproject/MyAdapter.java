package com.example.savemoneyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<History> mHistory;
    private final LayoutInflater mInflater;
    private String mTodayDate;
    private String selectedDate;

    public MyAdapter (Context context, String selectedDate, String mTodayDate) {
        mInflater = LayoutInflater.from(context);
        this.selectedDate = selectedDate;
        this.mTodayDate = mTodayDate;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = mInflater.inflate(R.layout.history_one, parent, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (mHistory != null) {
            History current = mHistory.get(position);
            holder.historyItemView.setText(current.getHistory());
        } else {
            holder.historyItemView.setText("No date Yet");
        }
    }

    void setHistory(List<History> histories) {
        mHistory = histories;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if (mHistory != null)
            return mHistory.size();
        else return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView historyItemView;


        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            historyItemView = itemView.findViewById(R.id.listText);

        }

    }

}
