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
import com.example.mudisemployee.databinding.BannerViewBinding;
import com.example.mudisemployee.databinding.RvFoodViewBinding;
import com.example.mudisemployee.model.BannerModel;
import com.example.mudisemployee.model.MenuModel;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private BannerAdapter.OnClickListener onClickListener;
    private List<BannerModel> discounts;
    private Context context;

    public BannerAdapter(List<BannerModel> discounts, BannerAdapter.OnClickListener onClickListener, Context context) {
        this.discounts = discounts;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BannerViewBinding binding = BannerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        holder.bind(discounts.get(position));
        holder.binding.ivMore.setOnClickListener(v -> {
            Context wrapper = new ContextThemeWrapper(context, R.style.PopupStyle);
            PopupMenu popup = new PopupMenu(wrapper, holder.binding.ivMore);
            popup.inflate(R.menu.options_menu);
            popup.setOnMenuItemClickListener(l -> {
                if (l.toString().equals(context.getResources().getString(R.string.update))) {
                    onClickListener.update(discounts.get(position));
                } else onClickListener.delete(discounts.get(position));
                return true;
            });
            popup.show();
        });


    }


    public interface OnClickListener {
        void update(BannerModel bannerModel);

        void delete(BannerModel bannerModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public BannerViewBinding binding;

        public ViewHolder(BannerViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BannerModel discount) {
            Glide.with(binding.ivPicture)
                    .load(discount.getImage())
                    .into(binding.ivPicture);
            binding.tvTitle.setText(discount.getTitle());
            binding.tvDescription.setText(discount.getDescription());
            binding.tvDate.setText(discount.getDate().subSequence(0,discount.getDate().length()-7));
        }

    }
}
