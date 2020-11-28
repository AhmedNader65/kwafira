package com.almusand.kawfira.WebServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.R;
import com.almusand.kawfira.chat.ChatActivity;
import com.almusand.kawfira.kwafira.orderProcess.timer.CounterViewModel;
import com.almusand.kawfira.ui.counterActivity.CounterActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "Kwafira";
    GlobalPreferences gp;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("MSG","RECEIVED >> " +remoteMessage.getData().toString());
        try {
            showNotification(remoteMessage,null);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "Refreshed token: " + token);
        gp = new GlobalPreferences(this);
        if ((new GlobalPreferences(getApplicationContext())).getUSER_Logged()) {
            updateUser(gp.getUserAuth(), token);
        }
    }

    private void sendRegistrationToServer(String token) {

    }
    // [END on_new_token]
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(RemoteMessage message, Intent intent2) throws JSONException, ParseException {
        String title = "";
        String body = "";

        createNotificationChannel();
            String type = message.getData().get("type");
        Intent intent = new Intent(this, HomeActivity.class);

        if(type!=null) {
                Log.e("data", type);

                if (type.equals("startService")) {
                    String content = message.getData().get("content");
                    JSONObject contentObj = new JSONObject(content);
                    String serviceId = contentObj.getString("service_id");
                    String start = contentObj.getString("start");
                    int full_duration = contentObj.getInt("full_duration");
                    start = start.replace("T", " ");
                    Log.e("start", ">> " + start);
                    start = start.substring(0, 19);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(start);
                    long millis = date.getTime();
                    String service_title_ar = contentObj.getString("service_title_ar");
                    title = getString(R.string.app_name);
                    body = getString(R.string.startService);
                    intent = new Intent(this, CounterActivity.class)
                            .putExtra("serviceId", serviceId)
                            .putExtra("start", millis)
                            .putExtra("full_duration", full_duration)
                            .putExtra("type", "start")
                            .putExtra("service_title_ar", service_title_ar);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (type.equals("stopService")) {
                    String content = message.getData().get("content");
                    JSONObject contentObj = new JSONObject(content);
                    String serviceId = contentObj.getString("service_id");
                    String total_time = contentObj.getString("total_time");
                    int full_duration = contentObj.getInt("full_duration");

                    title = getString(R.string.app_name);
                    body = getString(R.string.stopService);
                    String service_title_ar = contentObj.getString("service_title_ar");

                    intent = new Intent(this, CounterActivity.class)
                            .putExtra("serviceId", serviceId)
                            .putExtra("time", total_time)
                            .putExtra("full_duration", full_duration)
                            .putExtra("type", "stop")
                            .putExtra("service_title_ar", service_title_ar);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (type.equals("finishOrder")) {

                    String content = message.getData().get("content");
                    JSONObject contentObj = new JSONObject(content);
                    int total_time = contentObj.getInt("total_time");
                    double price = contentObj.getDouble("total_price");
                    int Order_id = contentObj.getInt("order_id");
                    title = getString(R.string.app_name);
                    body = getString(R.string.orderStoped);


                    intent = new Intent(this, CounterActivity.class)
                            .putExtra("time", total_time)
                            .putExtra("price", price)
                            .putExtra("Order_id", Order_id)
                            .putExtra("type", "payment");
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (type.equals("chat")) {

                    String img = message.getData().get("body");
                    String name = message.getData().get("header").split(",")[0];
                    String id = message.getData().get("header").split(",")[1];
                    title = "";
                    body = getString(R.string.newMsg);

                    intent = new Intent(this, ChatActivity.class)
                            .putExtra("id", id)
                            .putExtra("name", name)
                            .putExtra("img", img);
                    Log.e("receiverId >" + id, "receiverName> " + name);

                } else if (type.equals("orderAccepted")) {
                    title = getString(R.string.kwafiraAssigned);
                    body = getString(R.string.kwafiraAssignedBody);
                    intent = new Intent(this, HomeActivity.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (type.equals("confirmPayment")) {
                    String content = message.getData().get("content");
                    JSONObject contentObj = new JSONObject(content);
                    String kwafera_id = contentObj.getString("kwafera_id");
                    String name = contentObj.getString("name");
                    String image = contentObj.getString("image");
                    int order_id = contentObj.getInt("order_id");
                    float rate = BigDecimal.valueOf(contentObj.getDouble("overall_rate")).floatValue();

                    intent = new Intent(this, CounterActivity.class)
                            .putExtra("type", "confirm")
                            .putExtra("name", name)
                            .putExtra("id", kwafera_id)
                            .putExtra("rate", rate)
                            .putExtra("order_id", order_id)
                            .putExtra("image", image);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Create an explicit intent for an Activity in your app
        PendingIntent pendingIntent = PendingIntent.getActivity(this, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                                                  

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());

//
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        Intent myIntent = new Intent(this, HomeActivity.class);
//        myIntent.putExtra("notification", "not");
//        myIntent.setAction(Long.toString(System.currentTimeMillis()));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        /*
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                myIntent,
//                0);
//*/
//        Bitmap myLogo = ((BitmapDrawable) ResourcesCompat.getDrawable(this.getResources(), R.mipmap.ic_launcher, null)).getBitmap();
//
//        Notification myNotification = new NotificationCompat.Builder(this)
//                .setContentTitle(message.getData().get("header"))
//                .setContentText(message.getData().get("body"))
//                .setTicker("Notification!")
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.logo)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setAutoCancel(true)
//                .setPriority(Notification.PRIORITY_MAX)
//                .build();
//
//        NotificationManager notificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, myNotification);
//
//        Intent gcm_rec = new Intent("your_action");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(gcm_rec);
//        notificationManager.notify(0, myNotification);
    }
    public static void updateUser(String auth, String firebaseid) {

        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), firebaseid);
        RetroWeb.getClient().create(ServiceApi.class).updateUser("Bearer "+auth
                ,null,null,tokenBody,null,null).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.i("New Token","has been sent!");

                } else {
                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

            }

        });
    }

}