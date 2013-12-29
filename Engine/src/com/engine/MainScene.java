package com.engine;

import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

public class MainScene extends Scene{
	
	private BaseGameActivity baseGameActivity;
	
	public MainScene(BaseGameActivity baseGameActivity) {
		this.baseGameActivity = baseGameActivity;
		init();
	}
	
	public void init(){
		
	}
}
