package com.example.seecloud;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seecloud.database.Attribute;
import com.example.seecloud.database.CloudServer;
import com.example.seecloud.database.InitCloudServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private ArrayList<CloudServer> cloudServers = new ArrayList<>();

    private InitCloudServer initCloudServer = new InitCloudServer();

    private Context mContext;
    private List<Attribute> mAttriList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView AttriImage;
        TextView AttriName;
        TextView AttriInstr;
        public ViewHolder(View view) {
            super(view);
            cardView=(CardView)view;
            AttriImage = (ImageView) view.findViewById(R.id.attr_image);
            AttriName = (TextView) view.findViewById(R.id.attr_rank_name);
            AttriInstr = (TextView) view.findViewById(R.id.attr_intro);
        }
    }

    public AttributeAdapter(List<Attribute> AttriList) {
        this.mAttriList = AttriList;
    }

    @Override
    public AttributeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.rank_item, parent, false);
        //数据初始化
        cloudServers.addAll(initCloudServer.getClouds());

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //define interface
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Attribute attribute = mAttriList.get(position);
        holder.AttriName.setText(attribute.getName());
        Glide.with(mContext).load(attribute.getImageId()).into(holder.AttriImage);

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //holder.AttriInstr.setText(position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAttriList.size();
    }


}
