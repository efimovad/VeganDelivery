package com.nozimy.vegandelivery.interactors.orders;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.db.entity.OrderEntity;
import com.nozimy.vegandelivery.db.model.Item;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.network.ApiRepo;
import com.nozimy.vegandelivery.network.OrderRequest;
import com.nozimy.vegandelivery.network.OrdersApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersListInteractor {
    private final Context mContext;
    private OrdersApi mOrdersApi;

    private final static MutableLiveData<List<Order>> mOrders = new MutableLiveData<>();

//    static {
//        mDishes.setValue(Collections.<Dish>emptyList());
//    }

    public OrdersListInteractor(Context context) {
        mContext = context;
        mOrdersApi = ApiRepo.from(mContext).getOrdersApi();
    }

    public LiveData<List<Order>> getOrders() {
        return mOrders;
    }

    public void refresh(String user) {
        mOrdersApi.getAll(user).enqueue(new Callback<OrdersApi.OrderResponse>() {
            @Override
            public void onResponse(Call<OrdersApi.OrderResponse> call,
                                   Response<OrdersApi.OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mOrders.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<OrdersApi.OrderResponse> call, Throwable t) {
                Log.e("DishesInteractor", "Failed to load", t);
            }
        });
    }

    public void createPost(Order order) {
        OrderRequest request =  new OrderRequest(order);
        Call<OrderRequest> call = mOrdersApi.createOrder(request);

        call.enqueue(new Callback<OrderRequest>() {
            @Override
            public void onResponse(Call<OrderRequest> call,
                                   Response<OrderRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("Order Interactor", "Code:" + response.code());
                }

            }

            @Override
            public void onFailure(Call<OrderRequest> call, Throwable t) {
                Log.e("Order Interactor", "Failed to load", t);
            }
        });
    }

    private static List<Order> transform(OrdersApi.OrderResponse ordersResponse) {
        List<OrdersApi.OrderPlain> plains = ordersResponse.orders;
        List<Order> result = new ArrayList<>();
        if (plains != null) {
            for (OrdersApi.OrderPlain orderPlain : plains) {
                try {
                    OrderEntity order = map(orderPlain);
                    if (order != null) {
                        result.add(order);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private static OrderEntity map(OrdersApi.OrderPlain orderPlain) throws ParseException {
        return new OrderEntity(
                orderPlain.id,
                orderPlain.itemsFull,
                orderPlain.cost,
                orderPlain.address,
                orderPlain.cafeLogo,
                orderPlain.cafeName,
                orderPlain.status
        );
    }
}

