package com.ruiznavas.shipg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ruiznavas.shipg.screens.MainMenuScreen;

public class ShipGame extends Game {
	public static final int ANCHO = 480;
	public static final int ALTO = 720;
	
	SpriteBatch batch;	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public SpriteBatch getBatch() {
		return this.batch;
	}
}
