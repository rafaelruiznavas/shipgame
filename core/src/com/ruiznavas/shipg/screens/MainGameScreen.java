package com.ruiznavas.shipg.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.entidades.Disparo;

public class MainGameScreen implements Screen{
	public static final float SPEED = 300;
	public static final int ANCHO_PIXEL_SHIP = 16;
	public static final int ALTO_PIXEL_SHIP = 24;
	public static final int ANCHO_SHIP = ANCHO_PIXEL_SHIP * 3;
	public static final int ALTO_SHIP = ALTO_PIXEL_SHIP * 3;
	public static final float VELOCIDAD_ANIMACION_SHIP = 0.5f;
	public static final float TIMER_CAMBIO_ROLL = 0.25f;
	public static final float TIEMPO_ESPERA_DISPARO = 0.3f;
	
	Animation[] rolls; 
	
	float x,y;
	int roll;
	float rollTimer;
	float stateTime;
	float shootTimer;
	
	ShipGame game;
	
	ArrayList<Disparo> disparos;
	
	public MainGameScreen(ShipGame game) {
		this.game = game;
		y = 15;
		x = ShipGame.ANCHO/2 - ANCHO_SHIP / 2;
		disparos = new ArrayList<Disparo>();
		
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
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		shootTimer += delta;
		// Disparo nave
		if(Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= TIEMPO_ESPERA_DISPARO ) {
			shootTimer = 0;
			int offset = 4;
			if(roll == 1 || roll == 3)
				offset = 8;
			
			if(roll == 0 || roll == 4)
				offset = 16;
			
			disparos.add(new Disparo(x + offset));
			disparos.add(new Disparo(x + ANCHO_SHIP - offset));
		}
		// Actualizamos los disparos
		ArrayList<Disparo> disparosEliminar = new ArrayList<Disparo>();
		for(Disparo disparo : disparos) {
			disparo.update(delta);
			if(disparo.eliminar)
				disparosEliminar.remove(disparo);
		}
		disparos.removeAll(disparosEliminar);
		
		// Movimiento nave
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			y+= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			y-= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			x-= SPEED * delta;
			if(x < 0) x = 0;
			
			// Actualiza roll si se pulso el boton
			if(Gdx.input.isKeyJustPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll > 0) {
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
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x+= SPEED * delta;
			if(x + ANCHO_SHIP > Gdx.graphics.getWidth())
				x = Gdx.graphics.getWidth() - ANCHO_SHIP;
			
			// Actualiza roll si se pulso el boton
			if(Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll > 0) {
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
		
		stateTime += delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		for(Disparo disparo : disparos) {
			disparo.render(game.getBatch());
		}
		game.getBatch().draw((TextureRegion)rolls[roll].getKeyFrame(stateTime,true), x, y, ANCHO_SHIP, ALTO_SHIP); 
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
		
	}

}
