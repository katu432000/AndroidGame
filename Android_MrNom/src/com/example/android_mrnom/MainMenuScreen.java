package com.example.android_mrnom;

import java.util.List;

import com.badlogic.androidgames.frameworks.Game;
import com.badlogic.androidgames.frameworks.Graphics;
import com.badlogic.androidgames.frameworks.Screen;
import com.badlogic.androidgames.frameworks.Input.TouchEvent;
import com.utils.L;

public class MainMenuScreen extends Screen{
	
	public MainMenuScreen(Game game) {
		super(game);
	}
	
	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			L.d("X : " + event.x);
			L.d("Y : " + event.y);
			if(event.type == TouchEvent.TOUCH_UP){
				if(inBounds(event, 0, g.getHeight() - 64, 64, 64)){
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
				}
				if(inBounds(event, 64, 220, 192, 42)){
					game.setScreen(new GameScreen(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					return;
				}
				if(inBounds(event, 64, 220 + 42, 192, 42)){
					game.setScreen(new HighScore(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					return;
				}
				if(inBounds(event, 64, 220 + 84, 192, 42)){
					game.setScreen(new HelpScreen(game));
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					return;
				}
			}
		}
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height){
		if(event.x > x && event.x < x + width - 1 &&
			event.y > y && event.y < y + height - 1){
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.backGround, 0, 0);
		g.drawPixmap(Assets.logo, 64, 10);
		g.drawPixmap(Assets.mainMenu, 100, 180);
		if(Settings.soundEnabled){
			g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
		}
		else{
			g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
		}
	}
	
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}
	
	@Override
	public void dispose() {		
	}
	
	@Override
	public void resume() {		
	}
}
