package com.almusand.kawfira.WebServices;

import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.categories.CategoriesResponseModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.Models.offers.OffersModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.Models.reservations.ReservationResModel;
import com.almusand.kawfira.ui.kwafiraReviewProfile.ReviewsResponseModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> onLogin(
            @Field("phone") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<LoginModel> onRegisterUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("role") String role,
            @Field("firebase_id") String token
    );

    @FormUrlEncoded
    @POST("verify")
    Call<LoginModel> onUserVerify(
            @Field("verification_code") String code,
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST("resend-verification-code")
    Call<MsgModel> onUserResendVerify(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("reset-password")
    Call<MsgModel> onResetPassword(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("new-password")
    Call<MsgModel> onStorePassword(
            @Field("password") String password,
            @Field("verification_code") String code
    );

    @FormUrlEncoded
    @POST("update-password")
    Call<MsgModel> onUpdatePassword(
            @Header("Authorization") String Authorization,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );
    @GET("reviews/{id}")
    Call<ReviewsResponseModel> onGetReviews(
            @Path(value = "id", encoded = true) String id_,
            @Header("Authorization") String Authorization);

    @GET("offers")
    Call<OffersModel> onGetOffers(
            @Header("Authorization") String Authorization);
    @GET("user")
    Call<LoginModel> onGetUser(
            @Header("Authorization") String Authorization);

    @GET("reservations")
    Call<ReservationResModel> onGetReservations(
            @Header("Authorization") String Authorization);

    @POST("reservations/{id}/cancel")
    Call<MsgModel> onCancelReservation(
            @Path(value = "id", encoded = true) String id_,
            @Header("Authorization") String Authorization);
    @FormUrlEncoded
    @POST("reservations/{id}/update")
    Call<MsgModel> onUpdateReservation(
            @Path(value = "id", encoded = true) String id_,
            @Header("Authorization") String Authorization,
            @Field("date") String date);

    @GET("orders")
    Call<OrdersResModel> onGetOrders(
            @Header("Authorization") String Authorization);

    @POST("order/{id}/cancel")
    Call<MsgModel> onCancelOrder(
            @Path(value = "id", encoded = true) String id_,
            @Header("Authorization") String Authorization);
    @GET("categories")
    Call<CategoriesResponseModel> onGetCategories();

    @GET("services")
    Call<ServicesResponseModel> onGetServices();

    @GET("services")
    Call<ServicesResponseModel> onGetServicesOfCat(
         @Query(value = "category_id", encoded = true) String category_id);

    @FormUrlEncoded
    @POST("reservations")
    Call<MsgModel> PostReservation(
            @Header("Authorization") String Authorization,
            @Field("date") String date,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("address") String address,
            @Field("appartment_number") String appartment_number,
            @Field("house_number") String house_number,
            @Field("services[]") ArrayList<String> services

    );
    @FormUrlEncoded
    @POST("order/initiate")
    Call<MsgModel> PostOrder(
            @Header("Authorization") String Authorization,
            @Field("address") String address,
            @Field("appartment_number") String appartment_number,
            @Field("house_number") String house_number,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("services[]") ArrayList<String> services

    );

    @FormUrlEncoded
    @Multipart
    @POST("complaint")
    Call<MsgModel> PostComplaint(
            @Header("Authorization") String Authorization,
            @Field("content") String content,
            @Part("file1") MultipartBody.Part file1,
            @Part("file2") MultipartBody.Part file2,
            @Part("file3") MultipartBody.Part file3

    );

    @Multipart
    @POST("user/update")
    Call<LoginModel> updateUser(
            @Header("Authorization") String Authorization,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("firebase_id") RequestBody firebase_id,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part image

    );
}
