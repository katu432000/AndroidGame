package com.badlogic.androidgames.frameworks.impl;

import com.badlogic.androidgames.frameworks.Audio;
import com.badlogic.androidgames.frameworks.FileIO;
import com.badlogic.androidgames.frameworks.Game;
import com.badlogic.androidgames.frameworks.Graphics;
import com.badlogic.androidgames.frameworks.Input;
import com.badlogic.androidgames.frameworks.Screen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AndroidGame extends Activity implements Game {
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		
		//API14以降はディスプレイサイズの取得が変更
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		float scaleX = (float)frameBufferWidth / size.x;
		float scaleY = (float)frameBufferHeight / size.y;
		
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(getAssets());
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		
		screen = getStartScreen();
		setContentView(renderView);
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GLGame");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if(isFinishing()){
			screen.dispose();
		}
	}
		
	@Override
	public Input getInput() {
		return input;
	}
	
	@Override
	public FileIO getFileIO() {
		return fileIO;
	}
	
	@Override
	public Graphics getGraphics() {
		return graphics;
	}
	
	@Override
	public Audio getAudio() {
		return audio;
	}
	
	@Override
	public void setScreen(Screen screen) {
		if(screen == null){
			throw new IllegalArgumentException("screen must not be null");
		}
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	@Override
	public Screen getCurrentScreen() {
		return screen;
	}
}
