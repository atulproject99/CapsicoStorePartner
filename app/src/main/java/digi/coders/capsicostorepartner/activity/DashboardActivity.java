package digi.coders.capsicostorepartner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.databinding.ActivityDashboardBinding;
import digi.coders.capsicostorepartner.fragment.AccountFragment;
import digi.coders.capsicostorepartner.fragment.BookingsFragment;
import digi.coders.capsicostorepartner.fragment.HomeFragment;
import digi.coders.capsicostorepartner.fragment.OfflineFragment;
import digi.coders.capsicostorepartner.fragment.WalletFragment;
import digi.coders.capsicostorepartner.helper.MyApi;
import digi.coders.capsicostorepartner.model.ShowProgress;
import digi.coders.capsicostorepartner.model.Vendor;
import digi.coders.capsicostorepartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    Fragment fragment=null;
    private SingleTask singleTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        singleTask=(SingleTask)getApplication(); 
        //bottomnaviation




        exchangeFragment(new OfflineFragment());

        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        if(vendor!=null)
        {
            loadMyProfile();
        }


        //handle switch of store

        
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        if(binding.onOff.isChecked())
                        {
                            fragment=new HomeFragment();

                        }
                        else
                        {
                            fragment=new OfflineFragment();
                        }

                        break;
                    case R.id.wallet:
                        fragment=new WalletFragment();
                        break;
                    case R.id.booking:
                        fragment=new BookingsFragment();
                        break;
                    case R.id.account:
                        fragment=new AccountFragment();
                        break;

                }
                exchangeFragment(fragment);
                return true;
            }
        });



    }

    private void loadMyProfile() {

        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        ShowProgress.getShowProgress(DashboardActivity.this).show();
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call=myApi.profile(vendor.getId());
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
                        ShowProgress.getShowProgress(DashboardActivity.this).hide();
                        if(res.equals("success"))
                        {

                            JSONArray jsonArray1=jsonObject.getJSONArray("data");
                            Vendor vendor1=new Gson().fromJson(jsonArray1.getJSONObject(0).toString(),Vendor.class);
                            JSONObject jsonObject1=jsonArray1.getJSONObject(0);
                            singleTask.addValue("vendor",jsonObject1);

                            if(vendor.getIsStatus().equals("true"))
                            {
                                binding.onOff.setChecked(true);
                            }
                            else
                            {
                                binding.onOff.setChecked(false);

                            }
                            binding.onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(isChecked)
                                    {
                                        exchangeFragment(new HomeFragment());
                                        binding.onOff.setText("Open");
                                        binding.bottomNavigation.setSelectedItemId(R.id.home);
                                        updateStatus("true");
                                    }
                                    else
                                    {
                                        exchangeFragment(new OfflineFragment());
                                        binding.onOff.setText("Close");
                                        binding.bottomNavigation.setSelectedItemId(R.id.home);
                                        updateStatus("false");
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                ShowProgress.getShowProgress(DashboardActivity.this).hide();
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStatus(String status) {
        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        ShowProgress.getShowProgress(DashboardActivity.this).show();
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call=myApi.merchantOpenStatus(vendor.getId(),status);
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
                        ShowProgress.getShowProgress(DashboardActivity.this).hide();
                        if(res.equals("success"))
                        {

                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                ShowProgress.getShowProgress(DashboardActivity.this).hide();
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        
        
    }

    public void exchangeFragment(androidx.fragment.app.Fragment fragment){
        if(fragment!=null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }
    }


}