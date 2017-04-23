package com.jiangwei.adapter.lib.baseitem;

/**
 * author: jiangwei18 on 17/4/2 19:10 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public abstract class BaseItem<T> {
    private int mType;
    private static final int ERROR_NUM = 0;

    public BaseItem(int type) {
        if (type == ERROR_NUM) {
            throw new IllegalStateException("type can't be 0, 0 means unRecognized");
        }
        mType = type;
    }

    // 标识消息的唯一id hash
    public int getId() {
        return this.hashCode();
    }

    // 每种消息都对应一种type
    public int getCreatorType() {
        return mType;
    }

    public abstract void setContent(T t);

    public abstract T getContent();

}
