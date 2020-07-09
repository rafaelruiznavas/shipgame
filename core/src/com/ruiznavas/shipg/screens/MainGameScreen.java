package com.ruiznavas.shipg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ruiznavas.shipg.ShipGame;

public class MainGameScreen implements Screen{
	public static final float SPEED = 120;
	Texture img;
	float x,y;
	
	ShipGame game;
	
	public MainGameScreen(ShipGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		img = new Texture("badlogic.jpg");
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
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		game.getBatch().draw(img, x, y); 
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
