package com.almusand.kawfira.ui.main.ui.editingActivities.lang;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.ui.main.ui.editingActivities.password.ChangePasswordNavigator;
import com.almusand.kawfira.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageVM extends BaseViewModel<LanguageNavigator> {

    public void getChecked(String lang) {
        if(lang.equals("en")){
            getNavigator().checkEng();
        }else{
            getNavigator().checkAr();
        }
    }

}
