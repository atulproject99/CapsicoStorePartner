package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import digi.coders.capsicostorepartner.databinding.ActivityProductFullDetailBinding;
import digi.coders.capsicostorepartner.helper.Constraints;
import digi.coders.capsicostorepartner.helper.MyApi;
import digi.coders.capsicostorepartner.model.Product;
import digi.coders.capsicostorepartner.model.ShowProgress;
import digi.coders.capsicostorepartner.model.Vendor;
import digi.coders.capsicostorepartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFullDetailActivity extends AppCompatActivity {

    ActivityProductFullDetailBinding binding;
    public static Product product;
    private SingleTask singleTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductFullDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        singleTask=(SingleTask)getApplication();

        setData();
        //
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });


        binding.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    binding.status.setText("On");
                    updateStaus("true");
                }
                else
                {
                    binding.status.setText("Off");
                    updateStaus("false");


                }
            }

        });


        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(ProductFullDetailActivity.this,AddProductFormActivity.class);
                AddProductFormActivity.product=product;
                in.putExtra("key",2);
                startActivity(in);
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductFullDetailActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStaus(String status) {
        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        ShowProgress.getShowProgress(ProductFullDetailActivity.this).show();
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Log.e("sdsd",product.getId());
        Call<JsonArray> call=myApi.productStatus(product.getId(),status,vendor.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        String res=jsonObject.getString("res");
                        String msg=jsonObject.getString("message");
                        ShowProgress.getShowProgress(ProductFullDetailActivity.this).hide();
                        if(res.equals("success"))
                        {

                            Toast.makeText(ProductFullDetailActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(ProductFullDetailActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                ShowProgress.getShowProgress(ProductFullDetailActivity.this).hide();
                Toast.makeText(ProductFullDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData() {
        binding.productName.setText(product.getName());
        binding.productTitle.setText(product.getTitle());
        binding.proName.setText(product.getName());
        binding.prodcutMrp.setText("₹"+product.getMrp());
        binding.productOfferPrice.setText("₹"+product.getPrice());
        binding.discount.setText(product.getDiscount());
        binding.cgst.setText(product.getCgst());
        binding.sgst.setText(product.getSgst());
        binding.gstPrice.setText("₹"+product.getGst());
        binding.sellingPrice.setText("₹"+product.getSellPrice());
        binding.productCategory.setText(product.getCategoriesname());
        binding.productSubcategory.setText(product.getSubcategoriesname());
        Picasso.get().load(Constraints.BASE_URL+Constraints.MASTER_PRODUCT+product.getIcon()).into(binding.productImage);
        if(product.getIsStatus().equals("true"))
        {
            binding.status.setChecked(true);
            binding.status.setText("On");
        }
        else
        {
            binding.status.setChecked(false);
            binding.status.setText("Off");
        }

    }
}