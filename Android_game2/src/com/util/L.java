package com.util;

import java.util.regex.Pattern;

import android.util.Log;

import com.example.android_game.BuildConfig;

/**
 * ログ出力クラス
 *
 */
public class L {
	
	/**
	 * ログを出力
	 * @param msg
	 */
	public static void d(String msg){
		if(!BuildConfig.DEBUG){
			return;
		}
		Log.d(getTag(), msg);
	}
	
	/**
	 * エラー用ログを出力。
	 * catchの中や想定外の動作でログを出力するときに使用。
	 * @param msg
	 */
	public static void e(String msg){
		Log.e(getTag(), msg);
	}
	
	/**
	 * 同上
	 * @param msg
	 * @param t
	 */
	public static void e(String msg, Throwable t) {
		Log.e(getTag(), msg, t);
	}

	/**
	 * タグを取得
	 * @return
	 */
	private static String getTag(){
		final StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
		final String cla = trace.getClassName();
		Pattern pattern = Pattern.compile("[\\.]+");
		final String[] splitedStr = pattern.split(cla);
		final String simpleClass = splitedStr[splitedStr.length - 1];
		
		final String mthd = trace.getMethodName();
		final int line = trace.getLineNumber();
		final String tag = simpleClass + "#" + mthd + ":" + line;
		
		return tag;
	}
}
