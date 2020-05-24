package com.nozimy.vegandelivery.ui.place_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.MyPlace;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyHolder> {
    private AdapterListener listener;
    private List<MyPlace> mMyPlaces;

    public void setPlaceList(List<MyPlace> myPlaces) {
        mMyPlaces = myPlaces;
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onItemClick(MyPlace myPlace);
    }

    public void setListener(AdapterListener listener) {
        this.listener = listener;
    }

    public void updateWith(List<MyPlace> newMyPlaces) {
        mMyPlaces.clear();
        mMyPlaces.addAll(newMyPlaces);
        notifyItemInserted(mMyPlaces.size()-1);
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
        MyPlace myPlace = mMyPlaces.get(position);

        holder.name.setText(myPlace.getName());
        holder.deliveryTime.setText(myPlace.getDeliveryTimeString());
        holder.grade.setText(myPlace.getGradeString());
        holder.minOrderCost.setText(myPlace.getMinOrderCostString());
        holder.bindClickListener(position);
    }

    @Override
    public int getItemCount() {
        if (mMyPlaces == null){
            return 0;
        }
        return mMyPlaces.size();
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
                    listener.onItemClick(mMyPlaces.get(pos));
                }
            });
        }
    }
}
