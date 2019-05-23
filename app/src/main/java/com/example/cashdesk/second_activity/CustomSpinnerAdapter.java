package com.example.cashdesk.second_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cashdesk.models.Order;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private int hidingItemIndex;
    private Order item;

    public CustomSpinnerAdapter(Context context, Order item, int textViewResourceId, String[] objects, int hidingItemIndex) {
        super(context, textViewResourceId, objects);
        this.hidingItemIndex = hidingItemIndex;
        this.item = item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if (position == hidingItemIndex) {
            TextView tv = new TextView(getContext());
            tv.setVisibility(View.GONE);
            tv.setHeight(0);
            v = tv;

        } else {
            v = super.getDropDownView(position, null, parent);
        }
        if(item.getStatus().equals("collected") && position == 1){
            TextView tv = new TextView(getContext());
            tv.setVisibility(View.GONE);
            tv.setHeight(0);
            v = tv;
        }
        return v;
    }
}
