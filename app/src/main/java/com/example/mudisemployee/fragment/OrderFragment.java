package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.adapter.DishAdapter;
import com.example.mudisemployee.adapter.OrdersAdapter;
import com.example.mudisemployee.app.App;
import com.example.mudisemployee.databinding.FragmentOrderBinding;
import com.example.mudisemployee.model.MenuModel;
import com.example.mudisemployee.model.OrderModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;
    private OrderModel orderModel;
    private FirebaseRepository firebaseDataBase;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.actionId = R.id.action_orderFragment_to_mainFragment;
        Type type = new TypeToken<OrderModel>() {
        }.getType();
        orderModel = new Gson().fromJson(getArguments().getString("order", ""), type);
        applyClick();
        setUpObservers();
        setUpView();

    }

    private void setUpView() {
        firebaseDataBase.getDishes(orderModel.getOrderMenu().stream().map(MenuModel::getName).toList());
        binding.tvPayment.setText(orderModel.getPaymentMethod());
        binding.tvNumber.setText("№" + orderModel.getId().toString());
        binding.tvTime.setText(orderModel.getOrderDate());
        try {
            if (timeFormat.parse(orderModel.getOrderDate()).before(new Date())) {
                binding.tvPayment.setTextColor(requireContext().getResources().getColor(R.color.main_color));
                binding.tvStatus.setText(new StringBuilder().append(requireContext().getString(R.string.status)).append(" ").append(requireContext().getString(R.string.late)).toString());
            } else {
                binding.tvStatus.setText(new StringBuilder().append(requireContext().getString(R.string.status)).append(" ").append(requireContext().getString(R.string.in_process)).toString());
            }
        }catch (ParseException exception){
            Log.d("Error", exception.getMessage());
        }



    }

    private void deleteOrder() {

        App.sharedManager.saveArchive(orderModel, false);
        Navigation.findNavController(requireView()).popBackStack();
        App.actionId = -1;
        FirebaseFirestore.getInstance().collection("Orders").document(orderModel.getId())
                .update("orderStatus","CANCELED")
                .addOnCompleteListener(task ->{
                   if(task.isSuccessful()){
                       orderModel.setStatus("CANCELED");
                       App.sharedManager.saveArchive(orderModel, false);
                   }else {
                       Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                   }
                });

    }
    private void applyClick(){
        binding.btConfirm.setOnClickListener(v -> {
            deleteOrder();
        });
    }
    private void setAdapter(){
        binding.rvDish.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDish.setAdapter(new DishAdapter(firebaseDataBase.dishInfo,requireContext()));
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