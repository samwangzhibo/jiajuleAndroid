package com.example.jiajule.view;

import com.example.jiajule.R;
import android.app.Activity;
import android.os.Bundle;

public class unlock_new extends Activity {
	private SlideMenu slideMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unlock_new);
		slideMenu=(SlideMenu) findViewById(R.id.slide_menu);
		
	}

}
