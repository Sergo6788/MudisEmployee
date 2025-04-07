package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.BannerViewBinding;
import com.example.mudisemployee.databinding.FragmentEditBannerBinding;
import com.example.mudisemployee.model.BannerModel;
import com.example.mudisemployee.model.MenuModel;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditBannerFragment extends Fragment {

    private FragmentEditBannerBinding binding;
    private FirebaseFirestore firestore;
    private Boolean isUpdate = false;
    private BannerModel bannerModel = new BannerModel("", "", "0", "");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBannerBinding.inflate(inflater);
        firestore = FirebaseFirestore.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        isUpdate = getArguments().getBoolean("isAdd");
        if (isUpdate) {
            getBanner(getArguments().getString("BannerId", "Banner1"));
        }
    }

    private void applyClick() {

    }

    private void getBanner(String id) {
        firestore.collection("Discounts").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    bannerModel = documentSnapshot.toObject(BannerModel.class);
                    bannerModel.setId(id);
                });
    }
}
