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
import androidx.recyclerview.widget.RecyclerView;
import com.nozimy.vegandelivery.R;


public class PersonalFragment extends Fragment {
    private PersonalFragmentListener listener;
    private PersonalViewModel personalViewModel;
    private RecyclerView recyclerView;

    public interface PersonalFragmentListener {
        void clickOnFavoriteButton();
        void clickOnPersonalButton();
        void clickOnOrdersButton();
        void clickOnSalesButton();
    }

    public void setListener(PersonalFragmentListener listener) {
        this.listener = listener;
    }

    public PersonalFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personalViewModel =
                ViewModelProviders.of(this).get(PersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

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