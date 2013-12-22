package com.badlogic.androidgames.frameworks.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.badlogic.androidgames.frameworks.Graphics;
import com.badlogic.androidgames.frameworks.Pixmap;

public class AndroidGraphics implements Graphics{
	AssetManager assetManager;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	public AndroidGraphics(AssetManager assetManager, Bitmap frameBuffer) {
		this.assetManager = assetManager;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}
	
	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat pixmapFormat) {
		Config config = null;
		if(pixmapFormat == PixmapFormat.RGB565){
			config = Config.RGB_565;
		}
		else if(pixmapFormat == PixmapFormat.ARGB4444){
			config = Config.ARGB_4444;
		}
		else {
			config = Config.ARGB_8888;
		}
		
		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assetManager.open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if(bitmap == null){
				throw new RuntimeException("Couldn't load bitmap from asset" + fileName);
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset" + fileName);
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e){
				}
			}
		}
		
		if(bitmap.getConfig() == Config.RGB_565){
			pixmapFormat = PixmapFormat.RGB565;
		}
		else if(bitmap.getConfig() == Config.ARGB_4444){
			pixmapFormat = PixmapFormat.ARGB4444;
		}
		else{
			pixmapFormat = PixmapFormat.ARGB8888;
		}
		return new AndroidPixmap(bitmap, pixmapFormat);
	}
	
	@Override
	public void clear(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
	}
	
	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}
	
	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}
	
	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}
	
	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidht, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidht - 1;
		srcRect.bottom = srcY + srcHeight - 1;
		
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidht - 1;
		dstRect.bottom = y + srcHeight - 1;
		
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, srcRect, dstRect, null);
	}
	
	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
	}
	
	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}
	
	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}
}
