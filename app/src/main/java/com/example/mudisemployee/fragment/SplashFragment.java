package com.example.mudisemployee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisemployee.R;
import com.example.mudisemployee.app.App;
import com.example.mudisemployee.databinding.FragmentSplashBinding;


public class SplashFragment extends Fragment {
    private FragmentSplashBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentSplashBinding.inflate(inflater);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkIsLogin();
    }

    private void checkIsLogin() {
        if (App.sharedManager.isUserAuthorized()) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_splashFragment_to_mainFragment);
        } else
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_splashFragment_to_loginFragment);
    }
}
