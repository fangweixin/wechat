package com.team2.wechat.comments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team2.wechat.R;

import java.util.ArrayList;

/**
 * Created by FEI on 2017/12/30.
 * RecyclerView适配器
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MomentItem> momentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton momentTopMe;
        ImageView momentTopImage;
        ImageButton momentItemAvatar;
        TextView momentItemUserName;
        TextView momentItemText;
        ImageView momentItemImage;
        TextView momentTopName;
        RelativeLayout momentTopItem;
        RelativeLayout momentItemHolder;
        ViewHolder(View view){
            super(view);
            momentItemAvatar = view.findViewById(R.id.Moments_item_avatar);
            momentItemImage = view.findViewById(R.id.Moments_item_image);
            momentItemText = view.findViewById(R.id.Moments_item_text);
            momentItemUserName = view.findViewById(R.id.Moments_item_user_name);
            momentTopMe = view.findViewById(R.id.Moments_item_top_me);
            momentTopImage = view.findViewById(R.id.Moments_item_top_image);
            momentTopName = view.findViewById(R.id.Moments_item_top_name);
            momentTopItem = view.findViewById(R.id.Moments_top_item);
            momentItemHolder = view.findViewById(R.id.Moments_item_holder);
        }
    }
    public MyAdapter(ArrayList<MomentItem> momentItems){
        this.momentList = momentItems;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moments_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MomentItem item = momentList.get(position);
        if(position == 0) {
            holder.momentItemHolder.setVisibility(View.GONE);
            holder.momentTopItem.setVisibility(View.VISIBLE);
            holder.momentTopImage.setImageBitmap(item.getMomentTopImage());
            holder.momentTopMe.setImageBitmap(item.getMomentTopMe());
            holder.momentItemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.momentTopName.setText(item.getMomentTopName());
        }
        else{
            holder.momentItemHolder.setVisibility(View.VISIBLE);
            holder.momentTopItem.setVisibility(View.GONE);
            holder.momentItemUserName.setText(item.getUserName());
            holder.momentItemAvatar.setImageBitmap(item.getUserAvatar());
            holder.momentItemText.setText(item.getMomentText());
            holder.momentItemImage.setImageBitmap(item.getMomentImage());
        }

    }

    @Override
    public int getItemCount() {
        return momentList.size();
    }
}
