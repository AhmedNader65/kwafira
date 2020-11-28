package com.almusand.kawfira.kwafira.orderProcess.timer;

import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;

public interface CounterNavigator {
    void updateUI(String format);

    void start();
    void stop(SessionModel sessionModel);

    void payment();

    void paymentFromStart();

    void applyCoupon(String coupon);

    void updatePrice(OrderModel order, double v);

    void paid(OrderModel order);

    void confirmedPayment();

    void rated();
}
