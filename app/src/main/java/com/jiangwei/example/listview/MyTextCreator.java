package com.jiangwei.example.listview;

import com.jiangwei.adapter.lib.baseitem.BaseItem;
import com.jiangwei.adapter.lib.list.creator.ListCreatorAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import con.jiangwei.example.R;

/**
 * author: jiangwei18 on 16/8/23 10:38 email: jiangwei18@baidu.com Hi: jwill金牛
 */
public class MyTextCreator extends ListCreatorAdapter.ItemCreator {
    public MyTextCreator(int type) {
        super(type);
    }

    @Override
    public View creatView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(getContext(), R.layout.my_text_view, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        BaseItem item = getItem(position);
        if (item instanceof MyTextListMessage) {
            MyTextListMessage msg = (MyTextListMessage) getItem(position);
            tv.setText(msg.getContent());
        }
        return convertView;
    }
}
