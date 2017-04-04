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

import java.util.List;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.ViewHolder> {

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attribute attribute = mAttriList.get(position);
        holder.AttriName.setText(attribute.getName());
        Glide.with(mContext).load(attribute.getImageId()).into(holder.AttriImage);
    }

    @Override
    public int getItemCount() {
        return mAttriList.size();
    }
}
