package com.badlogic.androidgames;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FontTest  extends Activity{
	
	class RenderView extends View{
		Paint paint;
		Typeface font;
		Rect bounds = new Rect();
		
		public RenderView(Context context) {
			super(context);
			paint = new Paint();
			font = Typeface.createFromAsset(context.getAssets(), "font.ttf");
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			paint.setColor(Color.GREEN);
			paint.setTypeface(font);
			paint.setTextSize(50);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("This is a test", canvas.getWidth() / 2, 100, paint);
			
			String text = "This is another test o_0";
			paint.setColor(Color.BLUE);
			paint.setTextSize(70);
			paint.setTextAlign(Paint.Align.LEFT);
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText(text, canvas.getWidth() / 2  - bounds.width(), 140, paint);
			invalidate();
			super.onDraw(canvas);
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
