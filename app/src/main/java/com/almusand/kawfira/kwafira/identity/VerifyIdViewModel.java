package com.almusand.kawfira.kwafira.identity;

import android.net.Uri;
import android.util.Log;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.Login.LoginModel;
import com.almusand.kawfira.Models.Login.User;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.kwafira.identity.idFragment.IdVerifyNavigator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyIdViewModel extends BaseViewModel<VerifyIdNavigator> {


    public void checkStatus(boolean idSent, boolean isSentFromLogin) {
        if(idSent||isSentFromLogin){
            getNavigator().openNextFragment();
        }
    }
}
