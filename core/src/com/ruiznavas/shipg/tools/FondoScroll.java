package com.ruiznavas.shipg.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoScroll {
	public static final int VELOCIDAD_DEFECTO = 80;
	public static final int ACELERACION = 50;
	public static final int ACELERACION_ALCANZAR_OBJETIVO = 200;
	
	Texture image;
	float y1, y2;
	int velocidad; // En pixeles / segundo
	int velocidadObjetivo;
	float escalaImagen;
	boolean velocidadFijada;
	
	public FondoScroll() {
		image = new Texture("stars.png");
		
		velocidad = 0;
		velocidadObjetivo = VELOCIDAD_DEFECTO;
		escalaImagen = 1;
		velocidadFijada = true;
		y1 = 0;
		y2 = image.getHeight() * escalaImagen;
	}
	
	public void actualizarYRender(float deltaTime, SpriteBatch batch) {
		// Ajustamos la velocidad
		if(velocidad < velocidadObjetivo) {
			velocidad += ACELERACION_ALCANZAR_OBJETIVO * deltaTime;
			
			if(velocidad > velocidadObjetivo)
				velocidad = velocidadObjetivo;
		} else if(velocidad > velocidadObjetivo) {
			velocidad -= ACELERACION_ALCANZAR_OBJETIVO * deltaTime;
			
			if(velocidad < velocidadObjetivo)
				velocidad = velocidadObjetivo;
		}
		
		if(!velocidadFijada)
			velocidad += ACELERACION * deltaTime;
		
		y1 -= velocidad * deltaTime;
		y2 -= velocidad * deltaTime; 
		
		// Si la imagen alcanza la parte inferior de la pantalla y no es visible, la ponemos arriba de nuevo
		if(y1 + image.getHeight() * escalaImagen <= 0) {
			y1 = y2 + image.getHeight() * escalaImagen;
		}
		
		if(y2 + image.getHeight() * escalaImagen <= 0) {
			y2 = y1 + image.getHeight() * escalaImagen;
		}
		
		// Renderizamos
		batch.draw(image, 0, y1, Gdx.graphics.getWidth(), image.getHeight() * escalaImagen); 
		batch.draw(image, 0, y2, Gdx.graphics.getWidth(), image.getHeight() * escalaImagen);
	}
	
	public void redimensionar(int ancho, int alto) {
		escalaImagen = (float)ancho / (float)image.getWidth();
	}
	
	public void setVelocidad(int velocidadObjetivo) {
		this.velocidadObjetivo = velocidadObjetivo;
	}
	
	public void setVelocidadFijada(boolean velocidadFijada) {
		this.velocidadFijada = velocidadFijada;
	}
}




