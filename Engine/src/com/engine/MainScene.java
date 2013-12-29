package com.engine;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class MainScene extends Scene{
	
	private BaseGameActivity baseGameActivity;
	
	public MainScene(BaseGameActivity baseGameActivity) {
		this.baseGameActivity = baseGameActivity;
		init();
	}
	
	public void init(){
		// 画像リソースが格納されている場所を指定。 Assetsフォルダ以下にあるフォルダを指定するときのみ使用
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// サイズ指定。480, 800が収まる２のべき乗
		BitmapTextureAtlas bta = new BitmapTextureAtlas(
				baseGameActivity.getTextureManager(),
				512, // 最小のべき乗(480なので)
				1024,// 最小のべき乗(800なので)
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// 範囲をメモリに上に読み込み
		baseGameActivity.getTextureManager().loadTexture(bta);
		// メモリ上に読み込んだ範囲に画像を読み込み
		ITextureRegion btr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bta,
				baseGameActivity, "bg.png", 0, 0);
		// Sprite(画像)をインスタンス化
		Sprite bg = new Sprite(0, 0, btr, baseGameActivity.getVertexBufferObjectManager());
		// Spriteのアルファ値取り扱いを設定
		bg.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//画面の配置
		attachChild(bg);
	}
}
