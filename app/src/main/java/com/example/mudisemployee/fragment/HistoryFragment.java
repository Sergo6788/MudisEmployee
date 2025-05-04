package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisemployee.R;
import com.example.mudisemployee.adapter.OrdersAdapter;
import com.example.mudisemployee.app.App;
import com.example.mudisemployee.databinding.FragmentHistoryBinding;
import com.example.mudisemployee.model.OrderModel;
import com.example.mudisemployee.shared.SharedManager;


public class HistoryFragment extends Fragment implements OrdersAdapter.OnClickListener {
    FragmentHistoryBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
    }

    private void setAdapter(){

       binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvOrders.setAdapter(new OrdersAdapter(App.sharedManager.getListArchive(),requireContext(),this));
    }

    @Override
    public void click(OrderModel order) {

    }
}