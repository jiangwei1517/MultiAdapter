# MultiAdapter
![MacDown logo](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492967071848&di=556bb68e069546772fb5e96c95bdab87&imgtype=0&src=http%3A%2F%2Fpic.yesky.com%2FuploadImages%2F2015%2F160%2F42%2FI7K8497S52KR.jpg)

## 解决问题
* 会话流样式问题
* 根据消息的type灵活配置adapter
* 适用recycleView与ListView

## 基本思想
### ListView
* getItemViewType
* getView
* 异常View，默认View等

### RecycleView
* getItemViewType
* onBindViewHolder
* 异常View，默认View等

## 使用方法
### 初始化creators
	ListView lv = (ListView) findViewById(R.id.lv);
    ListCreatorAdapter.ItemCreators creators = new ListCreatorAdapter.ItemCreators();
    
### 注册creator

	creators.register(new OtherTextCreator(SendType.TYPE_OTHER_TEXT));
    creators.register(new MyTextCreator(SendType.TYPE_MY_TEXT));
其中***OtherTextCreator***，***MyTextCreator***都是继承ItemCreator。
	
	public class OtherTextCreator extends ListCreatorAdapter.ItemCreator {
	    public OtherTextCreator(int type) {
	        super(type);
	    }
	
	    @Override
	    public View creatView(int position, View convertView, ViewGroup parent) {
	        convertView = View.inflate(getContext(), R.layout.other_text_view, null);
	        TextView tv = (TextView) convertView.findViewById(R.id.tv);
	        BaseItem item = getItem(position);
	        if (item instanceof OtherTextListMessage) {
	            OtherTextListMessage msg = (OtherTextListMessage) getItem(position);
	            tv.setText(msg.getContent());
	        }
	        return convertView;
	    }
	}
	
### 添加消息
每种消息的类型要匹配上面注册的类型，否则会以异常的view的形式告知用户。

	OtherTextListMessage otherTextMessage = new OtherTextListMessage(SendType.TYPE_OTHER_TEXT);
    otherTextMessage.setContent("你好,有什么能帮您的?");
    MyTextListMessage myTextMessage = new MyTextListMessage(SendType.TYPE_MY_TEXT);
    myTextMessage.setContent("你好,我感冒了怎么办");
    
### 创建Adapter

	ListCreatorAdapter adapter = new ListCreatorAdapter(this, creators);
    adapter.add(otherTextMessage);
    adapter.add(myTextMessag）
    lv.setAdapter(adapter);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        