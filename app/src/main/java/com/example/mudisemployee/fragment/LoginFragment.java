package com.example.mudisemployee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisemployee.R;
import com.example.mudisemployee.app.App;
import com.example.mudisemployee.databinding.FragmentLoginBinding;
import com.example.mudisemployee.model.FireStoreUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = true;
    private FirebaseAuth auth;
    private final FirebaseFirestore dataBase = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applyClick();
    }

    private void applyClick() {

        binding.btSignIn.setOnClickListener(v -> {
            checkEnterData();
        });
        binding.tvButtonForgot.setOnClickListener(v -> {

        });
        binding.eyePassword.setOnClickListener(v -> {

            if (isPasswordVisible) {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_open_img_svg));

                binding.etPassword.setTransformationMethod(null);
            } else {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_closed_img_svg));

                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordVisible = !
                    isPasswordVisible;


        });


    }


    private void checkEnterData() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()) {
            Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.email_incorrect), Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6 || binding.etPassword.getText().toString().contains(" ")) {

            Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.password_length_must_be_more_than_6_characters_and_without_space), Toast.LENGTH_SHORT).show();
        } else {
            allCorrect();
        }
    }

    private void allCorrect() {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(requireActivity(), task -> {
            binding.btSignIn.setEnabled(false);
            if (task.isSuccessful()) {
                createUser();
            }
        }).addOnFailureListener(requireActivity(), error -> {
            binding.btSignIn.setEnabled(true);
            Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        });

    }

    private void createUser() {
        FireStoreUser user = new FireStoreUser(true);
        String uid = auth.getCurrentUser().getUid();
        dataBase.collection("Users").document(uid).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_mainFragment);
                        App.sharedManager.userAuthorize();
                    } else {
                        binding.btSignIn.setEnabled(true);
                        Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.bad_internet_connection_please_try_again_later), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}


