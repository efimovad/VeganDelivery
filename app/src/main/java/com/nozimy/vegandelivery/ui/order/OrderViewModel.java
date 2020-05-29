package com.nozimy.vegandelivery.ui.order;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.interactors.orders.OrdersListInteractor;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrdersListInteractor interactor = new OrdersListInteractor(getApplication());
    private LiveData<List<Order>> mOrderList = interactor.getOrders();

    public OrderViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Order>> getOrderList() {
        return mOrderList;
    }

    public void refresh(int user) {
        interactor.refresh(user);
    }

    public void createOrder(int user, Order order) {
        interactor.createPost(user, order);
    }
}
