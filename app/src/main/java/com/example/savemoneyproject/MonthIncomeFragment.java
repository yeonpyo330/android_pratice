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

public class MonthIncomeFragment extends Fragment {

    private View view;
    private MonthIncomeViewModel mViewModel;
    private RecyclerView mRecyclerViewIncome;
    private String selectedMonth;

    public static MonthIncomeFragment newInstance() {
        return new MonthIncomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.month_income_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MonthIncomeViewModel.class);
        selectedMonth = ((MonthlyActivity) getActivity()).sendMonth();
        if (selectedMonth != null) {
            setAdapter();
        }
    }

    // todo : separate setting and update logic
    public void setAdapter() {
        mRecyclerViewIncome = view.findViewById(R.id.income_recyclerview);
        final MyAdapter adapter = new MyAdapter(getActivity());
        mRecyclerViewIncome.setAdapter(adapter);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        mRecyclerViewIncome.setLayoutManager(mManager);

        final TextView monthlyIncome = view.findViewById(R.id.income_total);


        mViewModel.getMonthlyIncomeTotal(selectedMonth).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                monthlyIncome.setText(String.valueOf(integer));
            }
        });

        mViewModel.getMonthIncomeHistory(selectedMonth).observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setHistory(histories);
            }
        });

    }

}
