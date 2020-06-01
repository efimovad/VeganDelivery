package com.nozimy.vegandelivery.ui.dish_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;

import com.nozimy.vegandelivery.MainActivity;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<com.nozimy.vegandelivery.ui.dish_list.DishAdapter.MyHolder> {
    private DishListener listener;
    private List<Dish> dishes = new ArrayList<>();
    public static final int MAX_TITLE_LEN = 25;
    public Context context;

    public DishAdapter(Context context) {
        this.context = context;
    }

    public interface DishListener {
        void onItemClick(Dish dish, int position);
        void loadDishImage(ImageView view, String url);
        boolean inOrder(Dish dish);
    }

    public void setListener(DishListener listener) {
        this.listener = listener;
    }

    public void updateWith(List<Dish> newDishes) {
        dishes.clear();
        dishes.addAll(newDishes);
        notifyItemInserted(dishes.size()-1);
    }

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

        if (dish.getName().length() < MAX_TITLE_LEN) {
            holder.name.setText(dish.getName());
        } else {
            String dishName = dish.getName().substring(0, MAX_TITLE_LEN).concat("...");
            holder.name.setText(dishName);
        }

        holder.cost.setText(dish.getCostString());
        holder.params.setText(dish.getWeightString()+ " " +dish.getСalories());

        if (listener.inOrder(dishes.get(position))) {
            holder.isSelected.setVisibility(View.VISIBLE);
        }

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
        private TextView params;
        private ImageView image;
        private View isSelected;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.dish_name);
            cost = itemView.findViewById(R.id.dish_cost);
            params = itemView.findViewById(R.id.dish_params);
            image = itemView.findViewById(R.id.dish_avatar);
            isSelected = itemView.findViewById(R.id.item_selected);
        }

        void bindClickListener(int position) {
            itemView.findViewById(R.id.dish_card).setOnClickListener(v -> {
                listener.onItemClick(dishes.get(position), position);
            });
        }

    }

    public void setDishList(List<Dish> dishList) {
        dishes = dishList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return dishes.get(position).getId();
    }
}