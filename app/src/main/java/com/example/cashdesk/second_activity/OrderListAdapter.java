package com.example.cashdesk.second_activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cashdesk.R;
import com.example.cashdesk.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.RecyclerViewHolder> {
    private ArrayList<Order> listItem = new ArrayList<>();
    private Context context;
    private OnItemClickListener itemClickListener;
    private OrderProductAdapter.OnSmallImageLongClickListener onSmallImageLongClickListener;
    private boolean isInPrintMode = false;

    public void setItems(List<Order> items) {
        this.listItem.clear();
        this.listItem.addAll(items);
        notifyDataSetChanged();
    }

    public void setIsInPrintMode(boolean isInPrintMode){
        this.isInPrintMode = isInPrintMode;
        for (int i = 0; i < listItem.size(); i++) {
            listItem.get(i).setChecked(false);
        }
    }

    public ArrayList<String> getCheckedItems(){
        ArrayList<String> checkedIds = new ArrayList<>();
        for (int i = 0; i < listItem.size(); i++) {
            if(listItem.get(i).isChecked()){
                checkedIds.add(listItem.get(i).getOrder_id().toString());
            }
        }
        return checkedIds;
    }

    public OrderListAdapter(Context context, OnItemClickListener onItemClickListener, OrderProductAdapter.OnSmallImageLongClickListener onSmallImageLongClickListener) {
        this.context = context.getApplicationContext();
        this.itemClickListener = onItemClickListener;
        this.onSmallImageLongClickListener = onSmallImageLongClickListener;

    }


    public void addAll(List<Order> fakeItem) {
        this.listItem.addAll(fakeItem);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.RecyclerViewHolder holder, int position) {
        holder.bind(listItem.get(position));

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private int timeSHowCB = 100;

        private TextView tvStatus;
        private ImageView ivMore;
        private TextView tvId;
        private TextView tvName;
        private TextView tvComments;
        private TextView tvDepartureDate;
        private TextView tvTotal;
        private RecyclerView rv;
        private LinearLayout root;
        private View statusLayout;
        private int lastIndex;
        private CheckBox checkBox;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDepartureDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvComments = (TextView) itemView.findViewById(R.id.tvComments);
            ivMore = (ImageView) itemView.findViewById(R.id.ivMore);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            root = (LinearLayout) itemView.findViewById(R.id.llroot);
            statusLayout = (View) itemView.findViewById(R.id.viewStatus);
            checkBox = itemView.findViewById(R.id.cb);
        }

        public void bind(Order item) {

            if(isInPrintMode){
                        checkBox.setVisibility(View.VISIBLE);
                        ivMore.setVisibility(View.GONE);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.isChecked()){
                            if (item.getStatus().equals("collected")) {
                                if (item.getIs_today()) {
                                    root.setBackgroundResource(R.color.statusCollected1);
                                } else {
                                    root.setBackgroundResource(R.color.colorPrimary);

                                }
                            }

                            if (item.getStatus().equals("new")) {
                                if (item.getIs_today()) {
                                    root.setBackgroundResource(R.color.colorAccentBackground1);
                                } else {
                                    root.setBackgroundResource(R.color.colorPrimary);

                                }
                            }
                            item.setChecked(false);
                            checkBox.setChecked(item.isChecked());
                        } else {
                            item.setChecked(true);
                            checkBox.setChecked(item.isChecked());
                            root.setBackgroundResource(R.color.lightcolorAccent);
                        }
                    }
                });
            }else {
                checkBox.setVisibility(View.GONE);
                ivMore.setVisibility(View.VISIBLE);
                root.setOnClickListener(view -> itemClickListener.onItemClick(item));
            }


            tvId.setText(item.getOrder_id().toString());
            tvName.setText(item.getCustomer_name());
            tvTotal.setText(item.getPrice());
            tvDepartureDate.setText(item.getDeparture_date());
            root.setBackgroundResource(R.color.colorPrimary);
            if (!item.getComments().isEmpty()) {
                lastIndex = item.getComments().size();
                tvComments.setText(item.getComments().get(lastIndex - 1).getMessage());
            } else {
                tvComments.setText("");
            }

            if (item.getStatus().equals("collected")) {
                tvStatus.setText(R.string.status_collected);
                tvStatus.setTextColor(Color.parseColor("#FFA000"));
                statusLayout.setBackgroundResource(R.color.statusCollected);
                if (item.getIs_today()) {
                    root.setBackgroundResource(R.color.statusCollected1);
                }
            }

            if (item.getStatus().equals("new")) {
                tvStatus.setText(R.string.status_new);
                tvStatus.setTextColor(Color.parseColor("#7cb342"));
                statusLayout.setBackgroundResource(R.color.statusReady);
                if (item.getIs_today()) {
                    root.setBackgroundResource(R.color.colorAccentBackground1);
                }
            }


            if (item.getStatus().equals("completed")) {
                tvStatus.setText(R.string.status_done);
                statusLayout.setBackgroundResource(R.color.statusDOneTextColor);
                tvStatus.setTextColor(Color.parseColor("#5699ff"));

            }

            if (item.getStatus().equals("cancelled")) {
                tvStatus.setText(R.string.status_cancel);
                statusLayout.setBackgroundResource(R.color.black);
                tvStatus.setTextColor(Color.parseColor("#de000000"));
            }


            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onSpinnerItemClick(item, ivMore);
                }
            });

            checkBox.setOnCheckedChangeListener(null);

            checkBox.setChecked(item.isChecked());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    item.setChecked(isChecked);
                    if(item.isChecked()){
                        root.setBackgroundResource(R.color.lightcolorAccent);
                    } else {
                        if (item.getStatus().equals("collected")) {
                            if (item.getIs_today()) {
                                root.setBackgroundResource(R.color.statusCollected1);
                            } else {
                                root.setBackgroundResource(R.color.colorPrimary);

                            }
                        }
                        if (item.getStatus().equals("new")) {
                            if (item.getIs_today()) {
                                root.setBackgroundResource(R.color.colorAccentBackground1);
                            } else {
                                root.setBackgroundResource(R.color.colorPrimary);
                            }
                        }
                    }
                }
            });

            OrderProductAdapter adapter = new OrderProductAdapter(context, item.getProducts(), onSmallImageLongClickListener);
            rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rv.setAdapter(adapter);

        }



    }

    public interface OnItemClickListener {
        void onItemClick(Order item);

        void onSpinnerItemClick(Order item, View view);
    }


}
