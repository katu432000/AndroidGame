package com.badlogic.androidgames.frameworks.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.badlogic.androidgames.frameworks.FileIO;

public class AndroidFileIO  implements FileIO{
	
	AssetManager assetManager;
	String externalStoragePath;
	
	public AndroidFileIO(AssetManager assetManager) {
		this.assetManager = assetManager;
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	@Override
	public InputStream readAsset(String fileName) throws IOException {
		return assetManager.open(fileName);
	}
	
	@Override
	public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath + fileName);
	}
	
	public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(externalStoragePath + fileName);
	};
}
