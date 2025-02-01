package com.example.mudisemployee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.RvDishBinding;
import com.example.mudisemployee.databinding.RvOrderBinding;
import com.example.mudisemployee.model.MenuModel;
import com.example.mudisemployee.model.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    private HashMap<String,String> dishes;
    private Context context;
    public DishAdapter(HashMap<String,String> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }
    @NonNull
    @Override
    public DishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvDishBinding binding = RvDishBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DishAdapter.ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(@NonNull DishAdapter.ViewHolder holder, int position) {
        holder.bind(dishes.entrySet().stream().toList().get(position),context);


    }


    @Override
    public int getItemCount() {
        return dishes.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RvDishBinding binding;

        public ViewHolder(RvDishBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Map.Entry<String,String> dish, Context context){
            Glide.with(binding.image)
                    .load(dish.getValue())
                    .into(binding.image);
            binding.tvName.setText(dish.getKey());
        }

    }
}
