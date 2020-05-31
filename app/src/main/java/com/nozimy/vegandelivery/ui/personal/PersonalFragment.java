package com.nozimy.vegandelivery.ui.personal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.ui.PersonEdit.PersonEditFragment;
import com.nozimy.vegandelivery.ui.auth.AuthFragment;
import com.nozimy.vegandelivery.ui.basket.BasketFragment;
import com.nozimy.vegandelivery.ui.order.OrderFragment;


public class PersonalFragment extends Fragment {
    private PersonalFragmentListener listener;
    private PersonalViewModel personalViewModel;
    private RecyclerView recyclerView;

    private GoogleSignInClient mGoogleSignInClient;

    private String changePersonTag = "changePersonTag";

    public interface PersonalFragmentListener {
        void clickOnFavoriteButton();
        void clickOnOrdersButton();
        void clickOnSalesButton();
        void clickOnLogout();
        void setUser(String user);
    }

    public void setListener(PersonalFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personalViewModel =
                ViewModelProviders.of(this).get(PersonalViewModel.class);

        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        Button button = root.findViewById(R.id.logout_button);
        button.setOnClickListener(v -> signOut());

        Button changePersonButton = root.findViewById(R.id.item);
        Button ordersButton = root.findViewById(R.id.orders);
        ordersButton.setOnClickListener(v -> listener.clickOnOrdersButton());

        Button favoritePlacesButton = root.findViewById(R.id.favorite_places);
        favoritePlacesButton.setOnClickListener(v -> listener.clickOnFavoriteButton());

        Button stocksButton = root.findViewById(R.id.sales);
        stocksButton.setOnClickListener(v -> listener.clickOnSalesButton());

        changePersonButton.setOnClickListener(v -> getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new PersonEditFragment(), changePersonTag)
                .addToBackStack(changePersonTag)
                .commit());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        return root;
    }

    private void signOut() {
        // Signing out clears the current authentication state and resets the default user,
        // this should be used to "switch users" without fully un-linking the user's google
        // account from your application.
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.clickOnLogout();
            }
        });
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