package com.ruiznavas.shipg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.Alignment;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.tools.FondoScroll;

public class GameOverScreen implements Screen{
	private static final int ANCHO_BANNER = 350;
	private static final int ALTO_BANNER = 100;
	
	ShipGame game;
	int puntuacion, mayorPuntuacion;
	
	Texture bannerGameOver;
	BitmapFont fuentePuntuacion;

	public GameOverScreen(ShipGame game, int puntuacion) {
		this.game = game;
		this.puntuacion = puntuacion;
		
		// Obtenemos la puntuacion mas alta del fichero grabado
		Preferences prefs = Gdx.app.getPreferences("shipgame");
		this.mayorPuntuacion = prefs.getInteger("mayorpuntuacion", 0);
		
		// Comprobamos si la puntuacion es mas alta
		if(puntuacion > mayorPuntuacion) {
			prefs.putInteger("mayorpuntuacion", puntuacion);
			prefs.flush();
		}
		
		// Cargamos las texturas y las fuentes
		bannerGameOver = new Texture("gameover.png");
		fuentePuntuacion = new BitmapFont(Gdx.files.internal("fonts/hope-gold.fnt"));
		
		game.fondoScroll.setVelocidadFijada(true);
		game.fondoScroll.setVelocidad(FondoScroll.VELOCIDAD_DEFECTO);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		
		game.fondoScroll.actualizarYRender(delta, game.getBatch());
		
		game.getBatch().draw(bannerGameOver, ShipGame.ANCHO/2 - ANCHO_BANNER / 2, 
				ShipGame.ALTO - ALTO_BANNER - 15, ANCHO_BANNER, ALTO_BANNER);
		GlyphLayout capaPuntuacion = new GlyphLayout(fuentePuntuacion, "Score: \n" + puntuacion, Color.WHITE, 0, Align.left, false);
		GlyphLayout capaPuntuacionAlta = new GlyphLayout(fuentePuntuacion, "High Score: \n" + mayorPuntuacion, Color.WHITE, 0, Align.left, false);
		fuentePuntuacion.draw(game.getBatch(), capaPuntuacion, 
				ShipGame.ANCHO/2 - capaPuntuacion.width/2,
				ShipGame.ALTO - ALTO_BANNER - 15 * 2);
		fuentePuntuacion.draw(game.getBatch(), capaPuntuacionAlta, 
				ShipGame.ANCHO/2 - capaPuntuacionAlta.width/2,
				ShipGame.ALTO - ALTO_BANNER - 15 * 6);
		GlyphLayout capaIntentaDenuevo = new GlyphLayout(fuentePuntuacion, "Try Again");
		GlyphLayout capaMenuPrincipal = new GlyphLayout(fuentePuntuacion, "Main Menu");

		float intentaX = ShipGame.ANCHO / 2 - capaIntentaDenuevo.width / 2;
		float intentaY = ShipGame.ALTO / 2 - capaIntentaDenuevo.height / 2;
		
		float menuX = ShipGame.ANCHO / 2 - capaMenuPrincipal.width / 2;
		float menuY = ShipGame.ALTO / 2 - capaMenuPrincipal.height / 2 - capaIntentaDenuevo.height - 15;
		
		float touchX = game.camaraJuego.getInputEnMundoJuego().x, touchY = ShipGame.ALTO - game.camaraJuego.getInputEnMundoJuego().y;
		
		// Se ha pulsado intentar de nuevo o menu principal
		if(Gdx.input.isTouched()) {
			// Intenta de nuevo
			if(touchX > intentaX && touchX < intentaX + capaIntentaDenuevo.width &&
					touchY > intentaY - capaIntentaDenuevo.height && touchY < intentaY) {
				this.dispose();
				game.getBatch().end();
				game.setScreen(new MainGameScreen(game));
				return;
			}
			
			// Menu Principal
			if(touchX > menuX && touchX < menuX + capaMenuPrincipal.width &&
					touchY > menuY - capaMenuPrincipal.height && touchY < menuY) {
				this.dispose();
				game.getBatch().end();
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
		// Dibujamos los botones
		fuentePuntuacion.draw(game.getBatch(), capaIntentaDenuevo, intentaX,intentaY);
		fuentePuntuacion.draw(game.getBatch(), capaMenuPrincipal, menuX, menuY);
		
		
		game.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

