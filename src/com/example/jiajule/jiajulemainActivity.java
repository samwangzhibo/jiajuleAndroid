package com.example.jiajule;
//Download by http://www.codefans.net

//import com.xmobileapp.cammonitor.R;

import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class jiajulemainActivity extends Activity implements OnViewChangeListener{
	
	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private Button startBtn;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;
	public  String flag;
	public static final String SETTING_INFOS="SETTING_Infos";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);       
        SharedPreferences settings = getSharedPreferences(SETTING_INFOS,0);
		flag=settings.getString("daohang","false");
		Log.e("wangzhibo", "取得的flag值为"+flag);
        if(flag.matches("false"))	
        {
        	Log.e("wangzhibo", "flag.matches('false')"+"第一次安装");
			Editor sharedata = getSharedPreferences(SETTING_INFOS, 0).edit();
			sharedata.putString("daohang","true");
			sharedata.commit();
        	
        	Log.e("wangzhibo", getSharedPreferences(SETTING_INFOS, 0).getString("daohang", null));
        	initView();
    		
           
        }
        else {
        	Intent it=new Intent(jiajulemainActivity.this,welcome.class);
        	Log.e("wangzhibo","跳转去welcome");
        	startActivity(it);
        	finish();
        }
    }
    
	private void initView() {
		mScrollLayout  = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(onClick);
		animLayout = (LinearLayout) findViewById(R.id.animLayout);
		leftLayout  = (LinearLayout) findViewById(R.id.leftLayout);
		rightLayout  = (LinearLayout) findViewById(R.id.rightLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];//瀹氫箟鏁扮粍
		for(int i = 0; i< count;i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}
	
	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				mScrollLayout.setVisibility(View.GONE);
				pointLLayout.setVisibility(View.GONE);
				animLayout.setVisibility(View.VISIBLE);
				mainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);
				Animation leftOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
				Animation rightOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);
//				Animation leftOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadedout_to_left_down);
//				Animation rightOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadedout_to_right_down);
				leftLayout.setAnimation(leftOutAnimation);
				rightLayout.setAnimation(rightOutAnimation);
				leftOutAnimation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						mainRLayout.setBackgroundColor(getResources().getColor(R.color.black));
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
						leftLayout.setVisibility(View.GONE);
						rightLayout.setVisibility(View.GONE);
						Intent intent = new Intent(jiajulemainActivity.this,login.class);
						startActivity(intent);
//						Editor sharedata = getSharedPreferences(SETTING_INFOS, 0).edit();
//						sharedata.putString("daohang","true");
//						sharedata.commit();
						jiajulemainActivity.this.finish();
						overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
					}
				});
				break;
			}
		}
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if(position < 0 || position > count -1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}
}