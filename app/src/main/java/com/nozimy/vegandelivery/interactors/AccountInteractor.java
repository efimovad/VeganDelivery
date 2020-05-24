package com.nozimy.vegandelivery.interactors;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.network.AccountApi;

public class AccountInteractor {
    private final Context mContext;
    private DataRepository mDataRepository;
    private AccountApi mAccountApi;
    private LiveData<PersonEntity> mCurrentPerson;
    private GoogleSignInAccount account;

    public AccountInteractor(Context context){
        mContext = context;
        mDataRepository = new DataRepository(context);

        account = GoogleSignIn.getLastSignedInAccount(mContext);
        if (account == null){
            mCurrentPerson = null;
        } else {
            mCurrentPerson = mDataRepository.getPerson(account.getId());
        }
    }

    public LiveData<PersonEntity> getCurrentPerson(){
        return mCurrentPerson;
    };

    public void insertPerson(PersonEntity personEntity){
        mDataRepository.insertPerson(personEntity);
    }

    public void insertPerson(GoogleSignInAccount account){
        PersonEntity personEntity = new PersonEntity(account.getId(), account.getDisplayName(), "", account.getEmail());
        mDataRepository.insertPerson(personEntity);
    }

    public void updatePerson(String id, String name, String phone, String email){
        mDataRepository.updatePerson(id, name, phone, email);
    }

    public void updateCurrentPerson(String name, String phone, String email){
        if (account == null){
            return;
        }

        mDataRepository.updatePerson(account.getId(), name, phone, email);
    }

}
