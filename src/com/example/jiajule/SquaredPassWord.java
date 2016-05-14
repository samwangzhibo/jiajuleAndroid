package com.example.jiajule;

//import com.xmobileapp.cammonitor.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * @Title: SquaredPassWord.java
 * @Description: 九宫格密�?
 * @author wzb
 * @date 2013�?�?6�?下午3:48:10
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class SquaredPassWord extends View {
	ImageView i;
	private int length;// 九宫格密码是正方形所以只要知道边长就可以
	private Point[] points = new Point[9];// 九宫格节点
	private Bitmap defualtPointMap = BitmapFactory.decodeResource(getResources(), R.drawable.locus_round_original);// 正常情况下点的位�?
	private int poitleght = defualtPointMap.getWidth();// 节点的边长；这里值�?虑正方形状�?
	private Bitmap selectPointMap = BitmapFactory.decodeResource(getResources(), R.drawable.locus_round_click);// 选中情况下点的位�?
	private Point startPoint;// 起点
	private Point tempPoint;// 临时存储上一个节点
	private StringBuffer passWBuffer = new StringBuffer();// 保存轨迹顺序的密码
	private Bitmap lineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.locus_line);
	private int lineBitmapheight = lineBitmap.getHeight();
	private double lineBitmapWidth = lineBitmap.getWidth();
	// 以下四个变量是为了绘制最后一个跟手指之间的连�?
	private int startX;// 移动起点X
	private int startY;// 移动起点Y
	private int moveX;// 正在移动的X
	private int moveY;// 正在移动的Y

	public SquaredPassWord(Context context) {
		super(context);

	}

	public SquaredPassWord(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public SquaredPassWord(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}



	/**
	 * 回复各个点到初始状态
	 */
	private void reSetData() {
		for (Point point : points) {
			point.setSelected(false);
			point.setNextID(point.getId());
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getWidth() - getPaddingLeft() - getPaddingRight();
		int height = getHeight() - getPaddingTop() - getPaddingBottom();
		length = (width < height ? width : height);// 获取边长
		if(!(length>0)){
			
		}
		System.out.println(length);
		initPionts(points);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (moveX != 0 && moveY != 0 && startX != 0 && startY != 0) {
			// 绘制当前活动的线s
			drawLine(startX, startY, moveX, moveY, canvas);
		}
		drawLinePoint(canvas);
		super.onDraw(canvas);
	}

	/**
	 * 初始各节
	 * 
	 * @param pionts
	 */
	@SuppressWarnings("null")
	private void initPionts(Point[] points) {
		int spacing = (length - poitleght * 3) / 2;

		if (points == null && points.length != 9) {// 只做九宫格的处理
			return;
		} else {
			for (int i = 0; i < 9; i++) {
				int row = i / 3;// 行数
				int column = i % 3;// 列数；求整取�?

				int x = (poitleght + spacing) * column + getPaddingLeft();// x坐标
				int y = (poitleght + spacing) * row + getPaddingTop();// y坐标
				Point point = new Point((i + 1), x, y, poitleght);
				points[i] = point;
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean flag = true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			passWBuffer.delete(0, passWBuffer.length());
			int x = (int) event.getX();
			int y = (int) event.getY();
			for (Point point : points) {
				if (point.isInMyArea(x, y)) {
					point.setSelected(true);
					tempPoint = startPoint = point;
					startX = startPoint.getCenterX();
					startY = startPoint.getCenterY();
					passWBuffer.append(startPoint.getId());
				}
			}
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			moveX = (int) event.getX();
			moveY = (int) event.getY();
			if(points != null){
			for (Point point : points) {
				if (point.isInMyArea(moveX, moveY) && !point.isSelected()) {
					tempPoint.setNextID(point.getId());
					point.setSelected(true);
					tempPoint = point;
					startX = tempPoint.getCenterX();
					startY = tempPoint.getCenterY();
					passWBuffer.append(tempPoint.getId());
				}
			}
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			reSetData();
			startX = startY = moveX = moveY = 0;
			invalidate();
			flag = false;
			break;

		default:
			break;
		}
		return flag;
	}
	/**
	 * 绘制各节点以及被选择的个节点之间的连线轨�?
	 * 
	 * @param canvas
	 */
	private void drawLinePoint(Canvas canvas) {
		if (startPoint != null) {
			drawP2POrbit(startPoint, canvas);
		}
		Paint paint = null;// new Paint();
		// 绘制每个点的图片
		for (Point point : points) {
			if (point.isSelected()) {// 绘制大圈
				canvas.drawBitmap(selectPointMap, point.getX(), point.getY(), paint);
			} else {
				canvas.drawBitmap(defualtPointMap, point.getX(), point.getY(), paint);
			}
		}
	}

	/**
	 * 绘制点与点之间的轨迹
	 * 
	 * @param canvas
	 */
	private void drawP2POrbit(Point point, Canvas canvas) {
		if (point.getId() != point.nextID) {
			// canvas.concat(matrix);
			Point endPoint = null;
			// 获取目标节点
			for (Point point3 : points) {
				if (point3.getId() == point.getNextID()) {
					endPoint = point3;
					break;
				}
			}
			if (endPoint != null) {
				// 画线
				drawLine(point.getCenterX(), point.getCenterY(), endPoint.getCenterX(), endPoint.getCenterY(), canvas);
				// 递归
				drawP2POrbit(endPoint, canvas);
			}
		}
	}

	/**
	 * 画连�?
	 * 
	 * @param startX
	 *            起点X
	 * @param startY
	 *            起点Y
	 * @param stopX
	 *            终点X
	 * @param stopY
	 *            终点Y
	 * @param canvas
	 */
	private void drawLine(int startX, int startY, int stopX, int stopY, Canvas canvas) {
		Paint paint = new Paint();
		// 获得斜边长度
		double hypotenuse = Math.hypot((stopX - startX), (stopY - startY));
		// double side = stopX - startX;// 邻边
		// double piAngle = Math.acos(side / hypotenuse);// pi角度
		// float rotate = (float) (180 / Math.PI * piAngle);// 转换的角�?
		float rotate = getDegrees(startX, startY, stopX, stopY);
		Matrix matrix = new Matrix();
		// matrix.postRotate(rotate);//不能用这个matritx 来�?择角度只能用 让canvas懒�?�?
				// 用matrix的话会引起图片所表示的线条不在中心点�?
				canvas.rotate(rotate, startX, startY);
		matrix.preTranslate(0, 0);
		matrix.setScale((float) (hypotenuse / lineBitmapWidth), 1.0f);
		matrix.postTranslate(startX, startY - lineBitmapheight / 2.f);
		canvas.drawBitmap(lineBitmap, matrix, paint);
		canvas.rotate(-rotate, startX, startY);//恢复
		canvas.save();

//		Paint paint1 = new Paint();
//		paint1.setColor(Color.BLACK);
//		paint1.setStrokeWidth(8);// 粗细
//		paint1.setFlags(Paint.ANTI_ALIAS_FLAG);
//		canvas.drawLine(startX, startY, stopX, stopY, paint1);

	}

	/**
	 * 获取角度
	 * 
	 * @param startX
	 *            起点X
	 * @param startY
	 *            起点Y
	 * @param stopX
	 *            终点X
	 * @param stopY
	 *            终点Y
	 */
	private float getDegrees(int startX, int startY, int stopX, int stopY) {
		// 获得斜边长度
		double hypotenuse = Math.hypot((stopX - startX), (stopY - startY));
		double side = stopX - startX;// 邻边
		double piAngle = Math.acos(side / hypotenuse);// pi角度
		float rotate = (float) (180 / Math.PI * piAngle);// 转换的角度（0--180);
		if (stopY - startY < 0) {// 如果Y愁小�?说明角度在第三或者第四像�?
			rotate = 360 - rotate;
		}
		return rotate;
	}

	/**
	 * 轨迹顺序密码
	 * 
	 * @return
	 */
	public String getOrbitString() {
		return (passWBuffer == null ? null : passWBuffer.toString());
	}

	/**
	 * 表示�?���?
	 * 
	 * @author lanhaizhong
	 * 
	 */
	class Point {

		private int id;// 点的id
		private int nextID;// 连向下一个�?点的id
		private int x;// 点的左上角x坐标
		private int y;// 点的左上角的y坐标
		private boolean isSelected;// 该节点是否被选中
		private int width;// 点的长度 这里只�?虑正方形

		public Point() {
			super();
			// TODO Auto-generated constructor stub
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getNextID() {
			return nextID;
		}

		public void setNextID(int nextID) {
			this.nextID = nextID;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public Point(int id, int x, int y, int width) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.nextID = id;
			this.isSelected = false;
			this.width = width;
		}

		public int getCenterX() {
			return x + (width / 2);
		}

		private int getCenterY() {
			return y + (width / 2);
		}

		/**
		 * 判断某个坐标是否这个这个表示�?��点的图形区域�?
		 * 
		 * @param x
		 * @param y
		 * @return
		 */
		public boolean isInMyArea(int x, int y) {

			// return (this.x < x && x < (this.x + width)) && (this.y < y && y <
			// (this.y + width));
			return ((this.getCenterX() - lineBitmapWidth / 2) < x && x < (this.getCenterX() + lineBitmapWidth / 2))
					&& ((this.getCenterY() - lineBitmapWidth / 2) < y && y < (this.getCenterY() + lineBitmapWidth / 2));
		}
	}
}
