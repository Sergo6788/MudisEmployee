package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.FragmentChangesBinding;


public class ChangesFragment extends Fragment {
    FragmentChangesBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick(){
       binding.addLayout.setOnClickListener(this::clickAdd);
       binding.tvAdd.setOnClickListener(this::clickAdd);
        binding.deleteLayout.setOnClickListener(this::clickDelete);
        binding.tvDelete.setOnClickListener(this::clickDelete);
        binding.updateLayout.setOnClickListener(this::clickUpdate);
        binding.tvUpdate.setOnClickListener(this::clickUpdate);
    }

    private void clickAdd(View view) {

    }
    private void clickUpdate(View view){

    }
    private void clickDelete(View view){

    }




}