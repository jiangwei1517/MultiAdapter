package com.jiangwei.example.recycleview;

import com.jiangwei.adapter.lib.baseitem.BaseItem;

/**
 * author: jiangwei18 on 17/4/1 15:47 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class OtherTextMessage extends BaseItem<String> {
    private String mOther;

    public OtherTextMessage(int type) {
        super(type);
    }

    @Override
    public void setContent(String s) {
        mOther = s;
    }

    @Override
    public String getContent() {
        return mOther;
    }
}
