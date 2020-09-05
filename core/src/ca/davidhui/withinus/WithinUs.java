package ca.davidhui.withinus;

import ca.davidhui.withinus.screens.LevelScreen;
import ca.davidhui.withinus.screens.MainMenuScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WithinUs extends Game {
	public SpriteBatch batch;
	private AssetManager assetManager;
	public Skin skin;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader());
		assetManager.load("maps/testmap.tmx", TiledMap.class);
		assetManager.load("skins/shade/skin/uiskin.json", Skin.class);
		assetManager.load("images/ClickButtonTask.png", Texture.class);
		assetManager.finishLoading();
		this.skin = assetManager.get("skins/shade/skin/uiskin.json");
		this.setScreen(new LevelScreen(this));
//		this.setScreen(new MainMenuScreen(this));
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
}
