package com.badlogic.androidgames;

import java.io.IOException;
import java.io.InputStream;

import com.util.L;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BitmapTest  extends Activity{
	
	class RenderView extends View{
		Bitmap bob565;
		Bitmap bob4444;
		Rect dstRect = new Rect();
		
		public RenderView(Context context) {
			super(context);
			InputStream inputStream = null;
			
			try {
				AssetManager assetManager = context.getAssets();
				inputStream = assetManager.open("bobrgb888.png");
				bob565 = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				L.d("bobrgb888.png format: " + bob565.getConfig());
				
				inputStream = assetManager.open("bobargb8888.png");
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_4444;
				bob4444 = BitmapFactory.decodeStream(inputStream, null, options);
				inputStream.close();
				L.d( "bobargb8888.png format: " + bob4444.getConfig());
			} catch (IOException e) {
				e.getMessage();
			}
			finally{
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawRGB(0, 0, 0);
			dstRect.set(50, 50, 350, 350);
			canvas.drawBitmap(bob565, null, dstRect, null);
			canvas.drawBitmap(bob4444, 100, 100, null);
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
