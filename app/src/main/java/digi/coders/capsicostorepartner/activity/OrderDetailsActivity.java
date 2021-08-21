package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.adapter.OrderDetailAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityOrderDetailsBinding;
import digi.coders.capsicostorepartner.databinding.CancelReasonLayoutBinding;

public class OrderDetailsActivity extends AppCompatActivity {

    ActivityOrderDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //load neworder item list

        loadNewOrder();



        //accept order
        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderDetailsActivity.this, "Order Accepted", Toast.LENGTH_SHORT).show();
            }
        });

        //reject order
        binding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aler = new AlertDialog.Builder(OrderDetailsActivity.this);
                AlertDialog alertDialog = aler.create();
                View view = LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.cancel_reason_layout, null);
                CancelReasonLayoutBinding binding = CancelReasonLayoutBinding.bind(view);
                binding.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                binding.submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(OrderDetailsActivity.this, "Successfulky post reason", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
            }
        });

    }

    private void loadNewOrder() {
        binding.newOrderItemList.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.newOrderItemList.setAdapter(new OrderDetailAdapter());
    }
}