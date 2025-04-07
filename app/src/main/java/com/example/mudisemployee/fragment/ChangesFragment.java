package com.example.mudisemployee.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisemployee.FirebaseRepository;
import com.example.mudisemployee.R;
import com.example.mudisemployee.adapter.FoodAdapter;
import com.example.mudisemployee.databinding.FragmentChangesBinding;
import com.example.mudisemployee.model.MenuModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ChangesFragment extends Fragment implements FoodAdapter.OnClickListener {
    FragmentChangesBinding binding;
    FirebaseRepository firebaseRepository;
    ArrayList<MenuModel> menuList = new ArrayList();

    public void update(MenuModel menuModel) {
        Bundle bundle = new Bundle();
        bundle.putString("DishId", menuModel.getId());
        bundle.putBoolean("isAdd", true);
        Navigation.findNavController(requireView()).navigate(R.id.action_changesFragment_to_dishFragment, bundle);
    }

    public void delete(MenuModel menuModel) {
        FirebaseFirestore.getInstance().collection("Dishes").document(menuModel.getId()).update("ready", false)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for(int i=0; i<menuList.size();i++){
                            if(menuList.get(i).getId() == menuModel.getId()){
                                menuList.get(i).setReady(false);
                                binding.rvDishes.getAdapter().notifyItemChanged(i);
                            }
                        }

                    } else {
                        Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangesBinding.inflate(inflater);
        firebaseRepository = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        applyClick();
        firebaseRepository.getMenu();
    }

    @Override
    public void onPause() {
        super.onPause();
        menuList.clear();
    }

    private void applyClick() {
        binding.ivAdd.setOnClickListener(this::clickAdd);
    }

    private void clickAdd(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdd", false);
        Navigation.findNavController(view).navigate(R.id.action_changesFragment_to_dishFragment, bundle);


    }

    public void setAdapter(List<MenuModel> menuModels) {
        menuList.addAll(menuModels);
        binding.rvDishes.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDishes.setAdapter(new FoodAdapter(menuList, this, requireContext()));

    }

    private void setObservers() {
        firebaseRepository.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                binding.pbLoad.setVisibility(View.GONE);
                setAdapter(firebaseRepository.getMenuList());
                firebaseRepository.isTaskReady.setValue(false);
            }
        });
    }
}