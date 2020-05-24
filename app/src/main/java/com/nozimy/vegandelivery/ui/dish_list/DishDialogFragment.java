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
        int increment(Dish dish);
        int decrement(Dish dish);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.dish_details, container, false);

        ImageButton incButton = mRoot.findViewById(R.id.increment);
        View.OnClickListener incListener = v -> { setCount(listener.increment(mDish)); };
        incButton.setOnClickListener(incListener);

        ImageButton decButton = mRoot.findViewById(R.id.decrement);
        View.OnClickListener decListener = v -> { setCount(listener.decrement(mDish)); };
        decButton.setOnClickListener(decListener);

        TextView name = mRoot.findViewById(R.id.dish_name);
        name.setText(mDish.getName());

        TextView ingredients = mRoot.findViewById(R.id.dish_ingredients);
        ingredients.setText(mDish.getIngredients());

        TextView params = mRoot.findViewById(R.id.dish_params);
        params.setText(mDish.getWeightString() + " " + mDish.getСalories());

        TextView price = mRoot.findViewById(R.id.dish_price);
        price.setText(mDish.getCostString());

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
