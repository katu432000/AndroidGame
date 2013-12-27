package com.badlogic.androidgames.frameworks;

import android.R.integer;

public interface Graphics {
	public static enum PixmapFormat{
		ARGB8888, ARGB4444, RGB565
	}
	
	public Pixmap newPixmap(String fileName, PixmapFormat pixmapFormat);
	
	public void clear(int color);
	public void drawPixel(int x, int y, int color);
	public void drawLine(int x, int y, int x2, int y2, int color);
	public void drawRect(int x, int y, int width, int height, int color);
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidht, int srcHeight);
	public void drawPixmap(Pixmap pixmap, int x, int y);
	public void drawColor(int red, int green, int blue);
	public int getWidth();
	public int getHeight();
}
