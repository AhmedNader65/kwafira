package com.almusand.kawfira.WebServices;

import com.almusand.kawfira.Models.AboutUsModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.categories.CategoriesResponseModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.Models.offers.OffersModel;
import com.almusand.kawfira.Models.orders.reservations.OrdersResModel;
import com.almusand.kawfira.Models.reservations.ReservationResModel;
import com.almusand.kawfira.ui.kwafiraReviewProfile.ReviewsResponseModel;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
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

    @POST("logout")
    Call<MsgModel> logout(
            @Header("Authorization") String Authorization
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

    @Multipart
    @POST("national-id")
    Call<MsgModel> onKwafiraUploadId(
            @Header("Authorization") String Authorization,
            @Part("national_id") RequestBody national_id,
            @Part MultipartBody.Part national_id_image
    );

    @Multipart
    @POST("complete-register")
    Call<MsgModel> onKwafiraUploadCert(
            @Header("Authorization") String Authorization,
            @Part MultipartBody.Part certificate_image
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
            @Query(value = "status", encoded = true) String status,
            @Header("Authorization") String Authorization);

    @GET("kwafera-orders")
    Call<OrdersResModel> onGetKwafiraOrders(
            @Header("Authorization") String Authorization);

    @POST("order/{id}/cancel")
    Call<MsgModel> onCancelOrder(
            @Path(value = "id", encoded = true) String id_,
            @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("order/accept")
    Call<MsgModel> onAcceptOrder(
            @Field("order_id") String order_id,
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
    Call<OrdersResModel> PostOrder(
            @Header("Authorization") String Authorization,
            @Field("address") String address,
            @Field("appartment_number") String appartment_number,
            @Field("house_number") String house_number,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("services[]") ArrayList<String> services

    );

    @Multipart
    @POST("complaint")
    Call<MsgModel> PostComplaint(
            @Header("Authorization") String Authorization,
            @Part("content") RequestBody content,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3

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


    @FormUrlEncoded
    @POST("user/update")
    Call<LoginModel> updateKwafiraServices(
            @Header("Authorization") String Authorization,
            @Field("services[]") ArrayList<String> services

    );

    @FormUrlEncoded
    @POST("user/update")
    Call<LoginModel> updateUserStatus(
            @Header("Authorization") String Authorization,
            @Field("available") int available);

    @FormUrlEncoded
    @POST("notification")
    Call<MsgModel> sendNotification(
            @Header("Authorization") String Authorization,
            @Field("user_id") String user_id,
            @Field("header") String header,
            @Field("body") String body,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("order/{id}/session/start")
    Call<MsgModel> startService(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization,
            @Field("service_id") String service_id,
            @Field("created_at") String time
    );
    @FormUrlEncoded
    @POST("order/{id}/session/end")
    Call<MsgModel> stopService(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization,
            @Field("session_id") String service_id
    );

    @FormUrlEncoded
    @POST("order/{id}/apply-coupon")
    Call<OrdersResModel> applyOffer(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization,
            @Field("coupon") String coupon
    );

    @POST("order/{id}/end")
    Call<OrdersResModel> endOrder(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization
    );

    @POST("order/{id}/confirm")
    Call<MsgModel> confirmPayment(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization
    );

    @FormUrlEncoded
    @POST("order/{id}/pay")
    Call<OrdersResModel> payOrder(
            @Path(value = "id", encoded = true) String order_id,
            @Header("Authorization") String Authorization,
            @Field("method") String method
    );
    @FormUrlEncoded
    @POST("review")
    Call<MsgModel> review(
            @Header("Authorization") String Authorization,
            @Field("content") String content,
            @Field("order_id") int order_id,
            @Field("reviewed_id") int reviewed_id,
            @Field("stars") float stars
    );

    @FormUrlEncoded
    @POST("contact-us-message")
    Call<MsgModel> contactUs(
            @Header("Authorization") String Authorization,
            @Field("content") String content
    );

    @GET("about-us")
    Call<AboutUsModel> aboutUs();

}
