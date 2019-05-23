package com.example.cashdesk.second_activity;

import android.annotation.SuppressLint;

import com.example.cashdesk.api.ApiComponent;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.Order;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SecondScreenPresenter implements SecondScreenContract.Presenter {

    public SecondScreenContract.View view;

    SecondScreenPresenter(SecondScreenContract.View view){
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getStatusOrders(String query) {
        ApiComponent.provideApi().getOrdersStatus(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response.getStatus().equals("success")){
                        view.updateListOrder(response.getData(), response.getMessage());
                    } else {
                        view.onShowErrorMessage(response.getMessage());
                    }
                    },

                        throwable -> view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void printOrder(List<String> listOrder, int sort) {
        ApiComponent.provideApi().getPDFURL(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER), listOrder,sort)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                               view.printPDF(response.getData());
                            } else {
                                view.printPDF(response.getData());
                            }

                        },

                        throwable ->  view.onErrorConnection());

    }

    @SuppressLint("CheckResult")
    @Override
    public void getOrders(String status, String query) {
        ApiComponent.provideApi().getOrders(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER), status, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response.getStatus().equals("success")){
                        view.orderListUpdate(response.getData());
                    } else {
                        view.onShowErrorMessage(response.getMessage());
                    }

                        },

                        throwable -> view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateStatusOrder(Order item) {
        ApiComponent.provideApi().setOrderStatus(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                item.getOrder_id().toString(),"collected",null,null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.updateStatusOrder(item);
                            } else {
                                view.onShowErrorMessage(response.getMessage());
                           }

                        },

                        throwable ->  view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void refreshOrdersLIst(String status, String query) {
        ApiComponent.provideApi().getOrders(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER), status, query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.refreshOrderList(response.getData());
                            } else {
                                view.onShowErrorMessage(response.getMessage());
                            }

                        },

                        throwable -> view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void sendInfoAboutImage(Integer id) {
        ApiComponent.provideApi().sendNotCorrectImage(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            view.onShowResultNotCorrectImage();

                        },

                        throwable -> view.onErrorConnection());
    }
}
