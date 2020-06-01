package com.nozimy.vegandelivery.ui.dish_list;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.MyPlace;

public class DishDialogFragment extends BottomSheetDialogFragment {
    private DishDialogListener listener;
    private View mRoot;
    private Dish mDish;
    private  int mCount;
    private MyPlace mPlace;
    private int position;

    public DishDialogFragment(Dish dish, int count, MyPlace place, int position) {
        this.mDish = dish;
        this.mCount = count;
        this.mPlace = place;
        this.position = position;
    }

    public interface DishDialogListener {
        int increment(Dish dish, MyPlace place);
        int decrement(Dish dish);
        void loadImage(ImageView view, String url);
        void showNotification(String name);
        void updateOnPosition(int position);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.dish_details, container, false);

        ImageButton incButton = mRoot.findViewById(R.id.increment);
        View.OnClickListener incListener = v -> {
            int result = listener.increment(mDish, mPlace);
            if (result != -1) {
                setCount(result);
            } else {
                getDialog().dismiss();
                listener.showNotification(mPlace.getName());
            }
            listener.updateOnPosition(position);
        };
        incButton.setOnClickListener(incListener);

        ImageButton decButton = mRoot.findViewById(R.id.decrement);
        View.OnClickListener decListener = v -> {
            setCount(listener.decrement(mDish));
            listener.updateOnPosition(position);
        };
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

        ImageView image = mRoot.findViewById(R.id.dish_avatar);
        listener.loadImage(image, mDish.getImage());

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
