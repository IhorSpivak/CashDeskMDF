package com.example.cashdesk.second_activity;

import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.OrdersStatusResponse;

import java.util.List;

public class SecondScreenContract {

    interface View{
        void updateListOrder(List<OrdersStatusResponse> list, String message);
        void onShowErrorMessage(String message);
        void printPDF(String url);
        void orderListUpdate(List<Order> list);
        void updateStatusOrder(Order item);
        void refreshOrderList(List<Order> list);
        void onErrorConnection();
        void onShowResultNotCorrectImage();

    }

    public interface Presenter{
        void getStatusOrders(String query);
        void printOrder(List<String> listOrderd, int sort);
        void getOrders(String status, String query);
        void updateStatusOrder(Order item);
        void refreshOrdersLIst(String status, String query);
        void sendInfoAboutImage(Integer id);
    }
}