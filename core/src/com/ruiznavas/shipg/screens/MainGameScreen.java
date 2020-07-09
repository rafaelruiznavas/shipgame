package com.ruiznavas.shipg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ruiznavas.shipg.ShipGame;

public class MainGameScreen implements Screen{
	public static final float SPEED = 120;
	public static final int ANCHO_PIXEL_SHIP = 16;
	public static final int ALTO_PIXEL_SHIP = 24;
	public static final int ANCHO_SHIP = ANCHO_PIXEL_SHIP * 3;
	public static final int ALTO_SHIP = ALTO_PIXEL_SHIP * 3;
	public static final float VELOCIDAD_ANIMACION_SHIP = 0.5f;
	
	Animation[] rolls; 
	
	float x,y;
	int roll;
	float stateTime;
	
	ShipGame game;
	
	public MainGameScreen(ShipGame game) {
		this.game = game;
		y = 15;
		x = ShipGame.ANCHO/2 - ANCHO_SHIP / 2;
		
		roll = 1;
		rolls = new Animation[5];
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), ANCHO_PIXEL_SHIP,ALTO_PIXEL_SHIP);
		rolls[roll] = new Animation<TextureRegion>(VELOCIDAD_ANIMACION_SHIP, rollSpriteSheet[0][2],rollSpriteSheet[1][2]);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		

		if(Gdx.input.isKeyPressed(Keys.UP)) {
			y+= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			y-= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			x-= SPEED * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x+= SPEED * delta;
		}
		
		stateTime += delta;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
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
