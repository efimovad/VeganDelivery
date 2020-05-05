package com.nozimy.vegandelivery.ui.basket;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
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
import com.nozimy.vegandelivery.db.model.Order;

public class BasketFragment extends Fragment {

    private BasketViewModel basketViewModel;
    private RecyclerView list;
    private View root;
    private OrderAdapter adapter;
    private View.OnClickListener mClearListener = v -> {
        MainActivity activity = (MainActivity) getActivity();
        Order currOrder = activity.getCurrentOrder();
        currOrder.clear();

        TextView empty = root.findViewById(R.id.empty_text);
        empty.setVisibility(View.VISIBLE);

        Button submitButton = root.findViewById(R.id.submit_order_button);
        submitButton.setVisibility(View.INVISIBLE);

        adapter.notifyDataSetChanged();
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        basketViewModel =
                ViewModelProviders.of(this).get(BasketViewModel.class);

        root = inflater.inflate(R.layout.fragment_basket, container, false);

        MainActivity activity = (MainActivity) getActivity();
        Order currOrder = activity.getCurrentOrder();

        if (!currOrder.isEmpty()) {
            TextView empty = root.findViewById(R.id.empty_text);
            empty.setVisibility(View.INVISIBLE);

            Button submitButton = root.findViewById(R.id.submit_order_button);
            submitButton.setVisibility(View.VISIBLE);

            list = root.findViewById(R.id.item_list);
            list.setLayoutManager(
                    new LinearLayoutManager(this.getContext())
            );

            //adapter.setListener(this);
            adapter = new OrderAdapter(((MainActivity) getActivity()).getCurrentOrder());
            list.setAdapter(adapter);
        }

        Button clearButton = root.findViewById(R.id.clear_basket_button);
        clearButton.setOnClickListener(mClearListener);

        return root;
    }
}
