package com.almusand.kawfira.ui.main.ui.suggestions;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.categories.ServicesResponseModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionsViewModel extends BaseViewModel<SuggestionsNavigator> {

    private MutableLiveData<String> mText;

    public SuggestionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void postComplaint(String auth, String content, String filePath1,String filePath2,String filePath3){
        MultipartBody.Part file1Body;

        if(filePath1==null){
            file1Body= null;
        }else{

            File file = new File(filePath1);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            file1Body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }
        MultipartBody.Part file2Body;

        if(filePath2==null){
            file2Body= null;
        }else{

            File file = new File(filePath2);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            file2Body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }
        MultipartBody.Part file3Body;

        if(filePath3==null){
            file3Body= null;
        }else{

            File file = new File(filePath3);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            file3Body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }

        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);

        RetroWeb.getClient().create(ServiceApi.class).PostComplaint("bearer "+auth,
                contentBody,file1Body,
                file2Body,
                file3Body).enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    String msg = model.getMessage();
                    getNavigator().showSuccessToast("تم ارسال رسالتك بنجاح");
                    setIsLoading(false);

                } else {
                    try {
                        Log.e("error reviews", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }

    public void confirm(String userAuth, String toString, String filePath1, String filePath2, String filePath3) {
        if(toString.length()>1){
            postComplaint(userAuth,toString,filePath1,filePath2,filePath3);
        }else{
            getNavigator().showToast("برحاء كتابة تعليق");
        }
    }
}