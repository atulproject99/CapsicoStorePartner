package digi.coders.capsicostorepartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.OrderSummaryActivity;
import digi.coders.capsicostorepartner.databinding.TransactionDesignBinding;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyHolder> {

    private Context ctx;
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        ctx=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_design,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull TransactionAdapter.MyHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public  class MyHolder extends RecyclerView.ViewHolder
    {

        TransactionDesignBinding binding;
        public MyHolder(@NonNull  View itemView) {
            super(itemView);
            binding=TransactionDesignBinding.bind(itemView);

        }
    }
}
