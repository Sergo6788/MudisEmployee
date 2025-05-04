package com.example.mudisemployee.adapter;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.RvFoodViewBinding;
import com.example.mudisemployee.model.MenuModel;

import java.util.HashMap;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private OnClickListener onClickListener;
    private List<MenuModel> dishes;
    private Context context;

    public FoodAdapter(List<MenuModel> dishes, OnClickListener onClickListener, Context context){
        this.dishes = dishes;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return dishes.size();
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvFoodViewBinding binding = RvFoodViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dishes.get(position), context);
        holder.binding.ivMore.setOnClickListener(v->{
            Context wrapper = new ContextThemeWrapper(context, R.style.PopupStyle);
            PopupMenu popup = new PopupMenu(wrapper,holder.binding.ivMore);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(l->{
                if(l.toString().equals(context.getResources().getString(R.string.update))){
                    onClickListener.update(dishes.get(position));
                }else onClickListener.delete(dishes.get(position));
                return true;
            });
            popup.show();
        });



    }
    public interface OnClickListener{
        void update(MenuModel menuModel);
        void delete(MenuModel menuModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RvFoodViewBinding binding;

        public ViewHolder(RvFoodViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MenuModel menuItem, Context context) {
            Glide.with(binding.image)
                    .load(menuItem.getImage())
                    .into(binding.image);
            binding.tvName.setText(menuItem.getName());
            if(!menuItem.isReady()){
                binding.getRoot().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.late_process_order_bg));
            }

        }

    }
}