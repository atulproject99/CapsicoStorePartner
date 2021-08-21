package digi.coders.capsicostorepartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.OrderSummaryActivity;
import digi.coders.capsicostorepartner.databinding.OrderHistoryLayoutBinding;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyHolder> {
    private Context ctx;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(ctx,OrderSummaryActivity.class));

            }
        });
    holder.binding.viewOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ctx.startActivity(new Intent(ctx, OrderSummaryActivity.class));
        }
    });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        OrderHistoryLayoutBinding binding;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            binding=OrderHistoryLayoutBinding.bind(itemView);

        }
    }
}
