package com.example.cashdesk.second_activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cashdesk.R;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.OrdersStatusResponse;
import com.example.cashdesk.thirth_activity.ThirdActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SecondScreenContract.View {

    private static final String STATUS = "extra_status";
    private static final String QUERY = "extra_query";

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;


    private GridLayoutManager mGridLayoutManager;
    private OrderListAdapter orderListAdapter;
    private SecondScreenContract.Presenter mPresenter;
    private String status;
    private String query;
    private boolean isInPrintMode = false;
    String[] genders;

    public static OrdersListFragment newInstance(String status, String query) {
        Bundle args = new Bundle();
        args.putString(STATUS, status);
        args.putString(QUERY, query);
        OrdersListFragment fragment = new OrdersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders_list, container, false);
        ButterKnife.bind(this, v);

        pb.setVisibility(View.VISIBLE);
        status = getArguments().getString(STATUS);
        query = getArguments().getString(QUERY);

        mPresenter = new SecondScreenPresenter(this);

        swipe_container.setColorSchemeResources(R.color.colorAccent);

        rvProducts.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rvProducts.setLayoutManager(mGridLayoutManager);
        swipe_container.setOnRefreshListener(this);

        mPresenter.getOrders(status, query);

        orderListAdapter = new OrderListAdapter(getActivity(), new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order item) {
                ThirdActivity.startActivity(getActivity(), item);
            }

            @Override
            public void onSpinnerItemClick(Order order, View view) {
                genders = getResources().getStringArray(R.array.animals);
                int hidingItemIndex = 0;

                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);

                getActivity().getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
                if (order.getStatus().equals("collected") || order.getStatus().equals("completed") ||
                        order.getStatus().equals("cancelled")) {
                    popupMenu.getMenu().findItem(R.id.action_pick).setVisible(false);
                } else {
                    popupMenu.getMenu().findItem(R.id.action_pick).setVisible(true);
                }
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.action_print) {
                        onPrintOrder(new ArrayList<String>() {{
                            add(order.getOrder_id().toString());
                        }});
                    } else if (item.getItemId() == R.id.action_pick) {
                        onCollectOrder(order);
                    }
                    return true;
                });
                popupMenu.show();
            }


        }, product -> {
            BigDialog dialog = BigDialog.newInstance(product);
            dialog.show(getFragmentManager(), null);
            dialog.setAction(() -> showDialogNotCorrectImage(PreferenceHelper.getInstance().getInt(PreferenceHelper.Key.PRODUCT_ID)));
        });

        rvProducts.setAdapter(orderListAdapter);

        return v;
    }

    private void showDialogNotCorrectImage(int productId) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //todo translate
        builder.setTitle("Отправить информацию о несоответствующем фото?")
                .setNegativeButton(getText(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(getText(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.sendInfoAboutImage(productId);
                        pb.setVisibility(View.VISIBLE);
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void showPrintDialog(List<String> checkedItems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //todo translate
        builder.setTitle("Выберете вариант печати")
                .setNegativeButton(getText(R.string.goods_list), (dialog, id) -> {
                    mPresenter.printOrder(checkedItems, 1);
                    pb.setVisibility(View.VISIBLE);
                })
                .setPositiveButton(R.string.orders, (dialog, which) -> {
                    mPresenter.printOrder(checkedItems, 0);
                    pb.setVisibility(View.VISIBLE);
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            swipe_container.setRefreshing(false);
            mPresenter.refreshOrdersLIst(status, query);
        }, 1000);
    }

    private void onCollectOrder(Order item) {
        pb.setVisibility(View.VISIBLE);
        mPresenter.updateStatusOrder(item);
    }

    private void onPrintOrder(ArrayList<String> orderIds) {
        pb.setVisibility(View.VISIBLE);
        mPresenter.printOrder(orderIds, 0);
    }

    public void printCheckedItems() {
        List<String> checkedItems = orderListAdapter.getCheckedItems();
        if (checkedItems.size() > 0)
            showPrintDialog(checkedItems);
    }

    public void setIsInPrintMode(boolean isInPrintMode) {
        if (this.isInPrintMode != isInPrintMode) {
            this.isInPrintMode = isInPrintMode;
            orderListAdapter.setIsInPrintMode(isInPrintMode);
            orderListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateListOrder(List<OrdersStatusResponse> list, String message) {

    }

    @Override
    public void onShowErrorMessage(String message) {
        pb.setVisibility(View.GONE);
        if (!message.equals(""))
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void printPDF(String url) {
        pb.setVisibility(View.GONE);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void orderListUpdate(List<Order> list) {
        orderListAdapter.addAll(list);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void updateStatusOrder(Order item) {
        mPresenter.refreshOrdersLIst(status, query);
        Toast.makeText(getActivity(), getText(R.string.order) + item.getOrder_id().toString() + getText(R.string.collected), Toast.LENGTH_LONG).show();
    }

    @Override
    public void refreshOrderList(List<Order> list) {
        orderListAdapter.setItems(list);
        pb.setVisibility(View.GONE);
        Toast.makeText(getActivity(), R.string.page_reloaded, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorConnection() {
        pb.setVisibility(View.GONE);
        Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowResultNotCorrectImage() {
        //todo translate
        Toast.makeText(getActivity(), "Уведомление отправленно!", Toast.LENGTH_LONG).show();
        pb.setVisibility(View.GONE);
    }
}
