package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.FragmentDishBinding;


public class DishFragment extends Fragment {

    private FragmentDishBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDishBinding.inflate(inflater);
        return binding.getRoot();
    }
}