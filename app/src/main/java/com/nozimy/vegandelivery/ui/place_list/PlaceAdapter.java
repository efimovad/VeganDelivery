package com.nozimy.vegandelivery.ui.place_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyHolder> {
    private AdapterListener listener;
    private List<MyPlace> mMyPlaces;
    private Context context;

    public PlaceAdapter(Context context) {
        this.context = context;
    }

    public void setPlaceList(List<MyPlace> myPlaces) {
        this.mMyPlaces = myPlaces;
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onItemClick(MyPlace myPlace);
        void loadPlaceImage(ImageView view, String url);
        void changeFavStatus(long id, boolean value, int position);
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    public void updateWith(List<MyPlace> newMyPlaces) {
        mMyPlaces.clear();
        mMyPlaces.addAll(newMyPlaces);
        notifyItemInserted(mMyPlaces.size()-1);
    }

    public int removeAt(int position) {
        mMyPlaces.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mMyPlaces.size());
        return mMyPlaces.size();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutToInflate = R.layout.shop;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutToInflate, parent, false);

        return new MyHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyPlace myPlace = mMyPlaces.get(position);

        holder.name.setText(myPlace.getName());
        holder.deliveryTime.setText(myPlace.getDeliveryTimeString());
        holder.grade.setText(myPlace.getGradeString());
        holder.minOrderCost.setText(myPlace.getMinOrderCostString());
        holder.bindClickListener(position);
        listener.loadPlaceImage(holder.image, myPlace.getImage());

        View.OnClickListener oclBtnOk = v1 -> {
            if (myPlace.getFavourite()) {
                listener.changeFavStatus(mMyPlaces.get(position).getId(), false, position);
                myPlace.setFavourite(false);
            } else {
                listener.changeFavStatus(mMyPlaces.get(position).getId(), true, position);
                myPlace.setFavourite(true);
            }
            notifyItemChanged(position);
            Toast toast = Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT);
            toast.show();
        };

        holder.favButton.setImageResource(R.drawable.ic_favorite_24px);
        holder.favButton.setOnClickListener(oclBtnOk);
        if (myPlace.getFavourite()) {
            holder.favButton.setColorFilter(context.getResources().getColor(R.color.colorCanceled));
        } else {
            holder.favButton.setColorFilter(context.getResources().getColor(R.color.colorLightPink));
        }

    }

    @Override
    public int getItemCount() {
        if (mMyPlaces == null){
            return 0;
        }
        return mMyPlaces.size();
    }

    @Override
    public long getItemId(int position) {
        return mMyPlaces.get(position).getId();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView minOrderCost;
        private TextView grade;
        private TextView deliveryTime;
        private ImageView image;
        private ImageButton favButton;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            deliveryTime = itemView.findViewById(R.id.place_delivery_time);
            grade = itemView.findViewById(R.id.place_grade);
            minOrderCost = itemView.findViewById(R.id.place_min_cost);
            image = itemView.findViewById(R.id.place_avatar);
            favButton = itemView.findViewById(R.id.fav_button);
        }

        // TODO: check why need final
        void bindClickListener(final int pos) {
            itemView.findViewById(R.id.place_card).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onItemClick(mMyPlaces.get(pos));
                }
            });
        }
    }
}
