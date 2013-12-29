package com.engine;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleLayoutGameActivity;


public class MainActivity extends SimpleLayoutGameActivity{

	@Override
	public EngineOptions onCreateEngineOptions() {
		return new EngineOptions(
		        true,	// タイトルバー非表示
				ScreenOrientation.PORTRAIT_FIXED,	  // 縦向き 
				new RatioResolutionPolicy(480, 800),  // 解像度の縦横比を保ったまま拡大
				new Camera(0, 0, 480, 800));		  // 480, 800の描画範囲
	}

	@Override
	protected void onCreateResources() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	protected Scene onCreateScene() {
		MainScene mainScene = new MainScene(this);
		return mainScene;
	}

	@Override
	protected int getLayoutID() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	
}
