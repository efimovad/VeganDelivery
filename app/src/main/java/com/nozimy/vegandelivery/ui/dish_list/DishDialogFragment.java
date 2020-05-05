package com.nozimy.vegandelivery.ui.dish_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;

public class DishDialogFragment extends BottomSheetDialogFragment {
    private DishDialogListener listener;
    private View mRoot;
    private Dish mDish;
    private  int mCount;

    public DishDialogFragment(Dish dish, int count) {
        mDish = dish;
        mCount = count;
    }

    public interface DishDialogListener {
        void increment(Dish dish);
        void decrement(Dish dish);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.dish_details, container, false);

        ImageButton incButton = mRoot.findViewById(R.id.increment);
        View.OnClickListener incListener = v -> {
            listener.increment(mDish);
            mCount++;
            setCount(mCount);
        };
        incButton.setOnClickListener(incListener);

        ImageButton decButton = mRoot.findViewById(R.id.decrement);
        View.OnClickListener decListener = v -> {
            listener.decrement(mDish);
            if (mCount > 0) {
                mCount--;
            }
            setCount(mCount);
        };
        decButton.setOnClickListener(decListener);

        setCount(mCount);

        return mRoot;
    }

    public void setCount(int count) {
        TextView countView = mRoot.findViewById(R.id.dish_count);
        countView.setText(Integer.toString(count));
    }

    public void setListener(DishDialogListener listener) {
        this.listener = listener;
    }
}
