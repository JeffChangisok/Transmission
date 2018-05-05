package com.example.jeffchang.transmission.dao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffchang.transmission.R;
import com.example.jeffchang.transmission.dao.MyMenuItem;

import java.util.List;

public class MenuItemAdapter extends ArrayAdapter<MyMenuItem> {
    private int resourceId;

    public MenuItemAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<MyMenuItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyMenuItem menuItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView imageView = view.findViewById(R.id.img_item);
        TextView textView = view.findViewById(R.id.tv_item);
        imageView.setImageResource(menuItem.getImageId());
        textView.setText(menuItem.getItemText());
        return view;
    }
}
