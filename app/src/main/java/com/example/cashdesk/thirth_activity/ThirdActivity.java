package com.example.cashdesk.thirth_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashdesk.R;
import com.example.cashdesk.helper.MainHelper;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.Comments;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.Product;
import com.example.cashdesk.models.ReesonResponse;
import com.example.cashdesk.second_activity.SecondActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener, ThirdScreenContract.View {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.tvStatusValue)
    TextView tvStatusValue;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvBarcodeValue)
    TextView tvBarcodeValue;

    @BindView(R.id.tvBarcodeValueBig)
    TextView tvBarcodeValueBig;

    @BindView(R.id.statusLine)
    View statusLine;

    @BindView(R.id.tvIdValue)
    TextView tvIdValue;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvDateValue)
    TextView tvDateValue;

    @BindView(R.id.tvDiscount)
    TextView tvDiscount;

    @BindView(R.id.tvDiscountBig)
    TextView tvDiscountBig;

    @BindView(R.id.tvTotalPriceValue)
    TextView tvTotalPriceValue;

    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;

    @BindView(R.id.tvTotalPrice1)
    TextView tvTotalPrice1;

    @BindView(R.id.ll1)
    LinearLayout ll1;

    @BindView(R.id.ll2)
    LinearLayout ll2;

    @BindView(R.id.rvComments)
    RecyclerView rvComments;

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @BindView(R.id.tvFAB)
    TextView tvFAB;

    @BindView(R.id.pbFAB)
    ProgressBar pbFAB;

    @BindView(R.id.rlFAB)
    RelativeLayout rlFAB;

    @BindView(R.id.ivFAB)
    ImageView ivFAB;

    @BindView(R.id.tvFABCancel)
    TextView tvFABCancel;

    @BindView(R.id.pbFABCancel)
    ProgressBar pbFABCancel;

    @BindView(R.id.rlFABCancel)
    RelativeLayout rlFABCancel;

    @BindView(R.id.ivFABCancel)
    ImageView ivFABCancel;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.ivZoomIn)
    ImageView ivZoomIn;

    @BindView(R.id.ivZoomOut)
    ImageView ivZoomOut;

    @BindView(R.id.imageBig)
    ImageView imageBig;

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.edMessage)
    EditText edMessage;

    @BindView(R.id.tvMessage)
    TextView tvMessage;

    @BindView(R.id.ivComments)
    ImageView ivComments;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.rlRootBarcode)
    RelativeLayout rlRootBarcode;

    @BindView(R.id.rlRootBarcodeBig)
    RelativeLayout rlRootBarcodeBig;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;

    private int xDelta;
    private int yDelta;
    private String status;
    private Boolean flagShow = false;


    public static final String EXTRA_ORDER = "extra_order";
    private Order order = null;
    private ThirdScreenContract.Presenter mPresenter;

    List<Comments> list;
    List<Product> listProducts;
    private MessageAdapter messageAdapter;
    private ItemInOrderAdapter itemInOrderAdapter;
    private GridLayoutManager mGridLayoutManager;


    public static void startActivity(Activity activity, Order item) {
        Intent intent = new Intent(activity, ThirdActivity.class);
        intent.putExtra(EXTRA_ORDER, Parcels.wrap(item));
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);

        mPresenter = new ThirdScreenPresenter(this);
        swipe_container.setColorSchemeResources(R.color.colorAccent);
        swipe_container.setOnRefreshListener(this);

        pbFAB.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        pbFABCancel.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        order = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_ORDER));
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvMessage.setOnClickListener(v -> onCreateComment());

        rlRootBarcode.setOnTouchListener(onTouchListener());
        rlRootBarcodeBig.setOnTouchListener(onTouchListener());

        tvTitle.setText(order.getCustomer_name());
        setUI(order);

        messageAdapter = new MessageAdapter(this);
        messageAdapter.addAll(order.getComments());
        rvComments.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        rvComments.setLayoutManager(mGridLayoutManager);
        rvComments.setAdapter(messageAdapter);
        ivZoomIn.setOnClickListener(v -> {
            rlRootBarcode.setVisibility(View.GONE);
            rlRootBarcodeBig.setVisibility(View.VISIBLE);
        });

        ivZoomOut.setOnClickListener(v -> {
            rlRootBarcode.setVisibility(View.VISIBLE);
            rlRootBarcodeBig.setVisibility(View.GONE);
        });


        itemInOrderAdapter = new ItemInOrderAdapter(this, order, order.getProducts(), new ItemInOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                onShowDialog(item);
            }

            @Override
            public void onDeleteItem(Product item) {
                deleteItemDialog(item);
            }

            @Override
            public void onOutStock(Product item) {
                int inStock;
                pb.setVisibility(View.VISIBLE);
                if (item.getIs_obtainable().equals(0)) {
                    inStock = 1;
                } else {
                    inStock = 0;
                }
                mPresenter.setStockStatus(item, inStock, order);
            }

            @Override
            public void onIsRefused(Product item) {
                int isRefused;
                pb.setVisibility(View.VISIBLE);
                if (item.getIs_refused().equals(0)) {
                    isRefused = 1;
                } else {
                    isRefused = 0;
                }
                mPresenter.setIsRefused(item, isRefused, order);
            }

            @Override
            public void onNotCorrectImage() {
                showDialogNotCorrectImage();
            }
        });
        rvProducts.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        rvProducts.setLayoutManager(mGridLayoutManager);
        rvProducts.setAdapter(itemInOrderAdapter);

        if (order.getStatus().equals("new")) {
            status = "collected";
        }

        if (order.getStatus().equals("collected")) {
            status = "completed";
        }


    }

    private void showDialogNotCorrectImage() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        //todo translate
        builder.setTitle("Отправить информацию о несоответствующем фото?")
                .setNegativeButton(R.string.close,
                        (dialog, id) -> dialog.cancel())
                .setPositiveButton(R.string.send,
                        (dialog, which) -> {
                            mPresenter.sendNotCorrectMessage(PreferenceHelper.getInstance().getInt(PreferenceHelper.Key.PRODUCT_ID));
                            pb.setVisibility(View.VISIBLE);
                        });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteItemDialog(Product item) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_goods_ensure)
                .setNegativeButton(R.string.cancelALLcaps,
                        (dialog, id) -> dialog.cancel())
                .setPositiveButton(R.string.delete,
                        (dialog, which) -> mPresenter.removeItem(item, order));
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void onCreateComment() {
        tvMessage.setVisibility(View.GONE);
        edMessage.setVisibility(View.VISIBLE);
        edMessage.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        edMessage.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String text = edMessage.getText().toString();
                edMessage.setText("");
                mPresenter.addMessage(order, text);
                return true;
            }
            return false;
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void onShowDialog(Product item) {
        RelativeLayout linearLayout = new RelativeLayout(this);
        final NumberPicker aNumberPicker = new NumberPicker(this);
        aNumberPicker.setMaxValue(item.getMaxQuantity());
        aNumberPicker.setMinValue(1);
        aNumberPicker.setValue(item.getQuantity());
        aNumberPicker.setWrapSelectorWheel(false);
        aNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //todo translate
        alertDialogBuilder.setTitle("Выберите доступное количесвто товаров");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.choose,
                        (dialog, id) -> mPresenter.setChangeQuantity(item, aNumberPicker.getValue(), order))
                .setNegativeButton(R.string.close,
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @OnClick(R.id.icon_print)
    void onPrint() {
        ArrayList<String> listOrder = new ArrayList<>();
        listOrder.add(order.getOrder_id().toString());
        pb.setVisibility(View.VISIBLE);
        mPresenter.printOrder(order, listOrder);
    }

    @OnClick(R.id.rlFABCancel)
    void onCancelOrder() {
        if (order.getCan_cancelled()) {
            pbFABCancel.setVisibility(View.VISIBLE);
            ivFABCancel.setVisibility(View.GONE);
            mPresenter.getCancelReasonList();
        } else {
            if (PreferenceHelper.getInstance().getString(PreferenceHelper.Key.SHOP_TYPE).equals("airport")) {
                //todo translate
                Toast.makeText(this, "Заказ не может быть отменен ранее чем за 3 часа до вылета!", Toast.LENGTH_LONG).show();
            } else {
                //todo translate
                Toast.makeText(this, "Заказ не может быть отменен ранее чем за 3 часа до прибытия!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnClick(R.id.rlFAB)
    void onChangeStatusOrder() {
        pbFAB.setVisibility(View.VISIBLE);
        ivFAB.setVisibility(View.GONE);
        mPresenter.changeOrderStatus(order, status, null, null);
    }

    private void onShowAlertDialog(List<ReesonResponse> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i).getReason());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_radio_group, null);
        RadioGroup rg = dialogView.findViewById(R.id.radio_group);
        EditText ed = dialogView.findViewById(R.id.edComments);

        for (int i = 0; i < arrayList.size(); i++) {
            RadioButton rb = new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(arrayList.get(i));
            rg.addView(rb);
            if (arrayList.get(i).equals("Another reason")) {
                rb.setOnClickListener(v -> {
                    ed.setVisibility(View.VISIBLE);
                    ed.requestFocus();
                    MainHelper.showSoftKeyboard(ThirdActivity.this, ed);
                });
            }
        }
        builder.setView(dialogView);

        builder.setPositiveButton(getText(R.string.send), (dialog, which) -> {
            pb.setVisibility(View.VISIBLE);
            rg.getCheckedRadioButtonId();
            int checked = rg.getCheckedRadioButtonId();
            String message = ed.getText().toString();
            mPresenter.changeOrderStatus(order, "cancelled", checked, message);
        });
        builder.setNegativeButton(getText(R.string.close), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setUI(Order order) {

        if (order.getStatus().equals("new")) {
            status = "collected";
        }
        if (order.getStatus().equals("collected")) {
            status = "completed";
        }
        tvIdValue.setText(order.getOrder_id().toString());
        tvDateValue.setText(order.getDeparture_date());
        tvTotalPriceValue.setText(order.getPrice());
        if (order.getDiscount() != null || order.getDiscount() != 0) {
            String s = getResources().getString(R.string.sum) + order.getDiscount() + "%)";
            tvTotalPrice.setText(s);
        }

        if (order.getTime_warning().equals("")) {
            tvDate.setText(R.string.departure_date_time);
            tvDate.setTextColor(Color.parseColor("#808080"));
        } else {
            tvDate.setText(order.getTime_warning());
            tvDate.setTextColor(Color.parseColor("#ef1865"));
        }

        if (order.getStatus().equals("new")) {
            ll1.setBackgroundResource(R.color.statusReady1);
            tvStatusValue.setTextColor(Color.parseColor("#7cb342"));
            statusLine.setBackgroundResource(R.color.statusReady);
            tvStatusValue.setText(R.string.status_new);
            rlFAB.setBackgroundResource(R.drawable.ripple_for_fab_collected);
            rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_done);
            rlRootBarcode.setVisibility(View.GONE);
            rlFAB.setVisibility(View.VISIBLE);
            tvFAB.setText(R.string.status_collected);

            Drawable image = getResources().getDrawable(R.drawable.shopping_bag);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            ivFAB.setImageDrawable(image);

            pbFAB.setVisibility(View.GONE);
            pbFABCancel.setVisibility(View.GONE);
            ivFAB.setVisibility(View.VISIBLE);
            if (!order.getCan_cancelled()) {
                rlFABCancel.setVisibility(View.VISIBLE);
                tvFABCancel.setText(R.string.cancel);
                tvFABCancel.setTextColor(Color.parseColor("#808080"));
                rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_done);
                Drawable imageCancel = getResources().getDrawable(R.drawable.x_circle_grey);
                imageCancel.setBounds(0, 0, imageCancel.getIntrinsicWidth(), imageCancel.getIntrinsicHeight());
                ivFABCancel.setImageDrawable(image);
            } else {
                rlFABCancel.setVisibility(View.VISIBLE);
                rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_cancell);
                tvFABCancel.setText(R.string.cancel);
                tvFABCancel.setTextColor(Color.parseColor("#FFFFFF"));
                Drawable image1 = getResources().getDrawable(R.drawable.x_circle_white);
                image1.setBounds(0, 0, image1.getIntrinsicWidth(), image1.getIntrinsicHeight());
                ivFABCancel.setImageDrawable(image);
            }

        }

        if (order.getStatus().equals("collected")) {
            ll1.setBackgroundResource(R.color.statusCollected1);
            tvStatusValue.setTextColor(Color.parseColor("#FFA000"));
            statusLine.setBackgroundResource(R.color.statusCollected);
            tvStatusValue.setText(R.string.status_collected);
            rlFAB.setBackgroundResource(R.drawable.ripple_for_fab_completed);
            rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_cancell);
            rlRootBarcode.setVisibility(View.GONE);
            rlFAB.setVisibility(View.VISIBLE);
            tvFAB.setText(R.string.status_done);

            Drawable image = getResources().getDrawable(R.drawable.check);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            ivFAB.setImageDrawable(image);
            if (!order.getCan_cancelled()) {
                rlFABCancel.setVisibility(View.VISIBLE);
                tvFABCancel.setText(R.string.cancel);
                tvFABCancel.setTextColor(Color.parseColor("#808080"));
                rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_done);
                Drawable imageCancel = getResources().getDrawable(R.drawable.x_circle_grey);
                imageCancel.setBounds(0, 0, imageCancel.getIntrinsicWidth(), imageCancel.getIntrinsicHeight());
                ivFABCancel.setImageDrawable(image);
            } else {
                rlFABCancel.setVisibility(View.VISIBLE);
                rlFABCancel.setBackgroundResource(R.drawable.ripple_for_fab_cancell);
                tvFABCancel.setText(R.string.cancel);
                tvFABCancel.setTextColor(Color.parseColor("#FFFFFF"));
                Drawable image1 = getResources().getDrawable(R.drawable.x_circle_white);
                image1.setBounds(0, 0, image1.getIntrinsicWidth(), image1.getIntrinsicHeight());
                ivFABCancel.setImageDrawable(image1);
            }
            pbFAB.setVisibility(View.GONE);
            ivFAB.setVisibility(View.VISIBLE);
            pbFABCancel.setVisibility(View.GONE);
            ivFABCancel.setVisibility(View.VISIBLE);

        }

        if (order.getStatus().equals("cancelled")) {
            ll1.setBackgroundResource(R.color.statusCancelled);
            tvStatusValue.setTextColor(Color.parseColor("#de000000"));
            statusLine.setBackgroundResource(R.color.black);
            tvStatusValue.setText(R.string.status_cancel);
            rlRootBarcode.setVisibility(View.GONE);
            rlFAB.setVisibility(View.GONE);
            rlFABCancel.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
        }

        if (order.getStatus().equals("completed")) {
            rlFABCancel.setVisibility(View.GONE);
            if (!order.getBarcode().equals("")) {
                rlRootBarcode.setVisibility(View.VISIBLE);
                String text = order.getBarcode();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.EAN_13, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                    image.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            } else {
                image.setVisibility(View.GONE);
                rlRootBarcode.setVisibility(View.GONE);
            }
            ll1.setBackgroundResource(R.color.statusDone);
            tvStatusValue.setTextColor(Color.parseColor("#5699ff"));
            statusLine.setBackgroundResource(R.color.statusDOneTextColor);
            tvStatusValue.setText(R.string.status_done);
            rlFAB.setVisibility(View.GONE);
            tvDiscount.setText(order.getDiscount_title());
            tvBarcodeValue.setText(order.getBarcode());
            pbFAB.setVisibility(View.GONE);
            ivFAB.setVisibility(View.VISIBLE);
            pbFABCancel.setVisibility(View.GONE);
            ivFABCancel.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onRemoveItem(Product item) {
        pb.setVisibility(View.GONE);
        itemInOrderAdapter.removeItem(item);

        //todo translate
        Toast.makeText(this, R.string.goods_deleted, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSetStockStatus(Order order) {
        pb.setVisibility(View.GONE);
        itemInOrderAdapter.setItems(order.getProducts(), order);
        setUI(order);
        Toast.makeText(this, getText(R.string.changing_saved), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSetRefusedStatus(Order order) {
        pb.setVisibility(View.GONE);
        itemInOrderAdapter.setItems(order.getProducts(), order);
        setUI(order);
        Toast.makeText(this, getText(R.string.changing_saved), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPrintOrder(String url) {
        pb.setVisibility(View.GONE);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

    @Override
    public void onAddMessage(List<Comments> list) {
        messageAdapter.setItems(list);
        tvMessage.setVisibility(View.VISIBLE);
        edMessage.setVisibility(View.GONE);
        MainHelper.hideSoftKeyboard(ThirdActivity.this);
        Toast.makeText(this, R.string.comment_added, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangeQuantity(Order order) {
        pb.setVisibility(View.GONE);
        itemInOrderAdapter.setItems(order.getProducts(), order);
        setUI(order);
        Toast.makeText(this, getText(R.string.changing_saved), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onShowReasonList(List<ReesonResponse> list) {
        pb.setVisibility(View.GONE);
        onShowAlertDialog(list);
    }

    @Override
    public void onChangeOrderStatus(Order order) {
        itemInOrderAdapter.setItems(order.getProducts(), order);
        setUI(order);
        Toast.makeText(this, getText(R.string.order_status_changed_successfully), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRessetOrder(Order order) {
        itemInOrderAdapter.setItems(order.getProducts(), order);
        messageAdapter.setItems(order.getComments());
        setUI(order);
        Toast.makeText(this, getText(R.string.updated), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorConnection() {
        pb.setVisibility(View.GONE);
        Toast.makeText(this, getText(R.string.no_connection), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowResultNotCorrectImage() {
        //todo translate
        Toast.makeText(this, "Уведомление отправлено!", Toast.LENGTH_LONG).show();
        pb.setVisibility(View.GONE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener onTouchListener() {
        return (view, event) -> {

            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                            view.getLayoutParams();

                    xDelta = x - lParams.leftMargin;
                    yDelta = y - lParams.topMargin;
                    break;

                case MotionEvent.ACTION_UP:
                    break;

                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = x - xDelta;
                    layoutParams.topMargin = y - yDelta;
                    layoutParams.rightMargin = 0;
                    layoutParams.bottomMargin = 0;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            root.invalidate();
            return true;
        };
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            swipe_container.setRefreshing(false);
            mPresenter.getOrder(order.getOrder_id());
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SecondActivity.startActivity(this, PreferenceHelper.getInstance().getString(PreferenceHelper.Key.AUTH_KEY));
    }
}
