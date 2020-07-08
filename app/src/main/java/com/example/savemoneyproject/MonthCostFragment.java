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
import com.example.savemoneyproject.databinding.MonthCostFragmentBinding;
import java.util.List;

// todo : monthlycost fragment && monthincomefragment -> view are identical
// todo : please consider abstract class or find a way to merge it
public class MonthCostFragment extends Fragment {

    private MonthHistoryViewModel mViewModel;
    private MyAdapter adapter;
    private MonthCostFragmentBinding monthCostBinding;
    private String selectedMonth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        monthCostBinding = MonthCostFragmentBinding.inflate(inflater, container,false);
        adapter = new MyAdapter(getActivity());
        return monthCostBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MonthHistoryViewModel.class);
        selectedMonth = ((MonthlyActivity)getActivity()).sendMonth();
        if (selectedMonth != null){
            setAdapter();
            getCostTotal();
            getCostHistory();
        }
    }

    public void setAdapter() {
        monthCostBinding.costRecyclerview.setAdapter(adapter);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getActivity());
        monthCostBinding.costRecyclerview.setLayoutManager(mManager);
    }

    public void getCostTotal() {
        mViewModel.getMonthlyCostTotal(selectedMonth).observe(getViewLifecycleOwner() ,new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                monthCostBinding.costTotal.setText(String.valueOf(integer));
            }
        });
    }

    public void getCostHistory() {

        mViewModel.getMonthCostHistory(selectedMonth).observe(getViewLifecycleOwner(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.setHistory(histories);
            }
        });
    }
}
