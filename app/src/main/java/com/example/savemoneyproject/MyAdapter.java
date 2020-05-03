package com.example.savemoneyproject;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final LinkedList<String> historyList;
    private LayoutInflater mInflater;

    public MyAdapter (Context context, LinkedList<String> hList) {
        mInflater = LayoutInflater.from(context);
        this.historyList = hList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemVIew = mInflater.inflate(R.layout.history_one, parent, false);
        return new MyViewHolder(mItemVIew, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String mCurrent = historyList.get(position);
        holder.historyItemView.setText(mCurrent);


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView historyItemView;
        final MyAdapter mAdapter;

        public MyViewHolder(@NonNull View itemView, MyAdapter adapter) {
            super(itemView);
            historyItemView = itemView.findViewById(R.id.listText);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = historyList.get(mPosition);
            historyList.set(mPosition, "Clicked" + element);
            mAdapter.notifyDataSetChanged();

        }
    }

}
