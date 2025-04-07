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

import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.adapter.BannerAdapter;
import com.example.mudisemployee.adapter.FoodAdapter;
import com.example.mudisemployee.databinding.FragmentSalesBinding;
import com.example.mudisemployee.model.BannerModel;
import com.example.mudisemployee.model.MenuModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SalesFragment extends Fragment implements BannerAdapter.OnClickListener {
    FragmentSalesBinding binding;
    FirebaseRepository firebaseRepository;
    ArrayList<BannerModel> banner = new ArrayList<>();


    @Override
    public void update(BannerModel bannerModel) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdd", true);
        bundle.putString("BannerId",bannerModel.getId());
        Navigation.findNavController(requireView()).navigate(R.id.action_salesFragment_to_editBannerFragment, bundle);


    }

    public void delete(BannerModel bannerModel) {
        FirebaseFirestore.getInstance().collection("Discounts").document(bannerModel.getId()).delete()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        banner.removeIf(it-> Objects.equals(it.getId(), bannerModel.getId()));
                        binding.rvSales.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalesBinding.inflate(inflater);
        firebaseRepository =new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        setObservers();
        firebaseRepository.getBannersFromFirebase();

    }

    private void applyClick() {
        binding.ivAdd.setOnClickListener(this::clickAdd);
    }
    private void clickAdd(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdd", false);
        Navigation.findNavController(view).navigate(R.id.action_salesFragment_to_editBannerFragment, bundle);


    }
    @Override
    public void onPause() {
        super.onPause();
        banner.clear();
    }

    public void setAdapter(List<BannerModel> banners) {
        banner.addAll(banners);
        binding.rvSales.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSales.setAdapter(new BannerAdapter(banner, this,requireContext()));

    }
    private void setObservers() {
        firebaseRepository.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                binding.pbLoad.setVisibility(View.GONE);
                setAdapter(firebaseRepository.getBanner());
                firebaseRepository.isTaskReady.setValue(false);


            }
        });
    }
}