package com.example.cashdesk.second_activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cashdesk.R;
import com.example.cashdesk.models.Product;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


public class BigDialog extends DialogFragment {

    private Product product;
    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvSKu;
    private TextView tvQuantity;
    private Button btnNotCorrectImage;
    private Action action;

    public static BigDialog newInstance(Product product) {
        Bundle args = new Bundle();
        args.putParcelable("SUPER-TAG", Parcels.wrap(product));
        BigDialog fragment = new BigDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_big, container);
        imageView = layout.findViewById(R.id.ivBig);
        tvTitle = layout.findViewById(R.id.tvTitle);
        tvSKu = layout.findViewById(R.id.tvSKu);
        tvQuantity = layout.findViewById(R.id.tvQuantity);
        btnNotCorrectImage = layout.findViewById(R.id.btnNotCorrectImage);

        layout.setOnClickListener(v -> getDialog().cancel());
        btnNotCorrectImage.setOnClickListener(v -> {
            if (action != null)
                action.exec();
        });

        product = Parcels.unwrap(getArguments().getParcelable("SUPER-TAG"));

        setUI();

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BidDialog);
    }

    private void setUI() {
        Picasso.with(getContext()).load(product.getImages().getBig()).into(imageView);
        tvTitle.setText(product.getTitle());
        tvSKu.setText(product.getSku());
        tvQuantity.setText(String.format("x%s", product.getQuantity().toString()));
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public interface Action {
        void exec();
    }
}