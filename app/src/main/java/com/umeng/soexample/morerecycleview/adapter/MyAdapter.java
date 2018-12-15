package com.umeng.soexample.morerecycleview.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.morerecycleview.R;
import com.umeng.soexample.morerecycleview.bean.User;

import java.util.ArrayList;

/**
 * 文件描述：
 * 作者：鲁华丰
 * 创建时间：2018/12/15
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<User.DataBean> mList;

    private final int ONE_ITEM = 1;
    private final int TWO_ITEM = 2;

    public MyAdapter(Context context , ArrayList<User.DataBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewGroup) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewGroup) {
            case ONE_ITEM:
                view = View.inflate(mContext, R.layout.item_news, null);
                holder = new OneHolder(view);
                break;
            case TWO_ITEM:
                view = View.inflate(mContext, R.layout.item_news2, null);
                holder = new TwoHolder(view);
                break;
        }
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //判断前一个参数是否是后一个参数的一个实例
        if (holder instanceof OneHolder) {
            ((OneHolder) holder).txtTitle.setText(mList.get(position).getTitle());
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s()).into(((OneHolder) holder).imglogo);
        } else {
            ((TwoHolder) holder).txtTitle.setText(mList.get(position).getTitle());
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s()).into(((TwoHolder) holder).mImage);
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s02()).into(((TwoHolder) holder).mImage1);
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s03()).into(((TwoHolder) holder).mImage2);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 1) {
            return ONE_ITEM;
        } else {
            return TWO_ITEM;
        }
    }


    class OneHolder extends RecyclerView.ViewHolder {
        private ImageView imglogo;
        private TextView txtTitle;
        public OneHolder(View itemView) {
            super(itemView);
            imglogo = itemView.findViewById(R.id.img_logo);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }

    class TwoHolder extends RecyclerView.ViewHolder {
        private ImageView mImage,mImage1,mImage2;
        private TextView txtTitle;

        public TwoHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img_logo);
            mImage1 = itemView.findViewById(R.id.img_logo1);
            mImage2= itemView.findViewById(R.id.img_logo2);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }
    public interface ItemClick {
        void setOnItem(View v, int position);
    }

    private ItemClick itemClick;

    public void setOnItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View v) {
        if (itemClick != null) {
            itemClick.setOnItem(v, (int) v.getTag());
        }
    }
}
