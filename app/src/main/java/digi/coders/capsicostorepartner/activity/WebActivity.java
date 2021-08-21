package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {

    ActivityWebBinding binding;
    private int key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        key=getIntent().getIntExtra("key",0);
        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if(key==1)
        {
            binding.toolbarText.setText("Terms and Conditions");
        }
        else if(key==2)
        {
            binding.toolbarText.setText("Privacy Policy");

        }
        else
        {
            binding.toolbarText.setText("About");

        }







    }
}