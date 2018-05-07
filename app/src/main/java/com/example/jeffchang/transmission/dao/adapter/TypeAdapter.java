package com.example.jeffchang.transmission.dao.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeffchang.transmission.R;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private  Context mContext;
    static private List<String> typeList;
    private RecyItemOnClick recyItemOnClick;
    private LayoutInflater layoutInflater;

    public void setRecyItemOnClick(RecyItemOnClick recyItemOnClick) {
        this.recyItemOnClick = recyItemOnClick;
    }

    public TypeAdapter(List<String> typeList, Context context) {
        super();
        this.typeList = typeList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 自定义点击接口
     */
    public interface RecyItemOnClick {
        //item点击
        void onItemOnClick(View view, int index);
    }


    /*
      重写viewholder
     */
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView tv_typeName;
        RecyItemOnClick recyItemOnClick;

        public ViewHolder(View view, RecyItemOnClick recyitemonclick) {
            super(view);
            cardView = (CardView) view;
            tv_typeName =  view.findViewById(R.id.tv_type);
            this.recyItemOnClick = recyitemonclick;
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (recyItemOnClick != null) {
                //这里ViewHolder中提供了getPosition()
                int position = getAdapterPosition();
                recyItemOnClick.onItemOnClick(v, position);
            }
        }
    }

    /*
      为ViewHolder设置布局元素
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View parentView = layoutInflater.inflate(R.layout.type_item, parent, false);
        return new ViewHolder(parentView, recyItemOnClick);
    }

    /*
      为ViewHolder设置元素
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_typeName.setText(typeList.get(position));
        holder.cardView.setBackgroundResource(R.drawable.border1);
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}
