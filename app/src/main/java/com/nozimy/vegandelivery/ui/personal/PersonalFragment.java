package com.nozimy.vegandelivery.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nozimy.vegandelivery.R;

//public class PersonalFragment extends Fragment {
//
//    private PersonalViewModel personalViewModel;
//    private RecyclerView recyclerView;
//    private PersonalAdapter adapter;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        personalViewModel =
//                ViewModelProviders.of(this).get(PersonalViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_personal, container, false);
////        final TextView textView = root.findViewById(R.id.text_notifications);
////        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
//
//        recyclerView = root.findViewById(R.id.personal_list);
//        adapter = new PersonalAdapter(getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        personalViewModel.getSettingItems().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> strings) {
//                adapter.setWords(strings);
//            }
//        });
//
//        return root;
//    }
//}

public class PersonalFragment extends Fragment {
    private PersonalFragmentListener listener;
    private PersonalViewModel model;

    public interface PersonalFragmentListener { }

    public void setListener(PersonalFragmentListener listener) {
        this.listener = listener;
    }

    public PersonalFragment(Context context) {}
    public PersonalFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        model =
                ViewModelProviders.of(this).get(PersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

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