package com.almusand.kawfira.kwafira.orderProcess;

import com.almusand.kawfira.Models.SessionModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;

public interface Navigator {
    void updateAdapter(OrderModel order);

    void setSession(SessionModel session);

    void endOrderClicked();

    void endOrder(OrderModel order);

    void failed();
}
