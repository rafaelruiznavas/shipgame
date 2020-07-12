package com.ruiznavas.shipg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.tools.FondoScroll;

public class MainMenuScreen implements Screen{
	private static final int BOTON_SALIR_ANCHO = 200;
	private static final int BOTON_SALIR_ALTO = 60;
	private static final int BOTON_SALIR_Y = 100;
	private static final int BOTON_PLAY_ANCHO = 200;
	private static final int BOTON_PLAY_ALTO = 60;
	private static final int BOTON_PLAY_Y = 200;
	private static final int ANCHO_LOGO = 400;
	private static final int ALTO_LOGO = 250;
	private static final int LOGO_Y = 300;

	final ShipGame game;
	
	Texture btnSalirActivo;
	Texture btnSalirInactivo;
	Texture btnPlayActivo;
	Texture btnPlayInactivo;
	Texture logo;
	
	public MainMenuScreen(final ShipGame game) {
		this.game = game;
		btnPlayActivo = new Texture("btnPlayActivo.png");
		btnPlayInactivo = new Texture("btnPlayInactivo.png");
		btnSalirActivo = new Texture("btnSalirActivo.png");
		btnSalirInactivo = new Texture("btnSalirInactivo.png");
		logo = new Texture("logo.png");
		
		game.fondoScroll.setVelocidadFijada(false);
		game.fondoScroll.setVelocidad(FondoScroll.VELOCIDAD_DEFECTO);
		
		final MainMenuScreen mainMenuScreen = this;
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// Boton salir
				int x = ShipGame.ANCHO/2 - BOTON_SALIR_ANCHO/2;
				if(game.camaraJuego.getInputEnMundoJuego().x < x + BOTON_SALIR_ANCHO &&
					game.camaraJuego.getInputEnMundoJuego().x > x &&
					ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y < BOTON_SALIR_Y + BOTON_SALIR_ALTO &&
					ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y > BOTON_SALIR_Y ) {
					mainMenuScreen.dispose();
					Gdx.app.exit();					
				}
				
				// Boton jugar
				if(game.camaraJuego.getInputEnMundoJuego().x < x + BOTON_PLAY_ANCHO &&
						game.camaraJuego.getInputEnMundoJuego().x > x &&
						ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y < BOTON_PLAY_Y + BOTON_PLAY_ALTO &&
						ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y > BOTON_PLAY_Y ) {
					mainMenuScreen.dispose();
					game.setScreen(new MainGameScreen(game));
				}
				return super.touchUp(screenX, screenY, pointer, button);
			}
		});
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.15f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		
		game.fondoScroll.actualizarYRender(delta, game.getBatch());
		
		int x = ShipGame.ANCHO/2 - BOTON_SALIR_ANCHO/2;
		if(game.camaraJuego.getInputEnMundoJuego().x < x + BOTON_SALIR_ANCHO &&
			game.camaraJuego.getInputEnMundoJuego().x > x &&
			ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y < BOTON_SALIR_Y + BOTON_SALIR_ALTO &&
			ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y > BOTON_SALIR_Y ) {
				game.getBatch().draw(btnSalirActivo, x, BOTON_SALIR_Y, BOTON_SALIR_ANCHO,BOTON_SALIR_ALTO);			
		} else {
			game.getBatch().draw(btnSalirInactivo, ShipGame.ANCHO/2 - BOTON_SALIR_ANCHO/2, BOTON_SALIR_Y, BOTON_SALIR_ANCHO,BOTON_SALIR_ALTO);
		}
		
		x = ShipGame.ANCHO/2 - BOTON_PLAY_ANCHO/2;
		if(Gdx.input.getX() < x + BOTON_PLAY_ANCHO && Gdx.input.getX() > x &&
			ShipGame.ALTO - Gdx.input.getY() < BOTON_PLAY_Y + BOTON_PLAY_ALTO && ShipGame.ALTO - Gdx.input.getY() > BOTON_PLAY_Y ) {
			game.getBatch().draw(btnPlayActivo, x, BOTON_PLAY_Y, BOTON_PLAY_ANCHO,BOTON_PLAY_ALTO);
		} else {
			game.getBatch().draw(btnPlayInactivo, ShipGame.ANCHO/2 - BOTON_PLAY_ANCHO/2, BOTON_PLAY_Y, BOTON_PLAY_ANCHO,BOTON_PLAY_ALTO);
		}
		
		game.getBatch().draw(logo, ShipGame.ANCHO/2 - ANCHO_LOGO/2, LOGO_Y, ANCHO_LOGO, ALTO_LOGO);

		game.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
}
