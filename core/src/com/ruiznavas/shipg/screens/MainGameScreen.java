package com.ruiznavas.shipg.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.entidades.Asteroide;
import com.ruiznavas.shipg.entidades.Disparo;
import com.ruiznavas.shipg.entidades.Explosion;
import com.ruiznavas.shipg.tools.FondoScroll;
import com.ruiznavas.shipg.tools.RectColision;

public class MainGameScreen implements Screen{
	public static final float SPEED = 300;
	public static final int ANCHO_PIXEL_SHIP = 16;
	public static final int ALTO_PIXEL_SHIP = 24;
	public static final int ANCHO_SHIP = ANCHO_PIXEL_SHIP * 3;
	public static final int ALTO_SHIP = ALTO_PIXEL_SHIP * 3;
	public static final float VELOCIDAD_ANIMACION_SHIP = 0.5f;
	public static final float TIMER_CAMBIO_ROLL = 0.25f;
	public static final float TIEMPO_ESPERA_DISPARO = 0.3f;
	public static final float TIEMPO_MINIMO_SPAWN_ASTEROIDE = 0.3f;
	public static final float TIEMPO_MAXIMO_SPAWN_ASTEROIDE = 3.0f;
	
	Animation[] rolls; 
	
	float x,y;
	int roll;
	float rollTimer;
	float stateTime;
	float shootTimer;
	float timerSpawnAsteroide;
	
	Random random;
	
	ShipGame game;
	
	ArrayList<Disparo> disparos;
	ArrayList<Asteroide> asteroides;
	ArrayList<Explosion> explosiones;
	
	Texture vacia;
	Texture controles;
	
	BitmapFont fuentePuntuacion;
	RectColision rectJugador;
	int puntuacion;
	float salud = 1;
	
