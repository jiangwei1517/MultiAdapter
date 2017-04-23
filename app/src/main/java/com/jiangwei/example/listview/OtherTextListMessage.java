package com.jiangwei.example.listview;

import com.jiangwei.adapter.lib.baseitem.BaseItem;

/**
 * author: jiangwei18 on 17/4/1 15:47 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class OtherTextListMessage extends BaseItem<String> {
    private String mOther;

    public OtherTextListMessage(int type) {
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
