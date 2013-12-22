package com.badlogic.androidgames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MultiTouchTest  extends Activity implements OnTouchListener{
	StringBuilder builder = new StringBuilder();
	TextView textView;
	float[] x = new float[10];
	float[] y = new float[10];
	boolean[] touched = new boolean[10];
	
	private void updateTextView(){
		builder.setLength(0);
		for(int i = 0; i < 10; i++){
			builder.append(touched[i]);
			builder.append(", ");
			builder.append(x[i]);
			builder.append(", ");
			builder.append(y[i]);
			builder.append("\n");
		}
		textView.setText(builder.toString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText("Touch and drag (multiple fingers supported)");
		textView.setOnTouchListener(this);
		setContentView(textView);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> 
		MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int pointerId = event.getPointerId(pointerIndex);
		builder.setLength(0);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			touched[pointerId] = true;
			x[pointerId] = (int)event.getX(pointerIndex);
			y[pointerId] = (int)event.getY(pointerIndex);
			builder.append("down, ");
			break;
		case MotionEvent.ACTION_MOVE:
			int pointerCount = event.getPointerCount();
			for(int i = 0; i < pointerCount; i++){
				pointerIndex = i;
				pointerId = event.getPointerId(pointerIndex);
				x[pointerId] = (int)event.getX(pointerIndex);
				y[pointerId] = (int)event.getY(pointerIndex);
			}
			builder.append("move, ");
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL:
			touched[pointerId] = false;
			x[pointerId] = (int)event.getX(pointerIndex);
			y[pointerId] = (int)event.getY(pointerIndex);
			builder.append("up, ");
			break;
		default:
			break;
		}
		updateTextView();
		builder.append(event.getX());
		builder.append(" , ");
		builder.append(event.getY());
		String text = builder.toString();
		Log.d("TouchTest", text);
		textView.setText(text);
//		try {
//			Thread.sleep(16);
//		} catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
		return true;
	}
	
}
