package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.adapter.ReviewAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityMyReviewBinding;

public class MyReviewActivity extends AppCompatActivity {

    ActivityMyReviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //handle back

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //handel list
        loadReviewList();

    }

    private void loadReviewList() {
        binding.reviewList.setLayoutManager(new LinearLayoutManager(MyReviewActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.reviewList.setAdapter(new ReviewAdapter());
    }
}