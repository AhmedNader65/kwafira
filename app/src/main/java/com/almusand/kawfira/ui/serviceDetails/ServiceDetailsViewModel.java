package com.almusand.kawfira.ui.serviceDetails;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

public class ServiceDetailsViewModel extends BaseViewModel<ServiceDetailsNavigator> {
     String title;
     String desc_ar;
    String desc_en;
    String title_en;
    boolean ordered=false;
    public void onOrderService(){
        if(!ordered) {
            ordered= true;
            getNavigator().onOrderService();
        }else{
            ordered= false;
            getNavigator().onCancelService();
        }
    }

    MutableLiveData<Integer> cartNumber = new MutableLiveData<>();
    public MutableLiveData<Integer> getCartNumber() {
        return cartNumber;
    }
    public void getCartFromPf(GlobalPreferences pf) {

        cartNumber.setValue(pf.getCartCounter());
    }

    public void shouldShow(Integer cartCount){
        int x = cartCount;
        if(x>0){
            getNavigator().showCart();
        }else{
            getNavigator().hideCart();
        }
    };

    public void initCart(GlobalPreferences pf,int id) {
        boolean isInCart = pf.isInCart(id);
        if(isInCart){
            ordered = true;
            getNavigator().inCart();
        }
    }
}
