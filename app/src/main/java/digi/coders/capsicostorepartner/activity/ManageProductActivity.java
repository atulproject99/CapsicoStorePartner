package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import digi.coders.capsicostorepartner.adapter.ProductAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityManageProductBinding;
import digi.coders.capsicostorepartner.helper.MyApi;
import digi.coders.capsicostorepartner.model.Product;
import digi.coders.capsicostorepartner.model.ShowProgress;
import digi.coders.capsicostorepartner.model.Vendor;
import digi.coders.capsicostorepartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductActivity extends AppCompatActivity {

    ActivityManageProductBinding binding;
    private SingleTask singleTask;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityManageProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        singleTask=(SingleTask)getApplication();
        //hanlde back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //load Product list
        loadProduct();

        //addproduct
        binding.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageProductActivity.this,AddProductActivity.class));
            }
        });
    }
    private void loadProduct() {
        ShowProgress.getShowProgress(ManageProductActivity.this).show();
        String js=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(js,Vendor.class);

        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call=myApi.getAllProduct(vendor.getId());
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

                        if(res.equals("success"))
                        {

                            ShowProgress.getShowProgress(ManageProductActivity.this).hide();
                            productList=new ArrayList<>();
                            JSONArray jsonArray1=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                Product product=new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),Product.class);
                                productList.add(product);
                            }

                            binding.productList.setLayoutManager(new LinearLayoutManager(ManageProductActivity.this,LinearLayoutManager.VERTICAL,false));
                            binding.productList.setAdapter(new ProductAdapter(productList));


                        }
                        else
                        {
                            ShowProgress.getShowProgress(ManageProductActivity.this).hide();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                ShowProgress.getShowProgress(ManageProductActivity.this).hide();
                Toast.makeText(ManageProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}