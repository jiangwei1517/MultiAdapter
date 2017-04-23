package com.jiangwei.example.listview;

import com.jiangwei.adapter.lib.list.creator.ListCreatorAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import con.jiangwei.example.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
        ListView lv = (ListView) findViewById(R.id.lv);
        ListCreatorAdapter.ItemCreators creators = new ListCreatorAdapter.ItemCreators();
        creators.register(new OtherTextCreator(SendType.TYPE_OTHER_TEXT));
        creators.register(new MyTextCreator(SendType.TYPE_MY_TEXT));
        OtherTextListMessage otherTextMessage = new OtherTextListMessage(SendType.TYPE_OTHER_TEXT);
        otherTextMessage.setContent("你好,有什么能帮您的?");
        MyTextListMessage myTextMessage = new MyTextListMessage(SendType.TYPE_MY_TEXT);
        myTextMessage.setContent("你好,我感冒了怎么办");
        ListCreatorAdapter adapter = new ListCreatorAdapter(this, creators);
        adapter.add(otherTextMessage);
        adapter.add(myTextMessage);
        lv.setAdapter(adapter);
    }
}
