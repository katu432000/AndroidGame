package com.badlogic.androidgames;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RenderViewTest  extends Activity{
	
	class RenderView extends View{
		Random random = new Random();
		
		public RenderView(Context context) {
			super(context);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
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
