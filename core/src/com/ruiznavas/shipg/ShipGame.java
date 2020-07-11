package com.ruiznavas.shipg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ruiznavas.shipg.screens.MainMenuScreen;
import com.ruiznavas.shipg.tools.FondoScroll;

public class ShipGame extends Game {
	public static final int ANCHO = 480;
	public static final int ALTO = 720;
	
	SpriteBatch batch;
	public FondoScroll fondoScroll;
	
	private OrthographicCamera camera;
	private StretchViewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fondoScroll = new FondoScroll();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		this.fondoScroll.redimensionar(width, height);
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public SpriteBatch getBatch() {
		return this.batch;
	}
}
