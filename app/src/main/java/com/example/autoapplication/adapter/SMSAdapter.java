package com.example.autoapplication.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.autoapplication.OnItemClickLisenter;
import com.example.autoapplication.R;
import com.example.autoapplication.entity.SmsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author WeiWen Fang
 * on 2019/6/5.
 */
public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private List<SmsEntity> items;

    private List<SmsEntity> getItems() {
        return items;
    }

    public void addItem(SmsEntity entity) {
        items.add(0, entity);
        notifyDataSetChanged();
    }

    public void setNewData(List<? extends SmsEntity> data) {
        if (data != null) {
            items = new ArrayList<>(data);
        } else {
            items = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void getItem(int position) {
        items.get(position);
    }

    private void makeSureDataNotNull() {
        if (items == null) {
            items = new ArrayList<>();
        }
    }

    public void delete(int pos) {
        this.items.remove(pos);
        this.notifyItemRemoved(pos);

        this.notifyDataSetChanged();
    }

    public void remove(SmsEntity item) {
        if (item != null) {
            this.items.remove(item);
            notifyDataSetChanged();
        }
    }
/*
    public SMSAdapter(List< SmsEntity> data) {
        this.items = data;
    }*/

    public SMSAdapter() {
    }

    @NonNull
    @Override
    public SMSViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SMSViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sms_list_item_layout, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SMSViewHolder smsViewHolder, int i) {
        final SmsEntity entity = items.get(i);
        TextView addressTextView = smsViewHolder.findTextView(R.id.address_text);
        addressTextView.setText("发件人 : " + entity.getStrAddress());

        TextView dateTextView = smsViewHolder.findTextView(R.id.date_text);
        dateTextView.setText("时间 : " + entity.getStrDate());

        TextView typeTextView = smsViewHolder.findTextView(R.id.type_text);
        typeTextView.setText("短信类型 : " + entity.getStrType());

        TextView contentTextView = smsViewHolder.findTextView(R.id.content_text);
        contentTextView.setText("短信内容 : \n  " + entity.getStrbody());

        smsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lisenter != null) {
                    lisenter.onItemClickLisenter(entity);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class SMSViewHolder extends RecyclerView.ViewHolder {
        //所有控件的集合
        private SparseArray<View> views;

        public SMSViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        public <T extends View> T findTextView(int ResId) {
            View view = views.get(ResId);
            if (view == null) {
                view = itemView.findViewById(ResId);
                views.put(ResId, view);
            }
            return (T) view;
        }
    }

    private OnItemClickLisenter lisenter;

    public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
        this.lisenter = lisenter;
    }
}
