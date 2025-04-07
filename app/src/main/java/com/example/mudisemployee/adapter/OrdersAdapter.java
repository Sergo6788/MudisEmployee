package com.example.mudisemployee.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mudisemployee.R;
import com.example.mudisemployee.databinding.RvOrderBinding;
import com.example.mudisemployee.model.MenuModel;
import com.example.mudisemployee.model.OrderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private List<OrderModel> orders;
    private Context context;
    private OnClickListener onClickListener;

    public OrdersAdapter(List<OrderModel> orders, Context context, OnClickListener onClickListener) {
        this.orders = orders;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvOrderBinding binding = RvOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrdersAdapter.ViewHolder(binding);

    }

    public interface OnClickListener {
        void click(OrderModel order);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        if(!orders.get(position).getOrderMenu().isEmpty()){
            holder.bind(orders.get(position), context);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.click(orders.get(position));
        });


    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RvOrderBinding binding;
        private final SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        public ViewHolder(RvOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderModel order, Context context) {
            Glide.with(binding.image)
                    .load(order.getOrderMenu().get(0).getImage())
                    .into(binding.image);
            binding.tvPayment.setText(order.getPaymentMethod());
            binding.tvMealsList.setText(arrToStr(order.getOrderMenu().stream().map(MenuModel::getName).toList()));
            binding.tvNumber.setText("№" + order.getId().toString());
            binding.tvTime.setText(order.getOrderDate());
            try {
                if (timeFormat.parse(order.getOrderDate()).before(new Date())) {
                    binding.tvPayment.setTextColor(context.getResources().getColor(R.color.main_color));
                    binding.getRoot().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.late_process_order_bg));
                    binding.tvStatus.setText(new StringBuilder().append(context.getString(R.string.status)).append(" ").append(context.getString(R.string.late)).toString());
                } else {
                    binding.tvStatus.setText(new StringBuilder().append(context.getString(R.string.status)).append(" ").append(context.getString(R.string.in_process)).toString());
                }
            } catch (ParseException exception) {
                Log.d("Error", exception.getMessage());
            }


        }

        private String arrToStr(List<String> list) {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Integer> listIndex = new ArrayList<>();
            Integer itemCount = 0;
            for (int i = 0; i < list.size(); i++) {
                if (!listIndex.contains(i)) {
                    for (int j = i; j < list.size(); j++) {
                        if (Objects.equals(list.get(j), list.get(i))) {
                            listIndex.add(j);
                            itemCount++;
                        }
                    }
                    stringBuilder.append(list.get(i)).append(" x").append(itemCount).append("\n");
                    itemCount = 0;
                }
            }
            return stringBuilder.toString();
        }
    }
}
