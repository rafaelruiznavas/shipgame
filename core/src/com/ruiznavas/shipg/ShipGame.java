package com.ruiznavas.shipg;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ruiznavas.shipg.screens.MainMenuScreen;
import com.ruiznavas.shipg.tools.CamaraJuego;
import com.ruiznavas.shipg.tools.FondoScroll;

public class ShipGame extends Game {
	public static final int ANCHO = 480;
	public static final int ALTO = 720;
	public static boolean ES_MOVIL = false;
	
	SpriteBatch batch;
	public FondoScroll fondoScroll;
	public CamaraJuego camaraJuego;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camaraJuego = new CamaraJuego(ANCHO, ALTO);
		
		if(Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS) {
			ES_MOVIL = true;
		}
		
		fondoScroll = new FondoScroll();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camaraJuego.combined());
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		this.fondoScroll.redimensionar(width, height);
		camaraJuego.update(width, height);
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
