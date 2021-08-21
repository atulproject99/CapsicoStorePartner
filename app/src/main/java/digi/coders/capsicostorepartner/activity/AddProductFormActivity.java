package digi.coders.capsicostorepartner.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import digi.coders.capsicostorepartner.databinding.ActivityAddProductFormBinding;
import digi.coders.capsicostorepartner.helper.Constraints;
import digi.coders.capsicostorepartner.helper.FilePath;
import digi.coders.capsicostorepartner.helper.MyApi;
import digi.coders.capsicostorepartner.model.Brand;
import digi.coders.capsicostorepartner.model.Category;
import digi.coders.capsicostorepartner.model.Product;
import digi.coders.capsicostorepartner.model.ShowProgress;
import digi.coders.capsicostorepartner.model.Vendor;
import digi.coders.capsicostorepartner.singletask.SingleTask;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductFormActivity extends AppCompatActivity {

    ActivityAddProductFormBinding binding;
    private SingleTask singleTask;
    private ProgressDialog progressDialog;
    private List<Category> categoryList;
    private List<Category> subcategoryList;
    private List<Brand> brandList;

    private MultipartBody.Part icon;
    public static Product product;
    private int key;
    private RequestBody masterProductId;
    private RequestBody productId;
        private List<Integer> catIds;
    private String categoryName;
    String proMrp="0",proOffer="0",proCgst="0",proSgst="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProductFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.show();
        singleTask=(SingleTask)getApplication();
        key=getIntent().getIntExtra("key",0);
        //handle back
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(key==2)
        {
            binding.addProduct.setText("Update Product");

        }
        loadProductCategories();
        loadBrands();
        setData();
        TextWatcher mrpWatch=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                binding.productDiscount.getEditText().setText("");
                binding.productSellingPrice.getEditText().setText("");
                binding.productWithGstPrice.getEditText().setText("");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                proMrp=s.toString();
                calculateAmount();

            }

            @Override
            public void afterTextChanged(Editable s) {
                proMrp=s.toString();
                if(s.toString().isEmpty())
                {
                    binding.productDiscount.getEditText().setText("");
                    binding.productSellingPrice.getEditText().setText("");
                    binding.productWithGstPrice.getEditText().setText("");

                }
                else {
                    calculateAmount();
                }
            }
        };
        binding.productMrp.getEditText().addTextChangedListener(mrpWatch);

        //handle add product


        binding.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid())
                {
                    ShowProgress.getShowProgress(AddProductFormActivity.this).show();
                    String ven=singleTask.getValue("vendor");
                    Vendor vendor=new Gson().fromJson(ven,Vendor.class);
                    RequestBody merchantId=RequestBody.create(MediaType.parse("text/plain"),vendor.getId());
                    RequestBody merchantCategoryId=RequestBody.create(MediaType.parse("text/plain"),vendor.getMerchantCategoryId());
                    RequestBody pName=RequestBody.create(MediaType.parse("text/plain"),productName);
                    RequestBody title=RequestBody.create(MediaType.parse("text/plain"),productTitle);
                    RequestBody productCatId=RequestBody.create(MediaType.parse("text/plain"),categoryId);
                    RequestBody productSubId=RequestBody.create(MediaType.parse("text/plain"),subcategoryId);
                    if(key==1)
                    {
                        masterProductId= RequestBody.create(MediaType.parse("text/plain"), product.getId());
                        Log.e("matserProductId",product.getId());
                    }
                    else {
                        masterProductId= RequestBody.create(MediaType.parse("text/plain"), "0");
                    }
//                    Log.e("sdsd",categoryId+""+subcategoryId);
                    RequestBody productBrandId=RequestBody.create(MediaType.parse("text/plain"),brandId);
                    RequestBody descri=RequestBody.create(MediaType.parse("text/plain"),productDescription);
                    RequestBody pUnitPrice=RequestBody.create(MediaType.parse("text/plain"),productUnitPrice);
                    RequestBody pQuantity=RequestBody.create(MediaType.parse("text/plain"),productQuantity);
                    RequestBody pMrp=RequestBody.create(MediaType.parse("text/plain"),binding.productMrp.getEditText().getText().toString());
                    RequestBody pOfferPrice=RequestBody.create(MediaType.parse("text/plain"),productOfferPrice);
                    RequestBody pCGst=RequestBody.create(MediaType.parse("text/plain"),productCgst);
                    RequestBody pSGst=RequestBody.create(MediaType.parse("text/plain"),productSGst);
                    if(key==2||key==1)
                    {
                        productId=RequestBody.create(MediaType.parse("text/plain"),product.getId());
                        Log.e("sdsd",product.getId()+"");
                    }
                    else
                    {
                        productId=RequestBody.create(MediaType.parse("text/plain"),"0");

                    }
                    RequestBody pGst=RequestBody.create(MediaType.parse("text/plain"),binding.productWithGstPrice.getEditText().getText().toString());
                    RequestBody pSellingPrice=RequestBody.create(MediaType.parse("text/plain"),binding.productSellingPrice.getEditText().getText().toString());
                    RequestBody pDiscount=RequestBody.create(MediaType.parse("text/plain"),binding.productDiscount.getEditText().getText().toString());
  //                  Log.e("mrp+selling",productMrp+"/"+binding.productSellingPrice.getEditText().getText().toString());
                    MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call=myApi.vendorAddProduct(productId,merchantId,pName,title,descri,pUnitPrice,pQuantity,pMrp,masterProductId,merchantCategoryId,productCatId,productSubId,productBrandId,pOfferPrice,pCGst,pSGst,pGst,pSellingPrice,pDiscount,icon);
                    //Log.e("sds",productDescription);
                    call.enqueue(new Callback<JsonArray>() {
                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                            if(response.isSuccessful())
                            {
                                Log.e("sdsds",response.toString());
                                try {
                                    JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                                    JSONObject jsonObject= jsonArray.getJSONObject(0);
                                    String res=jsonObject.getString("res");
                                    String msg= jsonObject.getString("message");
                                    if(res.equals("success"))
                                    {
                                        ShowProgress.getShowProgress(AddProductFormActivity.this).hide();
                                        Toast.makeText(AddProductFormActivity.this, msg, Toast.LENGTH_SHORT).show() ;
                                        startActivity(new Intent(AddProductFormActivity.this,AddProductSuccessFullyActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        ShowProgress.getShowProgress(AddProductFormActivity.this).hide();
                                        Toast.makeText(AddProductFormActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {
                            ShowProgress.getShowProgress(AddProductFormActivity.this).hide();
                            Toast.makeText(AddProductFormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });














                }



            }
        });


        //handle gallery work
        binding.addProductPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
    }

    private void setData() {
    if(product!=null)
        {

            binding.productName.getEditText().setText(product.getName());
            binding.productTitle.getEditText().setText(product.getTitle());
            binding.productDescription.getEditText().setText(product.getDescription());
            binding.productUnitPrice.getEditText().setText(product.getUnit());
            binding.productQuantity.getEditText().setText(product.getQuantity());
            binding.productMrp.getEditText().setText(product.getMrp());
            binding.productOfferPrice.getEditText().setText(product.getPrice());
            binding.productCgst.getEditText().setText(product.getCgst());
            binding.productSgst.getEditText().setText(product.getSgst());
            binding.productWithGstPrice.getEditText().setText(product.getGst());
            binding.productSellingPrice.getEditText().setText(product.getSellPrice());
            binding.productDiscount.getEditText().setText(product.getDiscount());
            Picasso.get().load(Constraints.BASE_URL+Constraints.MASTER_PRODUCT+product.getIcon()).into(binding.productPhoto);

        }







    }


    private void calculateAmount() {


        TextWatcher offerPriceWatch=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                proOffer="0";
                Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                binding.productWithGstPrice.getEditText().setText(gst + "");

                Double sellPrice=Double.parseDouble(proOffer)+gst;
                binding.productSellingPrice.getEditText().setText(sellPrice+"");


                Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                if(discount.equals(0))
                {
                    binding.productDiscount.getEditText().setText("0.0");
                }
                else
                {

                    double e = Math.round(discount*100)/100;

                    binding.productDiscount.getEditText().setText(e+"");
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()==0)
                {
                    proOffer="0";
                }
                else
                {
                    proOffer=s.toString();
                }

                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");

                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");
                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {

                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }






            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                {
                 proOffer="0";
                }
                else {
                    proOffer = s.toString();
                }
                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");


                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {

                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }


                }

        };
        TextWatcher cgstWatch=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                proCgst="0";
                Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                binding.productWithGstPrice.getEditText().setText(gst + "");
                Double sellPrice=Double.parseDouble(proOffer)+gst;
                binding.productSellingPrice.getEditText().setText(sellPrice+"");


                Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                if(discount.equals(0))
                {
                    binding.productDiscount.getEditText().setText("0.0");
                }
                else
                {

                    double e = Math.round(discount*100)/100;

                    binding.productDiscount.getEditText().setText(e+"");
                }


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0)
                {
                    proCgst="0";
                }
                else
                {
                    proCgst=s.toString();
                }
                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");


                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {

                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }



            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(s.toString().isEmpty())
                    {
                        proCgst="0";
                    }
                    else
                    {
                        proCgst=s.toString();
                    }



                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");


                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {
                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }


                }

        };
        TextWatcher  sgstWatch=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    proSgst="0";
                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");


                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {

                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0)
                {
                    proSgst="0";
                }
                else
                {
                    proSgst=s.toString();
                }


                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");


                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {

                        double e = Math.round(discount*100)/100;
                        binding.productDiscount.getEditText().setText(e+"");
                    }



            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(s.toString().isEmpty())
                    {
                        proSgst="0";
                    }
                    else
                    {
                        proSgst=s.toString();
                    }



                    Double totalGst = Double.parseDouble(proCgst) + Double.parseDouble(proSgst);
                    Double gst = ((Double.parseDouble(proOffer) * totalGst) / 100);
                    binding.productWithGstPrice.getEditText().setText(gst + "");
                    Double sellPrice=Double.parseDouble(proOffer)+gst;
                    binding.productSellingPrice.getEditText().setText(sellPrice+"");
                    Double discount=((Double.parseDouble(proMrp)-sellPrice)/(Double.parseDouble(proMrp)))*100;
                    if(discount.equals(0))
                    {
                        binding.productDiscount.getEditText().setText("0.0");
                    }
                    else
                    {
                        double e = Math.round(discount*100)/100;

                        binding.productDiscount.getEditText().setText(e+"");
                    }
                }
        };

        binding.productOfferPrice.getEditText().addTextChangedListener(offerPriceWatch);
        binding.productCgst.getEditText().addTextChangedListener(cgstWatch);
        binding.productSgst.getEditText().addTextChangedListener(sgstWatch);

    }

    String brandId;
    private void loadBrands() {
        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call=myApi.getBrand(vendor.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        String res=jsonObject1.getString("res");
                        if(res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                            brandList=new ArrayList<>();
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                Brand brand=new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),Brand.class);
                                brandList.add(brand);
                            }
                            List<String> s=new ArrayList<>();
                            s.add("Choose Product  Brand");
                            for (int i = 0; i <brandList.size(); i++) {
                                s.add(brandList.get(i).getName());
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProductFormActivity.this, android.R.layout.simple_list_item_1,s);
                            binding.productBrand.setAdapter(arrayAdapter);
                            if(product!=null) {
                                if (product.getBarndname() != null) {

                                    int postion = arrayAdapter.getPosition(product.getBarndname());
                                    binding.productBrand.setSelection(postion);
                                }
                            }
                            binding.productBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position>0)
                                    {

                                        Brand brand=brandList.get(position-1);
                                        brandId=brand.getId();
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(AddProductFormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    String categoryId;


    private void loadProductCategories() {
        ShowProgress.getShowProgress(AddProductFormActivity.this).show();
        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Log.e("merchant_id",vendor.getId()+"");
        Call<JsonArray> call=myApi.getCategory(vendor.getMerchantCategoryId(),vendor.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        String res=jsonObject1.getString("res");
                        if(res.equals("success")) {
                            ShowProgress.getShowProgress(AddProductFormActivity.this).hide();
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                            categoryList = new ArrayList<>();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                Category category = new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), Category.class);
                                categoryList.add(category);
                                Log.e("categoryid", category.getId());
                            }
                            List<String> s = new ArrayList<>();
                            s.add("Choose Category");
                            for (int i = 0; i < categoryList.size(); i++) {
                                s.add(categoryList.get(i).getName());
                            }



                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProductFormActivity.this, android.R.layout.simple_list_item_1,s);
                            binding.chooseCategory.setAdapter(arrayAdapter);
                            if(product!=null) {
                                if (product.getCategoriesname() != null) {

                                    int postion = arrayAdapter.getPosition(product.getCategoriesname());
                                    binding.chooseCategory.setSelection(postion);

                                }
                            }

                            binding.chooseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position>0)
                                    {

                                        Category category=categoryList.get(position-1);
                                        categoryId=category.getId();
                                        Log.e("caid",categoryId);

                                        getSubcategory(category);
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });


    }

    String subcategoryId;
    private void getSubcategory(Category category) {
        String ven=singleTask.getValue("vendor");
        Vendor vendor=new Gson().fromJson(ven,Vendor.class);
        MyApi myApi=singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call=myApi.getSubCategory(category.getId());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {
                    try {
                        JSONArray jsonArray=new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        String res=jsonObject1.getString("res");
                        if(res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                            subcategoryList=new ArrayList<>();
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                Category category=new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),Category.class);
                                subcategoryList.add(category);
                            }
                            List<String> s=new ArrayList<>();
                            s.add("Choose Subcategory");
                            for (int i = 0; i <subcategoryList.size(); i++) {

                                s.add(subcategoryList.get(i).getName());
                            }


                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProductFormActivity.this, android.R.layout.simple_list_item_1,s);
                            binding.chooseSubCategory.setAdapter(arrayAdapter);
                            if(product!=null) {
                                if (product.getSubcategoriesname() != null) {

                                    int postion = arrayAdapter.getPosition(product.getSubcategoriesname());
                                    binding.chooseSubCategory.setSelection(postion);

                                }
                            }
                            binding.chooseSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position>0)
                                    {

                                        Category category=categoryList.get(position-1);
                                        subcategoryId=category.getId();

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    private String productName,productCategory,productSubCategory,productTitle,productBrand,productDescription,productUnitPrice,productQuantity,productMrp,productOfferPrice,productCgst,productSGst;
    private boolean valid() {
        productName=binding.productName.getEditText().getText().toString();
        productTitle=binding.productTitle.getEditText().getText().toString();
        if(binding.chooseCategory!=null) {
            productCategory = binding.chooseCategory.getSelectedItem().toString();
            if(binding.chooseCategory.getSelectedItemPosition()!=0) {

                productSubCategory = binding.chooseSubCategory.getSelectedItem().toString();
            }
        }
        productBrand=binding.productBrand.getSelectedItem().toString();
        productDescription=binding.productDescription.getEditText().getText().toString();
        productUnitPrice=binding.productUnitPrice.getEditText().getText().toString();
        productQuantity=binding.productQuantity.getEditText().getText().toString();
        productMrp=binding.productMrp.getEditText().getText().toString();
        productOfferPrice=binding.productOfferPrice.getEditText().getText().toString();
        productCgst=binding.productCgst.getEditText().getText().toString();
        productSGst=binding.productSgst.getEditText().getText().toString();
        if(TextUtils.isEmpty(productName))
        {
            binding.productName.setError("Please Enter Product Name");
            binding.productName.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(productTitle))
        {
            binding.productTitle.setError("Please Enter Product Title");
            binding.productTitle.requestFocus();
            return false;
        }
        else if(productCategory==null || productCategory.equals("Choose Category"))
        {
            Toast.makeText(AddProductFormActivity.this, "Please Choose Product Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(productSubCategory==null || productSubCategory.equals("Choose Subcategory"))
        {
            Toast.makeText(AddProductFormActivity.this, "Please Choose Product SubCategory", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(productBrand.equals("Choose Product Brand")|| productBrand.isEmpty())
        {
            Toast.makeText(AddProductFormActivity.this, "Please Choose Product Brand", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TextUtils.isEmpty(productDescription))
        {
            binding.productDescription.setError("Please Write Product Description");
            binding.productDescription.requestFocus();
            return false;

        }

        else if(TextUtils.isEmpty(productUnitPrice))
        {
            binding.productUnitPrice.setError("Please Enter Product Unit Price");
            binding.productUnitPrice.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(productMrp))
        {
            binding.productMrp.setError("Please Enter Product MRP");
            binding.productMrp.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(productOfferPrice))
        {
            binding.productOfferPrice.setError("Please Enter Product Offer Price");
            binding.productOfferPrice.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(productCgst))
        {
            binding.productCgst.setError("Please Enter Product Cgst");
            binding.productCgst.requestFocus();
            return false;

        }
        else if(TextUtils.isEmpty(productSGst))
        {
            binding.productSgst.setError("Please Enter Product Sgst");
            binding.productSgst.requestFocus();
            return false;
        }
        else
        {
            return true;
        }






    }

    private void openGallery() {
        if(checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }
        else
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1001);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode==RESULT_OK && data!=null)
        {
            binding.productPhoto.setImageURI(data.getData());
            String path = FilePath.getPath(this, data.getData());
            try {
                File file = new File(FilePath.getPath(this, data.getData()));
                if (file != null) {
//                myHolder.pdficon.setVisibility(View.VISIBLE);
                    RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
                    icon = MultipartBody.Part.createFormData("icon", file.getName(), reqFile);
                }

            }
            catch (Exception e)
            {

            }

            binding.productPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(AddProductFormActivity.this,FullImageViewActivity.class);
                    Uri uri=data.getData();
                    in.putExtra("data",uri.toString());
                    startActivity(in);
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {

            openGallery();
        }
        else
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }
    }
}