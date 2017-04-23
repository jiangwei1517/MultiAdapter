package com.jiangwei.adapter.lib.recycle.creator;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiangwei.adapter.lib.baseitem.BaseItem;

/**
 * author: jiangwei18 on 17/4/1 16:16 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class RecycleCreatorAdapter<T extends BaseItem, E extends RecycleViewHolder> extends RecyclerView.Adapter<E> {

    public Context mContext;
    private ArrayList<T> lists = new ArrayList<>();
    private RecycleCreatorAdapter.ItemCreators<T, E> mCreators;

    public RecycleCreatorAdapter(@NonNull Context context, @NonNull RecycleCreatorAdapter.ItemCreators creators) {
        mContext = context;
        mCreators = creators;
        for (int i = 0; i < mCreators.array.size(); i++) {
            int key = mCreators.array.keyAt(i);
            RecycleCreatorAdapter.ItemCreator itemCreator = mCreators.array.get(key);
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

    public void addAll(@NonNull ArrayList<T> lists) {
        this.lists.addAll(lists);
    }

    @Override
    public E onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据type返回holder
        int key = mCreators.array.keyAt(viewType);
        RecycleCreatorAdapter.ItemCreator itemCreator = mCreators.getCreatorByType(key);
        if (itemCreator == null) {
            itemCreator = mCreators.getUnrecognizedTypeCreator();
        }
        return (E) itemCreator.creatViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(E holder, int position) {
        // 填写数据 holder.tv.setText...
        T item = getItem(position);
        if (item == null) {
            StringBuilder builder = new StringBuilder();
            for (T m : lists) {
                builder.append(m).append('\n');
            }
            Log.d("RecycleCreatorAdapter",
                    "position:" + position + "size:" + getItemCount() + "data:" + builder.toString());
        }
        ItemCreator<T, E> itemCreator = mCreators.getCreatorByType(item.getCreatorType());
        if (itemCreator != null) {
            itemCreator.onBindViewHolder(holder, position, item);
        } else {
            UnrecognizedTypeCreator<T, E> creatorByType = (UnrecognizedTypeCreator<T, E>) mCreators
                    .getCreatorByType(UnrecognizedTypeCreator.UNRECOGNIZED_TYPE);
            creatorByType.onBindViewHolder(holder, position, item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        if (item == null) {
            StringBuilder builder = new StringBuilder();
            for (T m : lists) {
                builder.append(m).append('\n');
            }
            Log.d("RecycleCreatorAdapter",
                    "position:" + position + "size:" + getItemCount() + "data:" + builder.toString());
        }
        return item == null ? -1 : mCreators.indexOf(item.getCreatorType());
    }

    @Override
    public int getItemCount() {
        return lists.size();
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

    public T getItem(int position) {
        return lists.get(position);
    }

    public Context getContext() {
        return mContext;
    }

    // adapter消息类型
    // public interface com.jiangwei.adapter.lib.baseitem.BaseItem {
    // // 标识消息的唯一id hash
    // int getId();
    //
    // // 每种消息都对应一种type
    // int getCreatorType();
    // }

    public abstract static class ItemCreator<T extends BaseItem, E extends RecycleViewHolder> {
        public int mType;
        private RecycleCreatorAdapter<T, E> mAdapter;

        public ItemCreator(int type) {
            mType = type;
        }

        protected RecycleCreatorAdapter<T, E> getAdapter() {
            if (mAdapter == null) {
                throw new IllegalStateException("register must before new a adapter instance");
            }
            return mAdapter;
        }

        protected T getItem(int position) {
            if (mAdapter == null) {
                throw new IllegalStateException("register must before new a adapter instance instance");
            }
            return mAdapter.getItem(position);
        }

        public abstract E creatViewHolder(ViewGroup parent);

        /**
         * 根据holder设置数据
         * 
         * @param holder
         * @param position
         */
        public abstract void onBindViewHolder(E holder, int position, T item);

        public Context getContext() {
            if (mAdapter == null) {
                throw new IllegalStateException("register must before new a adapter instance");
            }
            return mAdapter.getContext();
        }

        public RecycleViewHolder forException(ViewGroup parent) {
            Context context = getContext();
            View view = new TextView(context);
            return new UnRecognizedViewHolder(view);
        }
    }

    public static class ItemCreators<T extends BaseItem, E extends RecycleViewHolder> {
        private SparseArray<RecycleCreatorAdapter.ItemCreator<T, E>> array = new SparseArray<>();

        public ItemCreators() {
            register(new RecycleCreatorAdapter.UnrecognizedTypeCreator<T, E>());
        }

        public void register(RecycleCreatorAdapter.ItemCreator itemCreatot) {
            if (array.get(itemCreatot.mType) == null) {
                array.put(itemCreatot.mType, itemCreatot);
            } else {
                throw new IllegalStateException("don't register type:" + itemCreatot.mType + "  again");
            }
        }

        public RecycleCreatorAdapter.ItemCreator<T, E> getCreatorByType(int type) {
            return array.get(type);
        }

        public RecycleCreatorAdapter.ItemCreator<T, E> getUnrecognizedTypeCreator() {
            return array.get(RecycleCreatorAdapter.UnrecognizedTypeCreator.UNRECOGNIZED_TYPE);
        }

        public int indexOf(int key) {
            int index = array.indexOfKey(key);
            if (index < 0) {
                index = array.indexOfKey(RecycleCreatorAdapter.UnrecognizedTypeCreator.UNRECOGNIZED_TYPE);
            }
            return index;
        }
    }

    private static class UnrecognizedTypeCreator<T extends BaseItem, E extends RecycleViewHolder>
            extends RecycleCreatorAdapter.ItemCreator<T, E> {

        public static final int UNRECOGNIZED_TYPE = 0;

        public UnrecognizedTypeCreator() {
            super(UNRECOGNIZED_TYPE);
        }

        @Override
        public E creatViewHolder(ViewGroup parent) {
            return (E) forException(parent);
        }

        @Override
        public void onBindViewHolder(E holder, int position, T item) {
            // T item = getItem(position);
            String message = null;
            if (item != null) {
                message = String.format("Type %d cannot be recognized", item.getCreatorType());
            }
            message = message == null ? "" : message + "\n";
            if (item == null) {
                message += "Item is null";
            }
            if (holder instanceof UnRecognizedViewHolder) {
                UnRecognizedViewHolder unRecognizedViewHolder = (UnRecognizedViewHolder) holder;
                unRecognizedViewHolder.tv.setText(
                        String.format("Position %d: %s\n", position, message == null ? item.toString() : message));
            }
        }
    }

    public static class UnRecognizedViewHolder<T extends BaseItem> extends RecycleViewHolder {
        TextView tv;

        public UnRecognizedViewHolder(View view) {
            super(view);
            tv = (TextView) view;
        }
    }

}
