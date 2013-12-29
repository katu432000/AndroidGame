package com.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceUtil {
	
	// 自身のインスタンを作成
	private static ResourceUtil self;
	
	// Context
	private static BaseGameActivity baseGameActivity;
	
	// 再利用するためのメンバ変数
	private static HashMap<String, ITextureRegion> textureRegionPool;
	private static HashMap<String, TiledTextureRegion> tiledTextureRegionPool;
	
	private ResourceUtil(){
		
	}
	
	// イニシャライズ
	public static ResourceUtil getInstance(BaseGameActivity baseGameActivity){
		if(self == null){
			self = new ResourceUtil();
			ResourceUtil.baseGameActivity = baseGameActivity;
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			
			textureRegionPool = new HashMap<String, ITextureRegion>();
			tiledTextureRegionPool = new HashMap<String, TiledTextureRegion>();
		}
		return self;
	}
	
	// ファイル名を指定してSpriteを得る
	public Sprite getSprite(String fileName) {
		// 同名のファイルが有れば再利用
		if(textureRegionPool.containsKey(fileName)){
			Sprite s = new Sprite(0, 0, textureRegionPool.get(fileName),
					baseGameActivity.getVertexBufferObjectManager());
			s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			return s;
		}
		
		// サイズを自動的に取得する
		InputStream in = null;
		
		try {
			in = baseGameActivity.getResources().getAssets().open("gfx/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bitmap bm = BitmapFactory.decodeStream(in);
		// Bitmapのサイズを元に最小のべき乗を取得
		BitmapTextureAtlas bta = new BitmapTextureAtlas(baseGameActivity.getTextureManager(),
				getTwoPowerSize(bm.getWidth()),
				getTwoPowerSize(bm.getHeight()),
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		baseGameActivity.getEngine().getTextureManager().loadTexture(bta);
		
		ITextureRegion btr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bta,
				baseGameActivity, fileName, 0, 0);
		Sprite s = new Sprite(0, 0, btr, baseGameActivity.getVertexBufferObjectManager());
		s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		// 再生性を防ぐため追加
		textureRegionPool.put(fileName, btr);
		
		return s;
	}
	
	// パラパラアニメのようなSpriteを生成
	// 画像はひとつにする、マス数とともに引数
	public AnimatedSprite getAnimatedSprite(String fileName, int colum, int row){
		if(tiledTextureRegionPool.containsKey(fileName)){
			AnimatedSprite s = new AnimatedSprite(0, 0,
					tiledTextureRegionPool.get(fileName),
					baseGameActivity.getVertexBufferObjectManager());
			s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			return s;
		}
		
		InputStream in = null;
		
		try {
			in = baseGameActivity.getResources().getAssets().open("gfx/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bitmap bm = BitmapFactory.decodeStream(in);
		BitmapTextureAtlas bta = new BitmapTextureAtlas(baseGameActivity.getTextureManager(),
				getTwoPowerSize(bm.getWidth()),
				getTwoPowerSize(bm.getHeight()),
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		baseGameActivity.getTextureManager().loadTexture(bta);
		
		TiledTextureRegion ttr = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				bta, baseGameActivity, fileName, 0, 0, colum, row);
		// AnimatedSpriteを生成
		AnimatedSprite s = new AnimatedSprite(0, 0, ttr, baseGameActivity.getVertexBufferObjectManager());
		s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		tiledTextureRegionPool.put(fileName, ttr);
		
		return s;
	}
	
	// タップすると画像が切り替わるボタン機能を持つSpriteを生成
	public ButtonSprite getButtonSprite(String normal, String pressed){
		if(textureRegionPool.containsKey(normal) && 
				textureRegionPool.containsKey(pressed)){
			ButtonSprite s = new ButtonSprite(0, 0, textureRegionPool.get(normal), 
					textureRegionPool.get(pressed), baseGameActivity.getVertexBufferObjectManager());
			s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			return s;
		}
		
		InputStream in = null;
		try {
			in = baseGameActivity.getResources().getAssets().open("gtx/" + normal);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bitmap bm = BitmapFactory.decodeStream(in);
		// ボタン生成のためTexureRegion作成
		// Tiledではなく、Buildを再利用
		BuildableBitmapTextureAtlas bta = new BuildableBitmapTextureAtlas(baseGameActivity.getTextureManager(),
				getTwoPowerSize(bm.getWidth()), getTwoPowerSize(bm.getHeight() * 2));
		ITextureRegion trNormal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bta, baseGameActivity, normal);
		ITextureRegion trPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bta, baseGameActivity, pressed);
		
		try {
			bta.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			bta.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		textureRegionPool.put(normal, trNormal);
		textureRegionPool.put(pressed, trPressed);
		
		ButtonSprite s = new ButtonSprite(0, 0, trNormal, trPressed,
				baseGameActivity.getVertexBufferObjectManager());
		s.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		return s;
	}
	
	// poolを開放、削除する関数
	public void resetAllTexture(){
		// Activity.finish()だけだとシングルトンクラスがnullにならない
		self = null;
		textureRegionPool.clear();
		tiledTextureRegionPool.clear();
	}
	
	// 2のべき乗を求める
	public int getTwoPowerSize(float size){
		int value = (int) (size + 1);
		int pow2value = 64;
		while (pow2value < value) {
			pow2value *= 2;
		}
		return pow2value;
	}
}
