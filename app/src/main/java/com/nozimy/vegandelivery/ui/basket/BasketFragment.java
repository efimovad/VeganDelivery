package com.nozimy.vegandelivery.ui.basket;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.MainActivity;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.ui.order.OrderViewModel;

public class BasketFragment extends Fragment {
    private BasketFragmentListener listener;
    private BasketViewModel basketViewModel;
    private RecyclerView list;
    private View root;
    private OrderAdapter adapter;
    //private OrderViewModel mOrderListViewModel;

    public interface BasketFragmentListener {
        void onSubmitOrder();
        void loadImage(ImageView view, String url);
    };

    public void setListener(BasketFragmentListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener mClearListener = v -> {
        MainActivity activity = (MainActivity) getActivity();
        Order currOrder = activity.getCurrentOrder();
        if (currOrder == null || currOrder.isEmpty()) {
            return;
        }

        currOrder.clear();

        TextView empty = root.findViewById(R.id.empty_text);
        empty.setVisibility(View.VISIBLE);

        Button submitButton = root.findViewById(R.id.submit_order_button);
        submitButton.setVisibility(View.INVISIBLE);

        View costView = root.findViewById(R.id.price_layout);
        costView.setVisibility(View.INVISIBLE);

        View cafeDetailsView = root.findViewById(R.id.place_info);
        cafeDetailsView.setVisibility(View.INVISIBLE);

        adapter.notifyDataSetChanged();
    };

    // TODO: break down into functions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        basketViewModel =
                ViewModelProviders.of(this).get(BasketViewModel.class);

        root = inflater.inflate(R.layout.fragment_basket, container, false);

        MainActivity activity = (MainActivity) getActivity();
        Order currOrder = activity.getCurrentOrder();

        View costView = root.findViewById(R.id.price_layout);
        View cafeDetailsView = root.findViewById(R.id.place_info);

        if (currOrder != null && !currOrder.isEmpty()) {
            TextView empty = root.findViewById(R.id.empty_text);
            empty.setVisibility(View.INVISIBLE);

            Button submitButton = root.findViewById(R.id.submit_order_button);
            submitButton.setVisibility(View.VISIBLE);

            list = root.findViewById(R.id.item_list);
            list.setLayoutManager(
                    new LinearLayoutManager(this.getContext())
            );

            adapter = new OrderAdapter(((MainActivity) getActivity()).getCurrentOrder());
            list.setAdapter(adapter);

            TextView placeName = root.findViewById(R.id.place_name);
            placeName.setText(currOrder.getCafeName());

            ImageView logo = root.findViewById(R.id.logo);
            listener.loadImage(logo, currOrder.getLogo());

            TextView totalPrice = root.findViewById(R.id.order_total_price);
            totalPrice.setText(currOrder.getTotalPrice());

            costView.setVisibility(View.VISIBLE);
            cafeDetailsView.setVisibility(View.VISIBLE);
        }

        Button clearButton = root.findViewById(R.id.clear_basket_button);
        clearButton.setOnClickListener(mClearListener);

        Button submitButton = root.findViewById(R.id.submit_order_button);
        View.OnClickListener submitListener = v -> listener.onSubmitOrder();
        submitButton.setOnClickListener(submitListener);

        //mOrderListViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        //mOrderListViewModel.createOrder(1, );

        return root;
    }


}
