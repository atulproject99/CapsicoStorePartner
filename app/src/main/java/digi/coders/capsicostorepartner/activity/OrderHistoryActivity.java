package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.adapter.OrderDetailAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityOrderHistoryBinding;

public class OrderHistoryActivity extends AppCompatActivity {

    ActivityOrderHistoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //hanlde view order
        binding.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(OrderHistoryActivity.this,OrderSummaryActivity.class));
            }
        });
        binding.trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderHistoryActivity.this,TrackOrderActivity.class));
            }
        });

        //load item list
        loadItemList();
    }
    private void loadItemList() {
        binding.itemList.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.itemList.setAdapter(new OrderDetailAdapter());
    }
}