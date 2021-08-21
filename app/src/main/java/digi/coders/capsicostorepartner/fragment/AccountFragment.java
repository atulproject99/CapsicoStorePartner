package digi.coders.capsicostorepartner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.DocumentActivity;
import digi.coders.capsicostorepartner.activity.LoginActivity;
import digi.coders.capsicostorepartner.activity.ManageProductActivity;
import digi.coders.capsicostorepartner.activity.MyReviewActivity;
import digi.coders.capsicostorepartner.activity.OrderHistoryActivity;
import digi.coders.capsicostorepartner.activity.OrderListActivity;
import digi.coders.capsicostorepartner.activity.OrderSummaryActivity;
import digi.coders.capsicostorepartner.activity.RestaurantMenuActivity;
import digi.coders.capsicostorepartner.activity.ViewAccountActivity;
import digi.coders.capsicostorepartner.activity.WebActivity;
import digi.coders.capsicostorepartner.databinding.FragmentAccountBinding;
import digi.coders.capsicostorepartner.databinding.OrderHistoryLayoutBinding;
import digi.coders.capsicostorepartner.helper.MyApi;
import digi.coders.capsicostorepartner.model.ShowProgress;
import digi.coders.capsicostorepartner.model.Vendor;
import digi.coders.capsicostorepartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    private SingleTask singleTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAccountBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            singleTask=(SingleTask)getActivity().getApplication();

        //handle view account
        binding.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), ViewAccountActivity.class));
            }
        });

        //handle document
        binding.viewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DocumentActivity.class));
            }
        });


        //handle manage product

        binding.manageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManageProductActivity.class));
            }
        });
        //hanle manage payment
        binding.managePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        //
        binding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(getActivity(), WebActivity.class);
            intent.putExtra("key",1);
            startActivity(intent);
            }
        });


        binding.policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("key",2);
                startActivity(intent);
            }
        });
        binding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("key",3);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowProgress.getShowProgress(getActivity()).show();
                String ven=singleTask.getValue("vendor");
                Vendor vendor=new Gson().fromJson(ven,Vendor.class);
                MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
                Call<JsonArray> call=myApi.logout(vendor.getId());
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if(response.isSuccessful())
                        {
                            try {
                                JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                                JSONObject jsonObject= jsonArray.getJSONObject(0);
                                String res=jsonObject.getString("res");
                                String msg= jsonObject.getString("message");
                                if(res.equals("success"))
                                {
                                    ShowProgress.getShowProgress(getActivity()).hide();
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    singleTask.removeUser("vendor");

                                }
                                else
                                {
                                    ShowProgress.getShowProgress(getActivity()).hide();
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
                        ShowProgress.getShowProgress(getActivity()).hide();
                    }
                });

            }
        });



        //handle order history

        binding.orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderListActivity.class));
            }
        });


        //upload menu
        binding.uploadMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), RestaurantMenuActivity.class));
            }
        });



        //see review
        binding.restaurantReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyReviewActivity.class));
            }
        });



    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}