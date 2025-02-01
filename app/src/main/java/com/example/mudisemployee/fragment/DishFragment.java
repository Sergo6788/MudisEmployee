package com.example.mudisemployee.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.FragmentDishBinding;
import com.example.mudisemployee.model.MenuModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicInteger;


public class DishFragment extends Fragment implements TextWatcher {

    private FragmentDishBinding binding;
    private Boolean isUpdate = false;
    private MenuModel menuModel = new MenuModel("", "", "0", "SNACK");
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String correctedText="0";
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(binding.costEditText.getText().toString()!=binding.costBar.getProgress()+"") {
            int maxProgress = binding.costBar.getMax();
            if(binding.costEditText.getText().toString().isEmpty()){
                correctedText="0";
            }
            else{
                correctedText=binding.costEditText.getText().toString();
            }
            binding.costEditText.getText().toString();
            if (Integer.parseInt(correctedText) <= maxProgress && Integer.parseInt(correctedText) >= binding.costBar.getMin()) {
                binding.costBar.setProgress(Integer.parseInt(correctedText));
            }


        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDishBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        isUpdate = getArguments().getBoolean("isAdd");
        if (isUpdate) {
            getDish(getArguments().getString("DishId", "Dish1"));
        }
        binding.costEditText.addTextChangedListener(this);

    }

    private boolean checkData() {
        String[] strings = binding.etPicture.getText().toString().split(" ");
        if (strings.length == 0 || binding.etPicture.getText().toString().isEmpty()) {
            return false;
        }
        strings = binding.etName.getText().toString().split(" ");
        if (strings.length == 0 || binding.etName.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void applyClick() {
        binding.btConfirm.setOnClickListener(v -> {
            if (checkData()) {
                createDish();

            } else {
                Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
            }

        });
        binding.costBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekBar.getProgress() > Integer.parseInt(binding.costEditText.getText().toString()) || b){
                    binding.costEditText.setText(seekBar.getProgress()+"");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.etType.setOnClickListener(v ->{
           openMealPopup();
        });
    }
    private void openMealPopup(){
        Context wrapper = new ContextThemeWrapper(requireContext(),R.style.PopupStyle);
        PopupMenu popup = new PopupMenu(wrapper,binding.etType);
        popup.inflate(R.menu.meal_type_menu);
        popup.setOnMenuItemClickListener(l->{
            binding.etType.setText(l.toString());
            return true;
        });
        popup.show();
    }

    private void createDish() {

        if (isUpdate) {
            firestore.collection("Dishes").document(menuModel.getId()).set(menuModel);
        } else {
            AtomicInteger n = new AtomicInteger(1);
            firestore.collection("Dishes").get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                n.getAndIncrement();
                            }
                            menuModel = new MenuModel(binding.etPicture.getText().toString(),binding.etName.getText().toString(),correctedText,binding.etType.getText().toString());
                            menuModel.setId("Dish" + n);
                            menuModel.setReady(binding.checkboxIsReady.isChecked());
                            firestore.collection("Dishes").document("Dish" + n).set(menuModel);

                        }else{
                            Toast.makeText(requireContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void setUpView() {
        binding.tvAddDish.setText(requireContext().getResources().getString(R.string.update_dish));
        binding.etPicture.setText(menuModel.getImage());
        binding.etName.setText(menuModel.getName());
        binding.etType.setText(menuModel.getType());
        binding.costEditText.setText(String.valueOf(menuModel.getPrice()));
        binding.checkboxIsReady.setChecked(menuModel.isReady());
        binding.checkboxIsReady.setVisibility(View.VISIBLE);

    }

    private void getDish(String id) {
        firestore.collection("Dishes").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    menuModel = documentSnapshot.toObject(MenuModel.class);
                    menuModel.setId(documentSnapshot.getId());
                    setUpView();
                });
    }
}