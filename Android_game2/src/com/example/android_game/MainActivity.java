package com.example.android_game;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	String tests[] = {"LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
			"KeyTest", "AccelerometerTest", "AssetsTest", "ExternalStorageTest",
			"SoundPoolTest", "MediaPlayerTest", "WakeLockTest", "FullScreenTest", "RenderViewTest",
			"ShapeTest", "BitmapTest", "FontTest", "SurfaceViewTest"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, tests));
        //setContentView(R.layout.activity_main);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	String testName = tests[position];
    	try {
			Class clazz = Class.forName("com.badlogic.androidgames." + testName);
			Intent intent = new Intent(this, clazz);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