	public MainGameScreen(ShipGame game) {
		this.game = game;
		y = 15;
		x = ShipGame.ANCHO/2 - ANCHO_SHIP / 2;
		disparos = new ArrayList<Disparo>();
		asteroides = new ArrayList<Asteroide>();
		explosiones = new ArrayList<Explosion>();
		fuentePuntuacion = new BitmapFont(Gdx.files.internal("fonts/hope-gold.fnt"));
		puntuacion = 0;
		vacia = new Texture("blank.png");
		rectJugador = new RectColision(0,0,ANCHO_SHIP,ALTO_SHIP);
		
		if(ShipGame.ES_MOVIL)
			controles = new Texture("controls.png");
		
		random = new Random();
		timerSpawnAsteroide = random.nextFloat() * (TIEMPO_MAXIMO_SPAWN_ASTEROIDE - TIEMPO_MINIMO_SPAWN_ASTEROIDE) + TIEMPO_MINIMO_SPAWN_ASTEROIDE;
		
		roll = 2;
		rollTimer = 0;
		shootTimer = 0;
		rolls = new Animation[5];
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), ANCHO_PIXEL_SHIP,ALTO_PIXEL_SHIP);
		rolls[0] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][0],rollSpriteSheet[1][0]);
		rolls[1] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][1],rollSpriteSheet[1][1]);
		rolls[2] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][2],rollSpriteSheet[1][2]);
		rolls[3] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][3],rollSpriteSheet[1][3]);
		rolls[4] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][4],rollSpriteSheet[1][4]);
		
		game.fondoScroll.setVelocidadFijada(true);
		game.fondoScroll.setVelocidad(FondoScroll.VELOCIDAD_DEFECTO);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		shootTimer += delta;
		// Disparo nave
		if((esDerecha() || esIzquierda()) && shootTimer >= TIEMPO_ESPERA_DISPARO ) {
			shootTimer = 0;
			int offset = 4;
			if(roll == 1 || roll == 3)
				offset = 8;
			
			if(roll == 0 || roll == 4)
				offset = 16;
			
			disparos.add(new Disparo(x + offset, y));
			disparos.add(new Disparo(x + ANCHO_SHIP - offset, y));		}
		
		// Codigo spawn asteroides
		timerSpawnAsteroide -= delta;
		if(timerSpawnAsteroide <= 0) {
			timerSpawnAsteroide = random.nextFloat() * (TIEMPO_MAXIMO_SPAWN_ASTEROIDE - TIEMPO_MINIMO_SPAWN_ASTEROIDE) + TIEMPO_MINIMO_SPAWN_ASTEROIDE;
			asteroides.add(new Asteroide(random.nextInt(ShipGame.ANCHO - Asteroide.ANCHO)));
		}
		
		// Actualizamos los asteroides
		ArrayList<Asteroide> asteroidesEliminar = new ArrayList<Asteroide>();
		for(Asteroide asteroide : asteroides) {
			asteroide.update(delta);;
			if(asteroide.eliminar)
				asteroidesEliminar.add(asteroide);
		}
		
		
		// Actualizamos los disparos
		ArrayList<Disparo> disparosEliminar = new ArrayList<Disparo>();
		for(Disparo disparo : disparos) {
			disparo.update(delta);
			if(disparo.eliminar)
				disparosEliminar.add(disparo);
		}
		
		// Actualizamos las explosiones
		ArrayList<Explosion> explosionesEliminar = new ArrayList<Explosion>();
		for(Explosion explosion : explosiones) {
			explosion.update(delta);
			if(explosion.eliminar)
				explosionesEliminar.add(explosion);
		}
		
		// Movimiento nave
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			y+= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			y-= SPEED * delta;
		}
		if(esIzquierda()) {
			x-= SPEED * delta;
			if(x < 0) x = 0;
			
			// Actualiza roll si se pulso el boton
			if(esSoloIzquierda() && !esDerecha() && roll > 0) {
				rollTimer = 0;
				roll--;
			}
			rollTimer -= Gdx.graphics.getDeltaTime();
			if(Math.abs(rollTimer) > TIMER_CAMBIO_ROLL && roll > 0) {
				rollTimer = 0;
				roll--;
			}
		} else {
			if(roll < 2) {
				rollTimer += Gdx.graphics.getDeltaTime();
				if(Math.abs(rollTimer) > TIMER_CAMBIO_ROLL && roll < 4 ) {
					rollTimer = 0;
					roll++;
				}
			}
		}
		
		if(esDerecha()) {
			x+= SPEED * delta;
			if(x + ANCHO_SHIP > ShipGame.ANCHO)
				x = ShipGame.ANCHO - ANCHO_SHIP;
			
			// Actualiza roll si se pulso el boton
			if(esSoloDerecha() && !esIzquierda() && roll > 0) {
				rollTimer = 0;
				roll--;
			}
						
			rollTimer += Gdx.graphics.getDeltaTime();
			if(Math.abs(rollTimer) > TIMER_CAMBIO_ROLL && roll < 4) {
				rollTimer -= TIMER_CAMBIO_ROLL;
				roll++;
			}
		}else {
			if(roll > 2) {
				rollTimer -= Gdx.graphics.getDeltaTime();
				if(Math.abs(rollTimer) > TIMER_CAMBIO_ROLL && roll > 0) {
					rollTimer -= TIMER_CAMBIO_ROLL;
					roll--;
				}
			}
		}
		
		// Actualizamos el rectangulo del jugador despues del movimiento
		rectJugador.mover(x, y);		
		
		// Despues de todas las actualizaciones, comprobamos las colisiones
		for(Disparo disparo : disparos) {
			for(Asteroide asteroid : asteroides) {
				if(disparo.getRectColision().colisionaCon(asteroid.getRectColision())) {
					disparosEliminar.add(disparo);
					asteroidesEliminar.add(asteroid);
					puntuacion += 100;
					explosiones.add(new Explosion(asteroid.getX(), asteroid.getY()));
				}
			}
		}
		
		for(Asteroide asteroide : asteroides) {
			if(asteroide.getRectColision().colisionaCon(rectJugador)) {
				asteroidesEliminar.add(asteroide);
				salud -= 0.1f;
				
				if(salud <= 0) {
					this.dispose();
					game.setScreen(new GameOverScreen(game, puntuacion));
					return;
				}
			}
		}
		
		asteroides.removeAll(asteroidesEliminar);
		disparos.removeAll(disparosEliminar);
		explosiones.removeAll(explosionesEliminar);
		
		stateTime += delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		
		game.fondoScroll.actualizarYRender(delta, game.getBatch());
		
		GlyphLayout capaPuntuacion = new GlyphLayout(fuentePuntuacion, "SCORE: " + puntuacion);
		fuentePuntuacion.draw(game.getBatch(), capaPuntuacion, ShipGame.ANCHO/2 - capaPuntuacion.width/2, 
				ShipGame.ALTO -capaPuntuacion.height - 10);
		
		for(Disparo disparo : disparos) {
			disparo.render(game.getBatch());
		}
		for(Asteroide asteroide : asteroides) {
			asteroide.render(game.getBatch());
		}
		for(Explosion explosion : explosiones) {
			explosion.render(game.getBatch()); 
		}
		
		if(salud > 0.6f)
			game.getBatch().setColor(Color.GREEN);
		else if(salud > 0.2f)
			game.getBatch().setColor(Color.ORANGE);
		else
			game.getBatch().setColor(Color.RED);
		
		game.getBatch().draw(vacia, 0,0, ShipGame.ANCHO * salud, 5);
		game.getBatch().setColor(Color.WHITE);
		
		game.getBatch().draw((TextureRegion)rolls[roll].getKeyFrame(stateTime,true), x, y, ANCHO_SHIP, ALTO_SHIP);
		
		if(ShipGame.ES_MOVIL) {
			// Dibujamos el control izquierda
			game.getBatch().setColor(Color.RED);
			game.getBatch().draw(controles, 0, 0, ShipGame.ANCHO/2, ShipGame.ALTO,0,0,ShipGame.ANCHO/2,ShipGame.ALTO, false, false);
			
			game.getBatch().setColor(Color.BLUE);
			game.getBatch().draw(controles, ShipGame.ANCHO/2, 0, ShipGame.ANCHO/2, ShipGame.ALTO,0,0,ShipGame.ANCHO/2,ShipGame.ALTO, false, false);
			
			game.getBatch().setColor(Color.WHITE);
		}
		
		game.getBatch().end();
	}
	
	private boolean esDerecha() {
		return Gdx.input.isKeyPressed(Keys.RIGHT) || (Gdx.input.isTouched() && game.camaraJuego.getInputEnMundoJuego().x >= ShipGame.ANCHO/2);
	}
	
	private boolean esIzquierda() {
		return Gdx.input.isKeyPressed(Keys.LEFT) || (Gdx.input.isTouched() && game.camaraJuego.getInputEnMundoJuego().x < ShipGame.ANCHO/2);
	}
	
	private boolean esSoloDerecha() {
		return Gdx.input.isKeyJustPressed(Keys.RIGHT) || (Gdx.input.justTouched() && game.camaraJuego.getInputEnMundoJuego().x >= ShipGame.ANCHO/2);
	}
	
	private boolean esSoloIzquierda() {
		return Gdx.input.isKeyJustPressed(Keys.LEFT) || (Gdx.input.justTouched() && game.camaraJuego.getInputEnMundoJuego().x < ShipGame.ANCHO/2);
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
		
	}

}


