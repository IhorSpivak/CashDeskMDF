package com.example.cashdesk.thirth_activity;

import com.example.cashdesk.models.Comments;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.Product;
import com.example.cashdesk.models.ReesonResponse;

import java.util.List;

public class ThirdScreenContract {

    interface View{
        void onRemoveItem(Product item);
        void onSetStockStatus(Order order);
        void onSetRefusedStatus(Order order);
        void onShowErrorMessage(String message);
        void onPrintOrder(String url);
        void onAddMessage(List<Comments> list);
        void onChangeQuantity(Order order);
        void onShowReasonList(List<ReesonResponse> list);
        void onChangeOrderStatus(Order order);
        void onRessetOrder(Order order);
        void onErrorConnection();
        void onShowResultNotCorrectImage();
    }

    public interface Presenter{

        void removeItem(Product item, Order order);
        void setStockStatus(Product item, int inStock, Order order);
        void setIsRefused(Product item, int isRefused, Order order);
        void  printOrder(Order order, List<String> list);
        void addMessage(Order order, String text);
        void setChangeQuantity(Product item, int qty, Order order);
        void getCancelReasonList();
        void changeOrderStatus(Order order, String status, Integer reason, String message);
        void getOrder(Integer id);
        void sendNotCorrectMessage(Integer id);
    }
}
