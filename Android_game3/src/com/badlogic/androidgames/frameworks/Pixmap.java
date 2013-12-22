package com.badlogic.androidgames.frameworks;

import com.badlogic.androidgames.frameworks.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
}
