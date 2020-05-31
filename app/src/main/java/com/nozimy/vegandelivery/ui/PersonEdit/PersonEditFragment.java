package com.nozimy.vegandelivery.ui.PersonEdit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.ui.personal.PersonalViewModel;

import java.util.List;

public class PersonEditFragment extends Fragment {
    private PersonEditViewModel mPersonEditViewModel;

    TextInputEditText editName;
    TextInputEditText editPhone;
    TextInputEditText editEmail;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPersonEditViewModel =
                ViewModelProviders.of(this).get(PersonEditViewModel.class);

        View root = inflater.inflate(R.layout.fragment_person_edit, container, false);

        editName = root.findViewById(R.id.edit_name);
        editPhone = root.findViewById(R.id.edit_phone);
        editEmail = root.findViewById(R.id.edit_email);
        Button saveButton = root.findViewById(R.id.edit_button_save);

        Observer<PersonEntity> observer = personEntity -> {
            if (personEntity != null) {
                editName.setText(personEntity.getName());
                editPhone.setText(personEntity.getPhone());
                editEmail.setText(personEntity.getEmail());
            }
        };

        mPersonEditViewModel.getCurrentPerson().observe(getViewLifecycleOwner(), observer);

        saveButton.setOnClickListener(v -> {
            mPersonEditViewModel.savePerson(editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString());
            Toast.makeText(getActivity(),"Сохранено",Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
