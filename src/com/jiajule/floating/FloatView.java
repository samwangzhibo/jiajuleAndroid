package com.jiajule.floating;

import com.example.jiajule.R;
import com.example.jiajule.push.ExampleApplication;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @website www.krislq.com
 * @date Nov 29, 2012
 * @version 1.0.0
 * 
 */
public class FloatView extends ImageView {
	private float mTouchX;
	private float mTouchY;
	private float x;
	private float y;
	private OnClickListener mClickListener;

	private WindowManager windowManager = (WindowManager) getContext()
			.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	// ��windowManagerParams����Ϊ��ȡ��ȫ�ֱ��������Ա����������ڵ�����
	private WindowManager.LayoutParams windowManagerParams = ((ExampleApplication) getContext()
			.getApplicationContext()).getWindowParams();

	public FloatView(Context context) {
		super(context);
		isMove = false;
		isRight = false;
		setImageResource(leftResource);
		windowManagerParams.type = LayoutParams.TYPE_PHONE; // ����window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��
		// ����Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * ע�⣬flag��ֵ����Ϊ�� LayoutParams.FLAG_NOT_TOUCH_MODAL ��Ӱ�������¼�
		 * LayoutParams.FLAG_NOT_FOCUSABLE ���ɾ۽� LayoutParams.FLAG_NOT_TOUCHABLE
		 * ���ɴ���
		 */
		// �����������������Ͻǣ����ڵ�������
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		windowManagerParams.x = 0;
		windowManagerParams.y = 0;
		// �����������ڳ�������
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
	}

	private boolean isMove = false;
	private boolean isRight = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ��ȡ��״̬���ĸ߶�
		Rect frame = new Rect();
		getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		System.out.println("statusBarHeight:" + statusBarHeight);
		// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
		x = event.getRawX();
		y = event.getRawY() - statusBarHeight; // statusBarHeight��ϵͳ״̬���ĸ߶�
		Log.i("tag", "currX" + x + "====currY" + y);

		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // ������ָ�������¶���
			// ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
			mTouchX = event.getX();
			mTouchY = event.getY();
			isMove = false;
			Log.i("tag", "startX" + mTouchX + "====startY" + mTouchY);
			if (isRight) {
				setImageResource(focusRightResource);
			} else {
				setImageResource(focusLeftResource);
			}
			break;

		case MotionEvent.ACTION_MOVE: // ������ָ�����ƶ�����
			if (x > 35 && (screenWidth - x) > 35) {
				isMove = true;
				setImageResource(defaultResource);
				updateViewPosition();
			}
			break;
		case MotionEvent.ACTION_UP: // ������ָ�����뿪����
			if (isMove) {
				isMove = false;
				float halfScreen = screenWidth / 2;
				if (x <= halfScreen) {
					setImageResource(leftResource);
					x = 0;
					isRight = false;
				} else {
					setImageResource(rightResource);
					x = screenWidth;
					isRight = true;
				}
				updateViewPosition();
			} else {
				setImageResource(leftResource);
				if (mClickListener != null) {
					mClickListener.onClick(this);
				}
			}
			mTouchX = mTouchY = 0;
			break;
		}
		return true;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.mClickListener = l;
	}

	private void updateViewPosition() {
		// ���¸�������λ�ò���
		windowManagerParams.x = (int) (x - mTouchX);
		windowManagerParams.y = (int) (y - mTouchY);
		windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
	}

	private int defaultResource = R.drawable.icon_default;
	private int focusLeftResource = R.drawable.icon_focus_left;
	private int focusRightResource = R.drawable.icon_focus_right;
	private int leftResource = R.drawable.icon_default_left;
	private int rightResource = R.drawable.icon_default_right;
}
