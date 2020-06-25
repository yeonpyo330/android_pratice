package com.example.savemoneyproject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// todo : monthlycost fragment && monthincomefragment -> view are identical
// todo : please consider abstract class or find a way to merge it
public class MonthCostFragment extends Fragment {

    private MonthCostViewModel mViewModel;
    private RecyclerView mRecyclerViewIncome;
    private View view;
    private String selectedMonth;


    public static MonthCostFragment newInstance() {
        return new MonthCostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.month_cost_fragment, container, false);
         return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MonthCostViewModel.class);
        selectedMonth = ((MonthlyActivity)getActivity()).sendMonth();
        if (selectedMonth != null){
            setAdapter();
        }
    }

    public void setAdapter() {
        mRecyclerViewIncome = view.findViewById(R.id.cost_recyclerview);
        final MyAdapter adapter = new MyAdapter(getActivity());
        mRecyclerViewIncome.setAdapter(adapter);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        mRecyclerViewIncome.setLayoutManager(mManager);

        final TextView monthlyCost = view.findViewById(R.id.cost_total);

        mViewModel.getMonthlyCostTotal(selectedMonth).observe(getViewLifecycleOwner() ,new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                monthlyCost.setText(String.valueOf(integer));
            }
        });

        mViewModel.getMonthCostHistory(selectedMonth).observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setHistory(histories);
            }
        });

}}
