package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.BannerViewBinding;
import com.example.mudisemployee.databinding.FragmentEditBannerBinding;
import com.example.mudisemployee.model.BannerModel;
import com.example.mudisemployee.model.MenuModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicInteger;


public class EditBannerFragment extends Fragment {

    private FragmentEditBannerBinding binding;
    private FirebaseFirestore firestore;
    private Boolean isUpdate = false;
    private BannerModel bannerModel = new BannerModel("", "", "", "");


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
        else  setUpView();
    }

    private void applyClick() {
        binding.btConfirm.setOnClickListener(v->{
            createBanner();
        });

    }

    private void getBanner(String id) {
        firestore.collection("Discounts").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    bannerModel = documentSnapshot.toObject(BannerModel.class);
                    bannerModel.setId(id);
                    setUpView();
                });
    }

    private void createBanner() {

        if (isUpdate) {
            String id = bannerModel.getId();
            bannerModel = new BannerModel(binding.etPicture.getText().toString(), binding.etName.getText().toString(), binding.etDescription.getText().toString(), binding.dateEditText.getText().toString());
            firestore.collection("Discounts").document(id).set(bannerModel)
                    .addOnCompleteListener(task ->{
                        if(task.isSuccessful()){
                            Navigation.findNavController(requireView()).popBackStack();
                        }
                        else Toast.makeText(requireContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    });

        } else {
            AtomicInteger n = new AtomicInteger(1);
            firestore.collection("Discounts").get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                n.getAndIncrement();
                            }
                            bannerModel = new BannerModel(binding.etPicture.getText().toString(), binding.etName.getText().toString(), binding.etDescription.getText().toString(), binding.dateEditText.getText().toString());
                            bannerModel.setId("Banner" + n);
                            firestore.collection("Discounts").document("Banner" + n).set(bannerModel);
                            Navigation.findNavController(requireView()).popBackStack();
                        } else {
                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void setUpView() {
        binding.etPicture.setText(bannerModel.getImage());
        binding.etName.setText(bannerModel.getTitle());
        binding.dateEditText.setText(bannerModel.getDate());
        binding.etDescription.setText(bannerModel.getDescription());
        if(isUpdate){
            binding.tvAddDish.setText(requireContext().getResources().getString(R.string.update_banner));
        }

    }
}
