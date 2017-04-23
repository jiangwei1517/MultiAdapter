package com.jiangwei.adapter.lib.list.creator;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiangwei.adapter.lib.baseitem.BaseItem;

/**
 * author: jiangwei18
 */
public class ListCreatorAdapter<T extends BaseItem> extends BaseAdapter {
    private ArrayList<T> lists = new ArrayList<>();
    private ItemCreators<T> mCreators;

    public Context mContext;

    // // adapter消息类型
    // public interface com.jiangwei.adapter.lib.baseitem.BaseItem {
    // // 标识消息的唯一id hash
    // int getId();
    //
    // // 每种消息都对应一种type
    // int getCreatorType();
    // }

    public ListCreatorAdapter(@NonNull Context context, @NonNull ItemCreators creators) {
        mContext = context;
        mCreators = creators;
        for (int i = 0; i < mCreators.array.size(); i++) {
            int key = mCreators.array.keyAt(i);
            ItemCreator itemCreator = mCreators.array.get(key);
            if (itemCreator.mAdapter != null && itemCreator.mAdapter != this) {
                throw new IllegalStateException("Creators can only be bound with one adapter!!!");
            } else {
                itemCreator.mAdapter = this;
            }
        }
    }

    public void add(@NonNull T item) {
        lists.add(item);
    }

    public Context getContext() {
        return mContext;
    }

    public void addAll(@NonNull ArrayList<T> lists) {
        this.lists.addAll(lists);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public T getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mCreators.array.size();
    }

    // 根据message的id查找对应item
    public T getItemById(long id) {
        for (T item : lists) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        return item == null ? -1 : mCreators.indexOf(item.getCreatorType());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        if (item == null) {
            StringBuilder builder = new StringBuilder();
            for (T m : lists) {
                builder.append(m).append('\n');
            }
            Log.d("ListCreatorAdapter", "position:" + position + "size:" + getCount() + "data:" + builder.toString());
        }
        ItemCreator itemCreator = mCreators.getCreatorByType(item.getCreatorType());
        if (itemCreator == null) {
            itemCreator = mCreators.getUnrecognizedTypeCreator();
        }
        return itemCreator.creatView(position, convertView, parent);
    }

    public abstract static class ItemCreator<T extends BaseItem> {
        private ListCreatorAdapter<T> mAdapter;
        public int mType;

        public ItemCreator(int type) {
            mType = type;
        }

        protected ListCreatorAdapter<T> getAdapter() {
            return mAdapter;
        }

        protected T getItem(int position) {
            if (mAdapter == null) {
                throw new IllegalStateException("register must before new a adapter instance");
            }
            return mAdapter.getItem(position);
        }

        public abstract View creatView(int position, View convertView, ViewGroup parent);

        public Context getContext() {
            if (mAdapter == null) {
                throw new IllegalStateException("register must before new a adapter instance");
            }
            return mAdapter.getContext();
        }

        public View forException(int position, View convertView, String message) {
            View view = convertView;
            Context context = getContext();

            if (view == null) {
                view = new TextView(context);
            }
            T item = getItem(position);
            message = message == null ? "" : message + "\n";
            if (item == null) {
                message += "Item is null";
            }
            ((TextView) view)
                    .setText(String.format("Position %d: %s\n", position, message == null ? item.toString() : message));
            return view;
        }
    }

    public static class ItemCreators<T extends BaseItem> {
        private SparseArray<ItemCreator<T>> array = new SparseArray<>();

        public ItemCreators() {
            register(new UnrecognizedTypeCreator<T>());
        }

        public void register(ItemCreator itemCreatot) {
            if (array.get(itemCreatot.mType) == null) {
                array.put(itemCreatot.mType, itemCreatot);
            } else {
                throw new IllegalStateException("don't register type:" + itemCreatot.mType + "  again");
            }
        }

        public ItemCreator<T> getCreatorByType(int type) {
            return array.get(type);
        }

        public ItemCreator<T> getUnrecognizedTypeCreator() {
            return array.get(UnrecognizedTypeCreator.UNRECOGNIZED_TYPE);
        }

        public int indexOf(int key) {
            int index = array.indexOfKey(key);
            if (index < 0) {
                index = array.indexOfKey(UnrecognizedTypeCreator.UNRECOGNIZED_TYPE);
            }
            return index;
        }
    }

    private static class UnrecognizedTypeCreator<T extends BaseItem> extends ItemCreator<T> {

        public static final int UNRECOGNIZED_TYPE = 0;

        public UnrecognizedTypeCreator() {
            super(UNRECOGNIZED_TYPE);
        }

        @Override
        public View creatView(int position, View convertView, ViewGroup parent) {
            return forException(position, convertView,
                    String.format("Type %d cannot be recognized", getItem(position).getCreatorType()));
        }
    }
}
