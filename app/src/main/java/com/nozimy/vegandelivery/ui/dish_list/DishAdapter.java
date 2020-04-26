package com.nozimy.vegandelivery.ui.dish_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<com.nozimy.vegandelivery.ui.dish_list.DishAdapter.MyHolder> {
    private DishListener listener;
    private List<Dish> dishes;

    DishAdapter(List<Dish> list) {
        this.dishes = list;
    }

    public interface DishListener {
        void onItemClick(Dish dish);
        void loadDishImage(ImageView view, String url);
    }

    public void setListener(DishListener listener) {
        this.listener = listener;
    }

    public void updateWith(List<Dish> newDishes) {
        dishes.clear();
        dishes.addAll(newDishes);
        notifyItemInserted(dishes.size()-1);
    }

    //public void addItem() {
    //DataSource.getInstance().updateData();
    //updateWith(DataSource.getInstance().getData());
    //}

    @NonNull
    @Override
    public com.nozimy.vegandelivery.ui.dish_list.DishAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutToInflate = R.layout.dish;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutToInflate, parent, false);

        return new com.nozimy.vegandelivery.ui.dish_list.DishAdapter.MyHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull com.nozimy.vegandelivery.ui.dish_list.DishAdapter.MyHolder holder, int position) {
        Dish dish = dishes.get(position);

        holder.name.setText(dish.getName());
        holder.cost.setText(dish.getCost());
        holder.ingredients.setText(dish.getIngredients());

        listener.loadDishImage(holder.image, dish.getImage());

        holder.bindClickListener(position);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView cost;
        private TextView ingredients;
        private TextView calories;
        private TextView weight;
        private ImageView image;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.dish_name);
            cost = itemView.findViewById(R.id.dish_cost);
            ingredients = itemView.findViewById(R.id.dish_ingredients);
            image = itemView.findViewById(R.id.dish_avatar);
        }

        // TODO: check why need final
        void bindClickListener(final int pos) {
            itemView.findViewById(R.id.dish_card).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onItemClick(dishes.get(pos));
                }
            });
        }
    }
}