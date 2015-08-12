package com.jiajule.adapter;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiajule.R;
import com.example.jiajule.R.layout;
import com.jiajule.db.DBHelper;
import com.jiajule.db.DBOpreate;
import com.jiajule.javabean.*;

public class UserInfoAdapter extends BaseAdapter{
	Context context;
	List<UserInfo> users;
	UserInfo userinfo;
	Dialog mdialog;
	public UserInfoAdapter(Context context,List<UserInfo> users,Dialog mdialog) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.users=users;
		this.mdialog=mdialog;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users==null?0:users.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return users==null?null:users.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return users==null?null:position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		
		if(null == v){
			//创建view
			v=LayoutInflater.from(context).inflate(R.layout.users_selected_templete, null);
			//实例化组件
			TextView tv=(TextView) v.findViewById(R.id.txt_show_name);
			ImageView im=(ImageView) v.findViewById(R.id.userselect_temple_delete);
			
			userinfo = users.get(position);
			final int position2=position;
			tv.setText(userinfo.getUsername());
			im.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//deleteByname
					userinfo = users.get(position2);
					DBHelper mdbhleper=new DBHelper(context);
					SQLiteDatabase db=mdbhleper.getReadableDatabase();
					DBOpreate.DeleteUserInfoByUsername(userinfo,db);
					mdialog.dismiss();
				}
			});
		}
		
		return v;
	}



}
