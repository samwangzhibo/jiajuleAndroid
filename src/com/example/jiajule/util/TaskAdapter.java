package com.example.jiajule.util;

import com.example.jiajule.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter{
	String[] item_id,item_username,item_msg,item_time;
	Context context;
	public TaskAdapter(Context context,String[] item_id,String[] item_username,String[] item_msg,String[] item_time){
		this.item_id=item_id;
		this.item_msg=item_msg;
		this.item_time=item_time;
		this.item_username=item_username;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item_id==null?0:item_id.length;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item_id==null?null:item_id[position];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return item_id==null?0:position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v  = convertView;
		if(convertView == null)
		{
			v=LayoutInflater.from(context).inflate(R.layout.renwutie_list, null);
			TextView id=(TextView) v.findViewById(R.id.renwutie_item_id);
			id.setText(item_id[position]);
			TextView username=(TextView) v.findViewById(R.id.renwutie_item_username);
			username.setText(item_username[position]);
			TextView msg=(TextView) v.findViewById(R.id.renwutie_item_msg);
			msg.setText(item_msg[position]);
			TextView time=(TextView) v.findViewById(R.id.renwutie_item_time);
			time.setText(item_time[position]);
		}
		return v;
	}
	
}
