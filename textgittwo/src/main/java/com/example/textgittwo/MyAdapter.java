package com.example.textgittwo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.LinkAddress;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/4/29.
 */

public class MyAdapter extends XRecyclerView.Adapter{
    private Context context;
    private ArrayList<DataBean> list;
    private SparseBooleanArray mSparseBooleanArray=new SparseBooleanArray();

    public MyAdapter(Context context, ArrayList<DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rec_item_layout, null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder recviewholder= (ViewHolder) holder;
        DataBean dataBean = list.get(position);
        recviewholder.item_msg.setText(dataBean.getFood_str());
        recviewholder.item_price.setText("$"+dataBean.getNum());
        Glide.with(context).load(dataBean.getPic()).into(recviewholder.item_img);
        recviewholder.item_check.setTag(position);
        recviewholder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int tag = (int) buttonView.getTag();
                if (isChecked){
                    mSparseBooleanArray.put(tag,true);
                }else{
                    mSparseBooleanArray.delete(tag);
                }
            }
        });
        recviewholder.item_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.ClickItem(v,position);
                }
            }
        });
        recviewholder.item_check.setChecked(mSparseBooleanArray.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends XRecyclerView.ViewHolder{

        private ImageView item_img;
        private TextView item_msg;
        private TextView item_price;
        private CheckBox item_check;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_msg = itemView.findViewById(R.id.item_msg);
            item_price = itemView.findViewById(R.id.item_price);
            item_check = itemView.findViewById(R.id.item_check);


        }
    }
    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void ClickItem(View v,int position);
    }
}
