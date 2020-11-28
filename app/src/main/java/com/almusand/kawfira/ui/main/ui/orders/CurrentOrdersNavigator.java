package com.almusand.kawfira.ui.main.ui.orders;

import com.almusand.kawfira.Models.orders.reservations.OrderModel;

public interface CurrentOrdersNavigator {

    void showStatusFragment();

    void openPayment(OrderModel orderModel);
}
