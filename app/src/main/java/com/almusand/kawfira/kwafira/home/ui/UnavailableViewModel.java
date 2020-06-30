//package com.almusand.kawfira.kwafira.home.ui;
//
//import android.util.Log;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.almusand.kawfira.Bases.BaseViewModel;
//import com.almusand.kawfira.Models.Login.LoginModel;
//import com.almusand.kawfira.WebServices.RetroWeb;
//import com.almusand.kawfira.WebServices.ServiceApi;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//import okio.Buffer;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class UnavailableViewModel extends BaseViewModel<StatusNavigator> {
//    // TODO: Implement the ViewModel
//    private MutableLiveData<Boolean> isAvailable = new MutableLiveData<>();
//
//
//    public MutableLiveData<Boolean> isAvailable() {
//        return isAvailable;
//    }
//
//    public void setIsAvailable(boolean isAvailable, boolean lastStatus, String userAuth) {
//        Log.e("lastStatus!=isAvailable",(lastStatus!=isAvailable)+"");
//        try {
//            if(lastStatus!=isAvailable)
//                updateUser(userAuth,isAvailable);
//            else
//                getNavigator().hideLoadingg();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateUser(String auth,boolean available) throws URISyntaxException {
//
//        RetroWeb.getClient().create(ServiceApi.class).updateUserStatus("Bearer "+auth
//                ,available?1:0).enqueue(new Callback<LoginModel>() {
//            @Override
//            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//                if (response.isSuccessful()) {
//                    LoginModel model = response.body();
//                    getNavigator().updatedSuccessfuly(available);
//                    isAvailable.setValue(available);
//                    setIsLoading(false);
//
//                } else {
//
//                    final Buffer buffer = new Buffer();
//
//                    try {
//                        call.request().body().writeTo(buffer);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("requesttt", buffer.readUtf8());
//                    getNavigator().revertAndShowToast();
//
//                    try {
//                        Log.e("error reviews", response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginModel> call, Throwable t) {
//                setIsLoading(false);
//
//            }
//
//        });
//    }
//
//}