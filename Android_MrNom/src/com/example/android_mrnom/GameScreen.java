package com.example.android_mrnom;

import java.util.List;

import android.R.bool;
import android.graphics.Color;

import com.badlogic.androidgames.frameworks.Game;
import com.badlogic.androidgames.frameworks.Graphics;
import com.badlogic.androidgames.frameworks.Pixmap;
import com.badlogic.androidgames.frameworks.Screen;
import com.badlogic.androidgames.frameworks.Input.TouchEvent;

public class GameScreen extends Screen{
	
	enum GameState{
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	GameState gameState = GameState.Ready;
	World world;
	int oldScore = 0;
	String score = "0";
	boolean drawTime = false;
	
	public GameScreen(Game game) {
		super(game);
		world = new World();
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		if(gameState == GameState.Ready){
			updateReady(touchEvents);
		}
		if(gameState == GameState.Running){
			updateRunning(touchEvents, deltaTime);
		}
		if(gameState == GameState.Paused){
			updatePaused(touchEvents);
		}
		if(gameState == GameState.GameOver){
			updateGameOver(touchEvents);
		}
	}
	
	private void updateReady(List<TouchEvent> touchEvent){
		if(touchEvent.size() > 0){
			gameState = GameState.Running;
		}
	}
	
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x < 64 && event.y < 64){
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					gameState = GameState.Paused;
					return;
				}
				
				drawTime = true;
			}
						
			if(event.type == TouchEvent.TOUCH_DOWN){
				if(event.x < 64 && event.y > 416){
					world.snake.turnLeft();
				}
				if(event.x > 256 && event.y > 416){
					world.snake.turnRight();
				}
			}
		}
		//world.update(deltaTime);
		if(world.gameOver){
			if(Settings.soundEnabled){
				Assets.bitten.play(1);
			}
			gameState = GameState.GameOver;
		}
		if(oldScore != world.score){
			oldScore = world.score;
			score = "" + oldScore;
			if(Settings.soundEnabled){
				Assets.eat.play(1);
			}
		}
	}
	
	private void updatePaused(List<TouchEvent> touchEvents){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x > 80 && event.x <= 240){
					if(event.y > 100 && event.y <= 148){
						if(Settings.soundEnabled){
							Assets.click.play(1);
						}
						gameState = GameState.Running;
						return;
					}
					if(event.y > 148 && event.y < 196){
						if(Settings.soundEnabled){
							Assets.click.play(1);
						}
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}
	
	private void updateGameOver(List<TouchEvent> touchEvents){
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x >= 128 && event.x <= 192 &&
				    event.y >= 200 && event.y <= 264){
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.backGround, 0, 0);
		//TODO 白を描画する
		if(drawTime){
			g.drawPixmap(Assets.buttons, 100, 100);
			drawTime = false;
		}
		
		drawWorld(world);
		if(gameState == GameState.Ready){
			drawReadyUI();
		}
		if(gameState == GameState.Running){
			drawRunningUI();
		}
		if(gameState == GameState.Paused){
			drawPausedUI();
		}
		if(gameState == GameState.GameOver){
			drawGameOverUI();
		}
		
		drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2, g.getHeight() - 42);
	}
	
	private void drawWorld(World world){
		Graphics g = game.getGraphics();
		Snake snake = world.snake;
		SnakePart head = snake.parts.get(0);
		Stain stain = world.stain;
		
		Pixmap stainPixmap = null;
		
		if(stain.type == Stain.TYPE_1){
			stainPixmap = Assets.stain1;
		}
		if(stain.type == Stain.TYPE_2){
			stainPixmap = Assets.stain2;
		}
		if(stain.type == Stain.TYPE_3){
			stainPixmap = Assets.stain3;
		}
		int x = stain.x * 32;
		int y = stain.y * 32;
		g.drawPixmap(stainPixmap, x, y);
		
		int len = snake.parts.size();
		for(int i = 1; i < len; i++){
			SnakePart part = snake.parts.get(i);
			x = part.x * 32;
			y = part.y * 32;
			g.drawPixmap(Assets.tail, x, y);
		}
		
		Pixmap headPixmap = null;
		if(snake.direction == Snake.UP){
			headPixmap = Assets.headUp;
		}
		if(snake.direction == Snake.LEFT){
			headPixmap = Assets.headLeft;
		}
		if(snake.direction == Snake.DOWN){
			headPixmap = Assets.headDown;
		}
		if(snake.direction == Snake.RIGHT){
			headPixmap = Assets.headRight;
		}
		x = head.x * 32 + 16;
		y = head.y * 32 + 16;
		g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
	}
	
	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.ready, 47, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
		g.drawLine(0, 416, 481, 416, Color.BLACK);
		g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
		g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
	}
	
	private void drawPausedUI(){
			Graphics g = game.getGraphics();
			
			g.drawPixmap(Assets.pause, 80, 100);
			g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.gameOver, 62, 100);
		g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}
	
	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for(int i = 0; i < len; i++){
			char charcter = line.charAt(i);
			
			if(charcter == ' '){
				x += 20;
				continue;
			}
			
			int srcX = 0;
			int srcWidth = 0;
			if(charcter == '.'){
				srcX = 200;
				srcWidth = 10;
			}
			else{
				srcX = (charcter - '0') * 20;
				srcWidth = 20;
			}
			
			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}
	
	public void animeA() {
		
	}
	
	@Override
	public void pause() {
		if(gameState == GameState.Running){
			gameState = GameState.Paused;
			
			if(world.gameOver){
				Settings.addScore(world.score);
				Settings.save(game.getFileIO());
			}
		}
	}
	
	@Override
	public void resume() {		
	}
	
	@Override
	public void dispose() {		
	}
}
