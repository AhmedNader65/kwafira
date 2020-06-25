package com.almusand.kawfira.ui.main.ui.home.offerDetails;

import com.almusand.kawfira.Bases.BaseViewModel;

public class OfferDetailsViewModel extends BaseViewModel<OfferDetailsNavigator> {
     String title;
     String desc_ar;
    String desc_en;
    String title_en;
    String code;
    public void onCopyClick(){

        getNavigator().onCopyClicked();
    }

}
