package com.ruiznavas.shipg.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.tools.RectColision;

public class Asteroide {
	public static final int VELOCIDAD = 500;
	public static final int ANCHO = 32;
	public static final int ALTO = 32;
	private static Texture textura;
	
	float x,y;
	RectColision rect;
	public boolean eliminar = false;
	
	public Asteroide(float x) {
		this.x = x;
		this.y = ShipGame.ALTO;
		
		this.rect = new RectColision(x, x, ANCHO, ALTO);
		
		if(textura == null)
			textura = new Texture("asteroide.png");
	}
	
	public void update(float deltaTime) {
		y -= VELOCIDAD * deltaTime;
		
		if(y < -ALTO)
			eliminar= true;
		
		rect.mover(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(textura, x, y, ANCHO, ALTO);
	}
	
	public RectColision getRectColision() {
		return this.rect;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}

