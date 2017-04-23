package com.jiangwei.example.recycleview;

import com.jiangwei.adapter.lib.baseitem.BaseItem;

/**
 * author: jiangwei18 on 17/4/1 15:47 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class MyTextMessage extends BaseItem<String> {
    private String mMy;

    public MyTextMessage(int type) {
        super(type);
    }

    @Override
    public void setContent(String s) {
        mMy = s;
    }

    @Override
    public String getContent() {
        return mMy;
    }
}
