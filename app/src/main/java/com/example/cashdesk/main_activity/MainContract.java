package com.example.cashdesk.main_activity;

import android.content.Context;

import com.example.cashdesk.models.LoginRequest;

public interface MainContract {

    interface Presenter {
        void onEnterBtnClicked(LoginRequest request);
    }

    interface View {
        void showProgressBar();
        void hideProgressBar();
        Context getContext();
        void showErrorMessage();
        void onStartSecondActivity(String authKey);
    }

}
