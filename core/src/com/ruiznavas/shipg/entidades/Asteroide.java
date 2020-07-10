package com.ruiznavas.shipg.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Asteroide {
	public static final int VELOCIDAD = 500;
	public static final int ANCHO = 16;
	public static final int ALTO = 16;
	private static Texture textura;
	
	float x,y;
	
	public boolean eliminar = false;
	
	public Asteroide(float x) {
		this.x = x;
		this.y = Gdx.graphics.getHeight();
		
		if(textura == null)
			textura = new Texture("asteroide.png");
	}
	
	public void update(float deltaTime) {
		y -= VELOCIDAD * deltaTime;
		
		if(y < -ALTO)
			eliminar= true;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(textura, x, y);
	}
}
