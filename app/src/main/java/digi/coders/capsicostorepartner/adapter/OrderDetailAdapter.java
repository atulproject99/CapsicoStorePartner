package digi.coders.capsicostorepartner.adapter;


import android.animation.LayoutTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.databinding.OrderDetailsDesignBinding;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyHolder> {
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_design,parent,false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        OrderDetailsDesignBinding binding;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            binding=OrderDetailsDesignBinding.bind(itemView);
        }
    }
}
