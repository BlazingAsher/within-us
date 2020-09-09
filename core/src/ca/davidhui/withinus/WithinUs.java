package ca.davidhui.withinus;

import ca.davidhui.withinus.enums.GameResult;
import ca.davidhui.withinus.enums.GameScreenType;
import ca.davidhui.withinus.screens.MainMenuScreen;
import ca.davidhui.withinus.screens.VictoryDefeatScreen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WithinUs extends Game {
	public SpriteBatch batch;
	private AssetManager assetManager;
	public Skin skin;
	public Screen currentScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());
		assetManager.load("maps/testmap.tmx", TiledMap.class);
		assetManager.load("skins/shade/skin/uiskin.json", Skin.class);
		assetManager.load("images/ClickButtonTask.png", Texture.class);
		assetManager.load("images/graphics/VentArrow.png", Texture.class);
		assetManager.finishLoading();
		this.skin = assetManager.get("skins/shade/skin/uiskin.json");
//		this.setScreen(new LevelScreen(this));
		this.currentScreen = new MainMenuScreen(this);
		this.setScreen(currentScreen);
	}

	public AssetManager getAssetManager(){
		return this.assetManager;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void changeScreen(GameScreenType newScreen) {
		currentScreen.dispose();
		if(newScreen == GameScreenType.DEFEAT || newScreen == GameScreenType.VICTORY){
			this.currentScreen = new VictoryDefeatScreen(this, GameResult.valueOf(newScreen.toString()));
//			Gdx.gl.glClearColor(0,0,0, 1);
//			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			this.setScreen(this.currentScreen);
		}
	}

}
