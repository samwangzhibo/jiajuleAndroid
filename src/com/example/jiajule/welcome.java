package com.example.jiajule;

import com.example.jiajule.push.ExampleUtil;
import cn.jpush.android.api.InstrumentedActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class welcome extends  InstrumentedActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method tub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		
		ImageView im=(ImageView) findViewById(R.id.login_im_icon);
		AlphaAnimation alphaanimation=new AlphaAnimation(0, 1);
		alphaanimation.setDuration(1000);
		alphaanimation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent it=new Intent(welcome.this,login.class);
				startActivity(it);
				finish();
			}
		});
		im.startAnimation(alphaanimation);
		}

	


}
