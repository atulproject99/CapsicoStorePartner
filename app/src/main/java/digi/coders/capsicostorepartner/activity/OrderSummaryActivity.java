package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.adapter.OrderDetailAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityOrderSummaryBinding;

public class OrderSummaryActivity extends AppCompatActivity {

    ActivityOrderSummaryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //handle back button
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //load Item List
        loadItemList();

    }

    private void loadItemList() {
        binding.itemList.setLayoutManager(new LinearLayoutManager(OrderSummaryActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.itemList.setAdapter(new OrderDetailAdapter());
    }
}