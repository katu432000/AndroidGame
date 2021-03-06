package com.example.android_mrnom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.androidgames.frameworks.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[] highscores = new int[] {100, 80, 50, 30, 10};
	
	public static void load(FileIO fileIO){
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(fileIO.readFile(".mrnom")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for(int i = 0; i < 5; i++){
				highscores[i] = Integer.parseInt(in.readLine());
			}
		} catch (IOException e) {
			// エラーは無視
		} catch (NumberFormatException e) {
		}finally{
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e2) {
			}
		}
	}
	
	public static void save(FileIO fileIO){
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(fileIO.writeFile(".mrnom")));
			out.write(Boolean.toString(soundEnabled));
			for(int i = 0; i < 5; i++){
				out.write(Integer.toString(highscores[i]));
			}
		} catch (IOException e) {
		} finally{
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e2) {
			}
		}
	}
	
	public static void addScore(int score) {
		for(int i = 0; i < 5; i++){
			if(highscores[i] < score){
				for(int j = 4; j > i; j--){
					highscores[j] = highscores[j - 1];
				}
				highscores[i] = score;
				break;
			}
		}
	}
	
}
