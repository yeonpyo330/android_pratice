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
import com.example.savemoneyproject.databinding.MonthIncomeFragmentBinding;
import java.util.List;

public class MonthIncomeFragment extends Fragment {

    private MonthHistoryViewModel mViewModel;
    private MyAdapter adapter;
    private MonthIncomeFragmentBinding monthIncomeBinding;
    private String selectedMonth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        monthIncomeBinding = MonthIncomeFragmentBinding.inflate(inflater,container,false);
        adapter = new MyAdapter(getActivity());
        return monthIncomeBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MonthHistoryViewModel.class);
        selectedMonth = ((MonthlyActivity) getActivity()).sendMonth();
        if (selectedMonth != null) {
            setAdapter();
            getIncomeTotal();
            getIncomeHistory();
        }
    }

    // todo : separate setting and update logic -> Resolved
    public void setAdapter() {
        monthIncomeBinding.incomeRecyclerview.setAdapter(adapter);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        monthIncomeBinding.incomeRecyclerview.setLayoutManager(mManager); }


    public void getIncomeTotal(){
        mViewModel.getMonthlyIncomeTotal(selectedMonth).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                monthIncomeBinding.incomeTotal.setText(String.valueOf(integer));
            }
        });
    }

    public void getIncomeHistory() {
        mViewModel.getMonthIncomeHistory(selectedMonth).observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setHistory(histories);
            }
        });
    }

}
