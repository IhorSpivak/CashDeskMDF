package com.example.cashdesk.thirth_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cashdesk.R;
import com.example.cashdesk.models.Comments;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.OrderProductsViewHolder> {
    private List<Comments> items = new ArrayList<>();
    private Context context;

    public MessageAdapter(Context context){
        this.context = context;
    }

    public void setItems(List<Comments> items){
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addAll(List<Comments> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public MessageAdapter.OrderProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageAdapter.OrderProductsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message,parent,false));
    }

    @Override
    public void onBindViewHolder(MessageAdapter.OrderProductsViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class OrderProductsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.message)
        TextView message;

        @BindView(R.id.user)
        TextView user;

        public OrderProductsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Comments item){
            message.setText(item.getMessage());
            user.setText(item.getAuthor() + ", " + item.getCreated_at());
        }
    }
    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }
}
