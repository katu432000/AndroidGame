package com.badlogic.androidgames;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ShapeTest  extends Activity{
	
	class RenderView extends View{
		
		Paint paint;
		Random random = new Random();
		int x = 0;
		boolean i = false;
		int width = 0;

		public RenderView(Context context){
			super(context);
			paint = new Paint();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(255, 255, 255);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(30);
			if(!i){
				width = canvas.getWidth();
				i = true;
			}
			canvas.drawLine(x+=2, 0, width -= 2, canvas.getHeight() - 1, paint);
			
			paint.setStyle(Style.STROKE);
			paint.setColor(0xff00ff00);
			canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40, paint);
			
			paint.setStyle(Style.FILL);
			paint.setColor(Color.YELLOW);
			canvas.drawRect(100, 100, 200, 200, paint);
			invalidate();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new RenderView(this));
	}
}
