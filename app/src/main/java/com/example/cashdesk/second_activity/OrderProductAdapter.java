package com.example.cashdesk.second_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cashdesk.R;
import com.example.cashdesk.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductsViewHolder> {
    private List<Product> items;
    private Context context;
    private OnSmallImageLongClickListener onSmallImageLongClickListener;



    public OrderProductAdapter(Context context, List<Product> items,OnSmallImageLongClickListener onSmallImageLongClickListener){
        this.items = items;
        this.context = context;
        this.onSmallImageLongClickListener = onSmallImageLongClickListener;

    }

    @Override
    public OrderProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderProductsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(OrderProductsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderProductsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;

        @BindView(R.id.badge)
        TextView badge;

        public OrderProductsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Product product){
            Picasso.with(context).load(product.getImages().getSmall()).into(ivImage);
            ivImage.setOnLongClickListener(v -> {
                onSmallImageLongClickListener.onClicked(product);
                return false;
            });

            if(product.getQuantity() > 1){
                badge.setVisibility(View.VISIBLE);
                badge.setText(product.getQuantity().toString() );
            }else {
                badge.setVisibility(View.GONE);
            }


        }

    }

    public interface OnSmallImageLongClickListener{
        void onClicked(Product product);
    }
}

