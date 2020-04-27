package com.nozimy.vegandelivery.ui.personal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;

import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalSettingViewHolder> {

    class PersonalSettingViewHolder extends RecyclerView.ViewHolder {
        private final TextView settingItemView;

        private PersonalSettingViewHolder(View itemView) {
            super(itemView);
            settingItemView = itemView.findViewById(R.id.item);
        }
    }

    private final LayoutInflater mInflater;
    private List<String> mList;

    PersonalAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public PersonalSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_personal, parent, false);
        return new PersonalSettingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonalSettingViewHolder holder, int position) {
        if (mList != null) {
            String current = mList.get(position);
            holder.settingItemView.setText(current);
        } else {
            holder.settingItemView.setText("No list");
        }
    }

    void setWords(List<String> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else return 0;
    }
}
