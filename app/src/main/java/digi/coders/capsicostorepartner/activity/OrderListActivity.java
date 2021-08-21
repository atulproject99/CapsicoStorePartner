package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.adapter.OrderHistoryAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityOrderListBinding;

public class OrderListActivity extends AppCompatActivity {

    ActivityOrderListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //load order History list
        loadOrderHistoryList();

    }

    private void loadOrderHistoryList() {
        binding.orderHistoryList.setLayoutManager(new LinearLayoutManager(OrderListActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.orderHistoryList.setAdapter(new OrderHistoryAdapter());
    }
}