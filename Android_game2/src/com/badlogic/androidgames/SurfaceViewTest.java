package com.badlogic.androidgames;

import com.util.L;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewTest  extends Activity{
	FastRenderView fastRenderView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		fastRenderView = new FastRenderView(this);
		setContentView(fastRenderView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		fastRenderView.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		fastRenderView.pause();
	}
	
	class FastRenderView extends SurfaceView implements Runnable{
		Thread renderThread = null;
		SurfaceHolder holder;
		volatile boolean running = false;
		
		public FastRenderView(Context context) {
			super(context);
			holder = getHolder();
		}
		
		public void resume(){
			running = true;
			renderThread= new Thread(this);
			renderThread.start();
		}
		
		public void run(){
			while(running){
				if(!holder.getSurface().isValid()){
					continue;
				}
				
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB(255, 0, 0);
				holder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void pause(){
			running = false;
			while(true){
				try {
					renderThread.join();
					break;
				} catch (InterruptedException e) {
					L.e(e.getMessage());
				}
			}
		}
	}
}