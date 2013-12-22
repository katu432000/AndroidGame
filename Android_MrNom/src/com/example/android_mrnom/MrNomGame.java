package com.example.android_mrnom;

import com.badlogic.androidgames.frameworks.Screen;
import com.badlogic.androidgames.frameworks.impl.AndroidGame;
public class MrNomGame extends AndroidGame{
	
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}
