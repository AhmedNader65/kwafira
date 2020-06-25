package com.almusand.kawfira.ui.allServices;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.utils.GlobalPreferences;

public class ServicesAdapterViewModel extends BaseViewModel<ServicesAdapterNavigator> {

    boolean ordered = false;
    public void initCart(GlobalPreferences pf,int id) {
        boolean isInCart = pf.isInCart(id);
        Log.e("inCart",id +"  "+isInCart);
        if(isInCart){

            ordered = true;
            getNavigator().inCart();
        }
    }

    MutableLiveData<Integer> cartNumber = new MutableLiveData<>();
    public MutableLiveData<Integer> getCartNumber() {
        return cartNumber;
    }
    public void getCartFromPf(GlobalPreferences pf) {

        cartNumber.setValue(pf.getCartCounter());
    }

    public void clicked() {
        if(!ordered) {
            ordered= true;
            getNavigator().onOrderService();
        }else{
            ordered= false;
            getNavigator().onCancelService();
        }
    }


    public void shouldShow(Integer cartCount) {
        int x = cartCount;
        if(x>0){
            getNavigator().showCart();
        }else{
            getNavigator().hideCart();
        }
    }
}
