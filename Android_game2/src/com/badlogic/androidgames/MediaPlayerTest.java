package com.badlogic.androidgames;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class MediaPlayerTest  extends Activity{
	
	MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		mediaPlayer = new MediaPlayer();
		
		try {
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor = assetManager.openFd("music.ogg");
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			mediaPlayer.prepare();
			mediaPlayer.setLooping(true);
		} catch (Exception e) {
			textView.setText("Couldn't load music file, " + e.getMessage());
			mediaPlayer = null;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(mediaPlayer != null){
			mediaPlayer.start();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(mediaPlayer != null){
			mediaPlayer.pause();
			if(isFinishing()){
				mediaPlayer.stop();
				mediaPlayer.release();
			}
		}
	}
}
