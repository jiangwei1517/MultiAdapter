package com.jiangwei.example.recycleview;

import com.jiangwei.adapter.lib.recycle.creator.RecycleCreatorAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import con.jiangwei.example.R;

/**
 * author: jiangwei18 on 17/4/1 20:54 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class RecycleActivity extends AppCompatActivity {
    private RecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_main);
        mRv = (RecyclerView) findViewById(R.id.rv);
        RecycleCreatorAdapter.ItemCreators creators = new RecycleCreatorAdapter.ItemCreators();
        RecycleOtherCreator recycleOtherCreator = new RecycleOtherCreator(1);
        RecycleMyCreator recycleMyCreator = new RecycleMyCreator(2);
        OtherTextMessage otherMsg = new OtherTextMessage(1);
        MyTextMessage myMsg = new MyTextMessage(2);
        otherMsg.setContent("你好,我是客服");
        myMsg.setContent("你好，我感冒了");
        creators.register(recycleOtherCreator);
        creators.register(recycleMyCreator);
        RecycleCreatorAdapter adapter = new RecycleCreatorAdapter(this, creators);
        adapter.add(otherMsg);
        adapter.add(myMsg);
        // 会话流只支持LinearLayoutManager流的形式。。。
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(adapter);
    }
}
