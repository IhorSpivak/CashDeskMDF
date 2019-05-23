package com.example.cashdesk.thirth_activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cashdesk.R;
import com.example.cashdesk.helper.PreferenceHelper;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.Product;
import com.example.cashdesk.second_activity.BigDialog;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rambler.libs.swipe_layout.SwipeLayout;

public class ItemInOrderAdapter extends RecyclerView.Adapter<ItemInOrderAdapter.OrderProductsViewHolder> {
    private List<Product> items;
    private Order order;
    private Context context;
    private ItemInOrderAdapter.OnItemClickListener itemClickListener;


    public ItemInOrderAdapter(Context context, Order order, List<Product> items,
                              ItemInOrderAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.order = order;
        this.items = items;

        this.itemClickListener = onItemClickListener;


    }

    public void setItemClickListener(ItemInOrderAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public void setItems(List<Product> items,Order order){
        this.items.clear();
        this.items.addAll(items);
        this.order = order;
        notifyDataSetChanged();
    }

    @Override
    public ItemInOrderAdapter.OrderProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemInOrderAdapter.OrderProductsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_in_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemInOrderAdapter.OrderProductsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderProductsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvSCU)
        TextView tvSCU;

        @BindView(R.id.tvQuantity)
        TextView tvQuantity;

        @BindView(R.id.tvPrice)
        TextView tvPrice;

        @BindView(R.id.tvInStoke)
        Button tvInStoke;

        @BindView(R.id.tvIsRefused)
        Button tvIsRefused;

        @BindView(R.id.iv_image)
        ImageView iv_image;

        @BindView(R.id.iv_chevron)
        ImageView iv_chevron;

        @BindView(R.id.rlOutStock)
        RelativeLayout rlOutStock;

        @BindView(R.id.rlDeleteItem)
        RelativeLayout rlDeleteItem;

        @BindView(R.id.root)
        SwipeLayout root;

        @BindView(R.id.root2)
        RelativeLayout root2;

        public OrderProductsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Product item) {
            Picasso.with(context).load(item.getImages().getBig()).into(iv_image);
            tvTitle.setText(item.getTitle());
            tvSCU.setText(item.getSku());
            tvQuantity.setText("x" + item.getQuantity().toString());
            tvPrice.setText(item.getPrice());
            iv_chevron.setVisibility(View.VISIBLE   );
            if(item.getIs_obtainable().equals(0)){
                root2.setAlpha(0.5f);
                root.setBackgroundColor(Color.parseColor("#0DFFA000"));
                root.setRightSwipeEnabled(false);
                tvInStoke.setVisibility(View.VISIBLE);
                tvIsRefused.setVisibility(View.GONE);
                root.animateReset();
            } else {
                root.setRightSwipeEnabled(true);
                tvInStoke.setVisibility(View.GONE);
                tvIsRefused.setVisibility(View.GONE);
                root.setBackgroundColor(Color.parseColor("#FFFFFF"));
                root2.setAlpha(1.0f);
            }

            if(item.getIs_refused().equals(1) ) {
                root2.setAlpha(0.5f);
                root.setBackgroundColor(Color.parseColor("#fff3f7"));
                root.setRightSwipeEnabled(false);
                tvInStoke.setVisibility(View.GONE);
                tvIsRefused.setVisibility(View.VISIBLE);
                root.animateReset();
            }
            if(item.getIs_refused().equals(0) && !item.getIs_obtainable().equals(0)){
                    root.setRightSwipeEnabled(true);
                    tvInStoke.setVisibility(View.GONE);
                    tvIsRefused.setVisibility(View.GONE);
                    root.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    root2.setAlpha(1.0f);
            }

            if(order.getStatus().equals("completed") || order.getStatus().equals("cancelled")){
                root.setRightSwipeEnabled(false);
                tvInStoke.setVisibility(View.GONE);
                iv_chevron.setVisibility(View.GONE);
                tvIsRefused.setVisibility(View.GONE);

            } else {
                iv_chevron.setVisibility(View.VISIBLE);
                iv_chevron.setOnClickListener(view -> itemClickListener.onItemClick(item));
                tvQuantity .setOnClickListener(view -> itemClickListener.onItemClick(item));
                rlOutStock .setOnClickListener(view -> itemClickListener.onOutStock(item));
                tvInStoke .setOnClickListener(view -> itemClickListener.onOutStock(item));
                tvIsRefused .setOnClickListener(view -> itemClickListener.onIsRefused(item));
                rlDeleteItem .setOnClickListener(view -> itemClickListener.onIsRefused(item));
            }

            if(item.getIs_obtainable().equals(0) || item.getIs_refused().equals(1)){
                iv_chevron.setVisibility(View.GONE);
            }

            iv_image.setOnLongClickListener(v -> {
                BigDialog dialog = BigDialog.newInstance(item);
                dialog.setAction(() -> itemClickListener.onNotCorrectImage());
                dialog.show(((FragmentActivity)context).getSupportFragmentManager(), null);
                return false;
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product item);
        void onDeleteItem(Product item);
        void onOutStock(Product item);
        void onIsRefused(Product item);
        void onNotCorrectImage();
    }

    public void removeItem(Product product){
        for (int i = 0; i < items.size(); i++) {
            if(Objects.equals(product.getProduct_id(), items.get(i).getProduct_id())){
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

}
