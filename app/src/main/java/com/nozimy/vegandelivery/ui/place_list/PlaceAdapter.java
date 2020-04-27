package com.nozimy.vegandelivery.ui.place_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyHolder> {
    private AdapterListener listener;
    private List<Place> mPlaces;

    public void setPlaceList(List<Place> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onItemClick(Place place);
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    public void updateWith(List<Place> newPlaces) {
        mPlaces.clear();
        mPlaces.addAll(newPlaces);
        notifyItemInserted(mPlaces.size()-1);
    }

    //public void addItem() {
        //DataSource.getInstance().updateData();
        //updateWith(DataSource.getInstance().getData());
    //}

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
        Place place = mPlaces.get(position);

        holder.name.setText(place.getName());
        holder.deliveryTime.setText(place.getDeliveryTimeString());
        holder.grade.setText(place.getGradeString());
        holder.minOrderCost.setText(place.getMinOrderCostString());
        holder.bindClickListener(position);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView minOrderCost;
        private TextView grade;
        private TextView deliveryTime;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            deliveryTime = itemView.findViewById(R.id.place_delivery_time);
            grade = itemView.findViewById(R.id.place_grade);
            minOrderCost = itemView.findViewById(R.id.place_min_cost);

        }

        // TODO: check why need final
        void bindClickListener(final int pos) {
            itemView.findViewById(R.id.place_card).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onItemClick(mPlaces.get(pos));
                }
            });
        }
    }
}
