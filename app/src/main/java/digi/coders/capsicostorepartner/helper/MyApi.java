package digi.coders.capsicostorepartner.helper;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MyApi {


    @POST("merchantCategory")
    Call<JsonArray> getMerchantCategory();

    @POST("city")
    Call<JsonArray> getCity();

    @FormUrlEncoded
    @POST("registers")
    Call<JsonArray> registerStore(
            @Field("merchant_category_id") String id,
            @Field("name") String name,
            @Field("owner_name") String ownerName,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("state")  String state,
            @Field("pincode") String pincode,
            @Field("password") String password,
            @Field("opening_time") String openingTime,
            @Field("closing_time") String closingTime,
            @Field("estimated_delivery") String estimatedTime,
            @Field("cities") String cityId,
            @Field("icon") String storePhotoFront,
            @Field("storeproof_type") String storeProofType,
            @Field("proof_photo") String proofPhoto,
            @Field("ownerproof_type") String ownerProofType,
            @Field("ownerphoto_front") String ownerFrontPhoto,
            @Field("ownerphoto_back") String ownerBackPhoto,
            @Field("ownerproof_no") String ownerProofNo,
            @Field("latitude") String lattitude,
            @Field("longitude") String longitude,
            @Field("address") String address,
            @Field("description") String description,
            @Field("categories") String categories,
            @Field("subcategories") String subCategories
            );


    @FormUrlEncoded
    @POST("otpVerification")
    Call<JsonArray> otpVerification(@Field("mobile") String mobile,
                                    @Field("otp") String otp);


    @FormUrlEncoded
    @POST("resendOtp")
    Call<JsonArray> resendOtp(@Field("mobile") String mobile);



    @FormUrlEncoded
    @POST("ForgetPassword")
    Call<JsonArray> forgetPassword(@Field("mobile") String mobile);



    @FormUrlEncoded
    @POST("profile")
    Call<JsonArray> profile(@Field("merchant_id") String merchantId);


    @FormUrlEncoded
    @POST("ResetPassword")
    Call<JsonArray> resetPassword(@Field("mobile") String mobile,
                                  @Field("password") String password);






    @FormUrlEncoded
    @POST("login")
    Call<JsonArray> login(@Field("mobile") String mobile,
                          @Field("password") String password);




    @FormUrlEncoded
    @POST("masterProduct")
    Call<JsonArray> getMasterProduct(@Field("merchant_category_id") String merchaneCategoryId,
                                     @Field("merchant_id") String merchantId,
                                     @Field("search") String search);




    @FormUrlEncoded
    @POST("category")
    Call<JsonArray> getCategory(@Field("merchant_category_id") String merchantCategoryId,
                                @Field("merchant_id") String merchantId
                                );




    @FormUrlEncoded
    @POST("subcategory")
    Call<JsonArray> getSubCategory(@Field("multiple_category_id") String categoryId);




    @FormUrlEncoded
    @POST("brand")
    Call<JsonArray> getBrand(@Field("merchant_id") String merchantId);



    @FormUrlEncoded
    @POST("product")
    Call<JsonArray> editProduct(@Field("merchant_id") String merchantId);


    @Headers("Accept: application/json")
    @Multipart
    @POST("addProduct")
    Call<JsonArray> vendorAddProduct(@Part("product_id") RequestBody productId,
                                        @Part("merchant_id") RequestBody merchaneId,
                                        @Part("name") RequestBody name,
                                        @Part("title") RequestBody title,
                                        @Part("description") RequestBody description,
                                        @Part("unit") RequestBody unit,
                                        @Part("quantity") RequestBody quantity,
                                        @Part("mrp") RequestBody mrp,
                                        @Part("master_product_id") RequestBody masterProductId,
                                        @Part("merchant_category_id") RequestBody merchantCategoryId,
                                        @Part("category_id") RequestBody categoryId,
                                        @Part("subcategory_id") RequestBody subCategoryId,
                                        @Part("brand_id") RequestBody brandId,
                                        @Part("price") RequestBody price,
                                        @Part("cgst") RequestBody cgst,
                                        @Part("sgst") RequestBody sgst,
                                        @Part("gst") RequestBody gst,
                                        @Part("sell_price") RequestBody sellPrice,
                                        @Part("discount") RequestBody discount,
                                        @Part  MultipartBody.Part icon);




            @FormUrlEncoded
            @POST("product")
            Call<JsonArray> getAllProduct(@Field("merchant_id") String merchantId);









            @FormUrlEncoded
            @POST("UpdateProductStatus")
            Call<JsonArray> productStatus(@Field("product_id") String productId,
                                          @Field("is_status") String status,
                                          @Field("merchant_id") String id);



            @FormUrlEncoded
            @POST("logout")
            Call<JsonArray> logout(@Field("merchant_id") String merchantId);

            @FormUrlEncoded
            @POST("MerchantOpenStatus")
            Call<JsonArray> merchantOpenStatus(@Field("merchant_id") String merchantId,
                                               @Field("is_open") String status);


}
