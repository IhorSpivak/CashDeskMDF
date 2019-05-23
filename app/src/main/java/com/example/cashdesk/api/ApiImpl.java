package com.example.cashdesk.api;

import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.BaseResponse;
import com.example.cashdesk.models.Comments;
import com.example.cashdesk.models.LoginRequest;
import com.example.cashdesk.models.LoginResponse;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.OrdersStatusResponse;
import com.example.cashdesk.models.ReesonResponse;

import java.util.List;

import io.reactivex.Observable;

public class ApiImpl {
    private ApiInterface mApi;

    public ApiImpl(ApiInterface apiInterface) {
        mApi = apiInterface;
    }

    public Observable<BaseResponse<LoginResponse>> userLogin(LoginRequest request) {
        return mApi.userLogin(request);
    }

    public Observable<BaseResponse<List<OrdersStatusResponse>>> getOrdersStatus(String userKey, String query) {
        return mApi.getOrdersStatus(userKey,query);
    }

    public Observable<BaseResponse<List<Order>>> getOrders (String keyUser, String status,String query) {
        return mApi.getOrders(keyUser,0,200, status, query);
    }

    public Observable<BaseResponse<Boolean>> sendNotCorrectImage (Integer id) {
        return mApi.sendNotCorrectImage(PreferenceHelper.getInstance().getString(PreferenceHelper.Key.KEY_USER), id);
    }

    public Observable<BaseResponse<String>> getPDFURL (String keyUser, List<String> listOrders, Integer sort) {
        return mApi.getPDFURL(keyUser, listOrders, sort);
    }

    public Observable<BaseResponse<List<Comments>>> addComments(String keyUser, String orderID, String comment) {
        return mApi.addComments(keyUser,orderID,comment);
    }

    public Observable<BaseResponse<Order>> setOrderStatus(String keyUser, String orderID, String status, Integer reason, String text) {
        return mApi.setOrderStatus(keyUser, orderID,status, reason,text);
    }

    public Observable<BaseResponse<List<ReesonResponse>>> getReasonsList(String keyUser) {
        return mApi.getReasonsList(keyUser);
    }

    public Observable<BaseResponse<Order>> setOutStock(String keyUser, Integer orderId, Integer productId, Integer isObtainable) {
        return mApi.setOutStock(keyUser, orderId,productId,isObtainable);
    }

    public Observable<BaseResponse<Order>> setIsRefused(String keyUser, Integer orderId, Integer productId, Integer isRefused) {
        return mApi.setIsRefused(keyUser, orderId,productId,isRefused);
    }

    public Observable<BaseResponse<Order>> removeItem(String keyUser,int productId, int orderId) {
        return mApi.removeItem(keyUser,orderId,productId);
    }

    public Observable<BaseResponse<Order>> changeQTY(String keyUser, int productId, int orderId, int qty) {
        return mApi.changeQTY(keyUser, orderId,productId, qty);
    }

    public Observable<BaseResponse<Order>> getOrder(String keyUser, int orderId) {
        return mApi.getOrder(keyUser, orderId);
    }


}

