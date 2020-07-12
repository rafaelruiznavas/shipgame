package com.ruiznavas.shipg.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ruiznavas.shipg.ShipGame;
import com.ruiznavas.shipg.tools.RectColision;

public class Disparo {
	public static final int VELOCIDAD = 500;
	public static final int DEFAULT_Y = 40;
	public static final int ANCHO = 3;
	public static final int ALTO = 12;
	
	private static Texture textura;
	
	float x,y;
	RectColision rect;
	public boolean eliminar = false;
	
	public Disparo(float x, float y) {
		this.x = x;
		this.y = y;
		
		this.rect = new RectColision(x, x, ANCHO, ALTO);
		
		if(textura == null)
			textura = new Texture("smallshot.png");
	}
	
	public void update(float deltaTime) {
		y += VELOCIDAD * deltaTime;
		
		if(y > ShipGame.ALTO)
			eliminar= true;
		
		rect.mover(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(textura, x, y);
	}
	
	public RectColision getRectColision() {
		return rect;
	}
}

