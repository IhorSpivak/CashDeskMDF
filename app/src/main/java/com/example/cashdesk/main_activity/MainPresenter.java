package com.example.cashdesk.main_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.cashdesk.api.ApiComponent;
import com.example.cashdesk.helper.MainHelper;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.LoginRequest;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private Context context;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        context = view.getContext();
    }


    @SuppressLint("CheckResult")
    @Override
    public void onEnterBtnClicked(LoginRequest request) {
        ApiComponent.provideApi().userLogin(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            String locale = response.getData().getLocale();
                            MainHelper.setLocale(context, new Locale(locale.toLowerCase()));
                            PreferenceHelper.getInstance(context).put(PreferenceHelper.Key.LOCALE, locale);
                            if (response.getStatus().equals("success")) {
                                PreferenceHelper.getInstance(context).put(PreferenceHelper.Key.AUTH_KEY, response.getData().getAuth_key());
                                PreferenceHelper.getInstance(context).put(PreferenceHelper.Key.SHOP_TYPE, response.getData().getTypeSHop());
                                view.onStartSecondActivity(response.getData().getAuth_key());
                            }
                            if (response.getStatus().equals("fail")) {
                                Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                                view.hideProgressBar();
                            }
                            if (response.getStatus().equals("error")) {
                                Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                                view.hideProgressBar();
                            }
                        },

                        throwable -> view.showErrorMessage());
    }
}
