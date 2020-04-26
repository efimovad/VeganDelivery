package com.nozimy.vegandelivery.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;

import java.util.List;

public class PersonalFragment extends Fragment {
    private PersonalFragmentListener listener;

    private PersonalViewModel personalViewModel;
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;

    public interface PersonalFragmentListener { }

    public void setListener(PersonalFragmentListener listener) {
        this.listener = listener;
    }

    public PersonalFragment(Context context) {}
    public PersonalFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personalViewModel =
                ViewModelProviders.of(this).get(PersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        recyclerView = root.findViewById(R.id.personal_list);
        adapter = new PersonalAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        personalViewModel.getSettingItems().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter.setWords(strings);
            }
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}