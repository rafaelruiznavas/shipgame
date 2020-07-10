package com.ruiznavas.shipg.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Disparo {
	public static final int VELOCIDAD = 500;
	public static final int DEFAULT_Y = 40;
	private static Texture textura;
	
	float x,y;
	
	public boolean eliminar = false;
	
	public Disparo(float x) {
		this.x = x;
		this.y = DEFAULT_Y;
		
		if(textura == null)
			textura = new Texture("smallshot.png");
	}
	
	public void update(float deltaTime) {
		y += VELOCIDAD * deltaTime;
		
		if(y > Gdx.graphics.getHeight())
			eliminar= true;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(textura, x, y);
	}
}

