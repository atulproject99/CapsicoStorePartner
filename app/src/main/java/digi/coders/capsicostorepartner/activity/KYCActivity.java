package digi.coders.capsicostorepartner.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.databinding.ActivityKycactivityBinding;

public class KYCActivity extends AppCompatActivity {

    ActivityKycactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityKycactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //handle next button
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KYCActivity.this,KYCStatusActivity.class));
            }
        });



        //handle back button
        binding.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //handle adhar card proof
        binding.addFrontPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(1);
            }
        });
        
        binding.addBackPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(2);
            }
        });


        //handle bank proof
        binding.addBankProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gallery(3);
            }
        });

        //handle pancard
        binding.addFrotPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gallery(4);
            }
        });
    }

    private void gallery(int i) {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        else
        {
            openGallery(i);
        }
    }

    private void openGallery(int i) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null)
        {
            handleGalleryPic(requestCode,data);
        }
    }

    private void handleGalleryPic(int i, Intent data) {
        switch (i)
        {
            case 1:
                binding.frontPhoto.setImageURI(data.getData());
                binding.frontPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(KYCActivity.this,FullImageViewActivity.class);
                        Uri uri=data.getData();
                        in.putExtra("data",uri.toString());
                        startActivity(in);
                    }
                });

                break;

            case 2:
                binding.backPhoto.setImageURI(data.getData());
                binding.backPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(KYCActivity.this,FullImageViewActivity.class);
                        Uri uri=data.getData();
                        in.putExtra("data",uri.toString());
                        startActivity(in);
                    }
                });

                break;
            case 3:
                binding.bankProof.setImageURI(data.getData());
                binding.bankProof.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(KYCActivity.this,FullImageViewActivity.class);
                        Uri uri=data.getData();
                        in.putExtra("data",uri.toString());
                        startActivity(in);
                    }
                });

                break;
            case 4:
                binding.frontPan.setImageURI(data.getData());
                binding.frontPan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(KYCActivity.this,FullImageViewActivity.class);
                        Uri uri=data.getData();
                        in.putExtra("data",uri.toString());
                        startActivity(in);
                    }
                });

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
        }
        else
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
    }
}