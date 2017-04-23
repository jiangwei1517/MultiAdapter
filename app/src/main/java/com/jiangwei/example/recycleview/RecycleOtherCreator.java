package com.jiangwei.example.recycleview;

import com.jiangwei.adapter.lib.baseitem.BaseItem;
import com.jiangwei.adapter.lib.recycle.creator.RecycleCreatorAdapter;
import com.jiangwei.adapter.lib.recycle.creator.RecycleViewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import con.jiangwei.example.R;

/**
 * author: jiangwei18 on 17/4/1 20:59 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class RecycleOtherCreator
        extends RecycleCreatorAdapter.ItemCreator<BaseItem<String>, RecycleOtherCreator.OtherRecycleViewHolder> {

    public RecycleOtherCreator(int type) {
        super(type);
    }

    @Override
    public OtherRecycleViewHolder creatViewHolder(ViewGroup parent) {
        View view = View.inflate(getContext(), R.layout.other_text_view, null);
        return new OtherRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherRecycleViewHolder holder, int position, BaseItem item) {
        if (item instanceof OtherTextMessage) {
            OtherTextMessage otherItem = (OtherTextMessage) item;
            holder.tv.setText(otherItem.getContent());
        }
    }

    public static class OtherRecycleViewHolder extends RecycleViewHolder {
        TextView tv;

        public OtherRecycleViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }

}
