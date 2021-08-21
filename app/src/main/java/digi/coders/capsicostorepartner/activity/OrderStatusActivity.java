package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.adapter.OrderDetailAdapter;
import digi.coders.capsicostorepartner.adapter.ProductAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityOrderStatusBinding;

public class OrderStatusActivity extends AppCompatActivity {

    ActivityOrderStatusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //load item list

        loadItemList();


        //
        binding.markAsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderStatusActivity.this, "Food Prepared", Toast.LENGTH_SHORT).show();
            }
        });
        binding.trackRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderStatusActivity.this,TrackRiderActivity.class));

            }
        });

    }

    private void loadItemList() {
        binding.itemList.setLayoutManager(new LinearLayoutManager(OrderStatusActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.itemList.setAdapter(new OrderDetailAdapter());
    }
}