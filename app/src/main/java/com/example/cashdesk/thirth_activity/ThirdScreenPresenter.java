package com.example.cashdesk.thirth_activity;

import android.annotation.SuppressLint;

import com.example.cashdesk.api.ApiComponent;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.Product;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ThirdScreenPresenter implements ThirdScreenContract .Presenter{

    public ThirdScreenContract.View view;

    ThirdScreenPresenter(ThirdScreenContract.View view){
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void removeItem(Product item, Order order) {
        ApiComponent.provideApi().removeItem(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                item.getProduct_id(),order.getOrder_id())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.onRemoveItem(item);
                            } else {

                                view.onShowErrorMessage(response.getMessage());
                            }

                        },

                        throwable ->   view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void setStockStatus(Product item, int inStock, Order order) {
        ApiComponent.provideApi().setOutStock(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                order.getOrder_id(),item.getProduct_id(), inStock)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                               view.onSetStockStatus(response.getData());
                            } else {
                               view.onShowErrorMessage(response.getMessage());
                            }
                        },
                        throwable ->   view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void setIsRefused(Product item, int isRefused, Order order) {
        ApiComponent.provideApi().setIsRefused(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                order.getOrder_id(),item.getProduct_id(), isRefused)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.onSetRefusedStatus(response.getData());
                            } else {
                                view.onShowErrorMessage(response.getMessage());
                            }
                        },
                        throwable ->   view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void printOrder(Order order, List<String> list) {
        ApiComponent.provideApi().getPDFURL(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),list,0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                               view.onPrintOrder(response.getData());
                            } else {
                               view.onShowErrorMessage(response.getMessage());
                            }

                        },

                        throwable ->  view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void addMessage(Order order, String text) {
        ApiComponent.provideApi().addComments(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                order.getOrder_id().toString(), text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                              view.onAddMessage(response.getData());
                            } else {
                                view.onShowErrorMessage(response.getMessage());
                            }
                        },

                        throwable -> view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void setChangeQuantity(Product item, int qty, Order order) {
        ApiComponent.provideApi().changeQTY(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                item.getProduct_id(),order.getOrder_id(),qty)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                               view.onChangeQuantity(response.getData());
                            } else {
                               view.onShowErrorMessage(response.getMessage());
                            }

                        },

                        throwable ->   view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCancelReasonList() {
        ApiComponent.provideApi().getReasonsList(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response.getStatus().equals("success")) {
                               view.onShowReasonList(response.getData());
                            } else {
                               view.onShowErrorMessage(response.getMessage());
                            }

                        },

                        throwable -> view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void changeOrderStatus(Order order, String status, Integer reason, String text) {
        ApiComponent.provideApi().setOrderStatus(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),
                order.getOrder_id().toString(),status,reason,text)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.onChangeOrderStatus(response.getData());
                            } else {

                            }

                        },

                        throwable ->  view.onErrorConnection());

    }

    @SuppressLint("CheckResult")
    @Override
    public void getOrder(Integer id) {
        ApiComponent.provideApi().getOrder(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER),id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if(response.getStatus().equals("success")){
                                view.onRessetOrder(response.getData());
                            } else {

                            }

                        },

                        throwable ->  view.onErrorConnection());
    }

    @SuppressLint("CheckResult")
    @Override
    public void sendNotCorrectMessage(Integer id) {
        ApiComponent.provideApi().sendNotCorrectImage(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            view.onShowResultNotCorrectImage();

                        },

                        throwable ->  view.onErrorConnection());
    }
}
