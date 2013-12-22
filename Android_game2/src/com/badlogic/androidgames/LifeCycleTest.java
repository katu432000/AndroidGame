package com.badlogic.androidgames;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LifeCycleTest  extends Activity{
	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	/**
	 * ログを出力
	 * @param text
	 */
	private void log(String text){
		Log.d("LifeCycleText", text);
		builder.append(text);
		builder.append('\n');
		textView.setText(builder.toString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText(builder.toString());
		setContentView(textView);
		log("create");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		log("resume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		log("pause");
		if(isFinishing()){
			log("finisihing");
		}
	}
	
}
