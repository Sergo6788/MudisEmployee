package com.example.mudisemployee.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.adapter.OrdersAdapter;
import com.example.mudisemployee.app.App;
import com.example.mudisemployee.databinding.FragmentLoginBinding;
import com.example.mudisemployee.databinding.FragmentMainBinding;
import com.example.mudisemployee.model.OrderModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainFragment extends Fragment implements OrdersAdapter.OnClickListener {
    private FragmentMainBinding binding;
    private final Handler handler = new Handler();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    private FirebaseRepository firebaseDataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        setUpObservers();
        firebaseDataBase.getOrders();
        startDataChecking();
    }
    @Override
    public void click(OrderModel order) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String orderString = gson.toJson(order);
        bundle.putString("order",orderString);
        Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_orderFragment,bundle);
        firebaseDataBase.orders.clear();


    }


    private void applyClick(){

    }
    private void setAdapter(){
        binding.rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvOrders.setAdapter(new OrdersAdapter(firebaseDataBase.orders,requireContext(),this));
    }

    private void startDataChecking(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = timeFormat.format(new Date());
                binding.currentTime.setText(currentTime);
                handler.postDelayed(this,1000);
            }
        },0);
    }
    private void endDataChecking(){
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        endDataChecking();
    }
    private void setUpObservers(){
        firebaseDataBase.isTaskReady.observe(getViewLifecycleOwner(),data->{
            if (data) {
                setAdapter();
                firebaseDataBase.isTaskReady.setValue(false);
            }
        });
    }
}
