package com.example.cashdesk.second_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashdesk.R;
import com.example.cashdesk.helper.MainHelper;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.main_activity.MainActivity;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.OrdersStatusResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends FragmentActivity implements SecondScreenContract.View {

    public static final String EXTRA_AUTH_KEY = "extra_auth_key";

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.icon_logout)
    ImageView icon_logout;

    @BindView(R.id.icon_print)
    ImageView icon_print;

    @BindView(R.id.rlFAB)
    RelativeLayout btnFAB;

    @BindView(R.id.rlFABCancel)
    RelativeLayout btnFABCancel;

    @BindView(R.id.tab_bar)
    TabLayout tabLayout;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.search)
    SearchView search;

    List<OrdersStatusResponse> listOrders;
    private PagerFragmentAdapter adapter;
    private SecondScreenContract.Presenter mPresenter;
    private String query = "";
    private boolean isInPrintMode = false;

    public static void startActivity(Activity activity, String auth_key) {
        Intent intent = new Intent(activity, SecondActivity.class);
        intent.putExtra(EXTRA_AUTH_KEY, auth_key);

        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        mPresenter = new SecondScreenPresenter(this);

        changeNavigationIconsSettings();

        search.setIconifiedByDefault(false);
        search.setQueryHint(getText(R.string.search_by_number_or_name));
        SearchView.SearchAutoComplete searchAutoComplete = search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);

        searchAutoComplete.setTextColor(Color.BLACK);
        LinearLayout searchEditFrame = search.findViewById(R.id.search_edit_frame); // Get the Linear Layout
        ((LinearLayout.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 0;

        String key = getIntent().getStringExtra(EXTRA_AUTH_KEY);
        PreferenceHelper.getInstance(this).put(PreferenceHelper.Key.KEY_USER, key);

        adapter = new PagerFragmentAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                isInPrintMode = false;
                btnFAB.setVisibility(View.GONE);
                btnFABCancel.setVisibility(View.GONE);
                OrdersListFragment prevFragment = adapter.getItem(i - 1);
                OrdersListFragment nextFragment = adapter.getItem(i + 1);
                if (prevFragment != null)
                    prevFragment.setIsInPrintMode(false);
                if (nextFragment != null)
                    nextFragment.setIsInPrintMode(false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mPresenter.getStatusOrders("");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String key) {
                query = key;
                mPresenter.getStatusOrders(query);
                MainHelper.hideSoftKeyboard(SecondActivity.this);
                toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
                toolbar.setNavigationOnClickListener(v -> {
                    query = "";
                    search.setQuery("", false);
                    search.clearFocus();
                    pb.setVisibility(View.VISIBLE);
                    mPresenter.getStatusOrders("");
                    changeNavigationIconsSettings();
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        if (PreferenceHelper.getInstance().getString(PreferenceHelper.Key.SHOP_TYPE).equals("airport")) {
            tvDate.setText(R.string.departure_time);
        } else {
            //todo translate
            tvDate.setText("Дата прибытия");
        }


        pb.setVisibility(View.VISIBLE);

    }


    private void changeNavigationIconsSettings() {
        toolbar.setNavigationIcon(R.drawable.search);
        toolbar.setNavigationOnClickListener(view -> {
            search.setFocusable(true);
            search.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        });
    }

    private void setupViewPager(String query) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        pb.setVisibility(View.GONE);

        for (int i = 0; i < listOrders.size(); i++) {
            titles.add(listOrders.get(i).getName());
            fragments.add(OrdersListFragment.newInstance(listOrders.get(i).getStatus(), query));
        }

        adapter.setFragments(fragments, titles);
        adapter.notifyDataSetChanged();
    }

    private void setIsInPrintMode(boolean isInPrintMode) {
        this.isInPrintMode = isInPrintMode;
        adapter.getItem(viewPager.getCurrentItem()).setIsInPrintMode(isInPrintMode);
    }

    @OnClick(R.id.icon_logout)
    void onLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logoutText))
                .setNegativeButton(R.string.cancelALLcaps, (dialog, id) -> dialog.cancel())
                .setPositiveButton(R.string.exit, (dialog, which) -> {
                    PreferenceHelper.getInstance(SecondActivity.this).put(PreferenceHelper.Key.KEY_USER, "");
                    MainActivity.startActivity(SecondActivity.this);
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.icon_print)
    void onPrint() {
        setIsInPrintMode(true);
        btnFAB.setVisibility(View.VISIBLE);
        btnFABCancel.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.print_mode_turned_on, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.rlFABCancel)
    void onCancel() {
        setIsInPrintMode(false);
        btnFAB.setVisibility(View.GONE);
        btnFABCancel.setVisibility(View.GONE);
        Toast.makeText(this, R.string.print_mode_turned_off, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.rlFAB)
    void onPrintChecked() {
        adapter.getItem(viewPager.getCurrentItem()).printCheckedItems();
        btnFAB.setVisibility(View.GONE);
        btnFABCancel.setVisibility(View.GONE);
        setIsInPrintMode(false);
    }

    @Override
    public void updateListOrder(List<OrdersStatusResponse> list, String message) {
        listOrders = list;
        if (list.size() != 0) {
            setupViewPager(query);
        }
        if (!message.equals("")) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShowErrorMessage(String message) {
        pb.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void printPDF(String url) {

    }

    @Override
    public void orderListUpdate(List<Order> list) {

    }

    @Override
    public void updateStatusOrder(Order item) {

    }

    @Override
    public void refreshOrderList(List<Order> list) {

    }

    @Override
    public void onErrorConnection() {
        pb.setVisibility(View.GONE);
        Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowResultNotCorrectImage() {

    }

    @Override
    public void onBackPressed() {
        query = "";
        search.setQuery("", false);
        search.clearFocus();
        pb.setVisibility(View.VISIBLE);
        mPresenter.getStatusOrders("");
        changeNavigationIconsSettings();
    }
}