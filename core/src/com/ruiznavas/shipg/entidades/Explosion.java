package com.ruiznavas.shipg.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
	public static final float LONGITUD_FRAME = 0.08f;
	public static final int OFFSET = 8;
	public static final int TAM = 64;
	public static final int TAM_IMAGEN = 16;
	
	private static Animation anim = null;
	float x,y;
	float tiempoEstado;
	
	public boolean eliminar = false;
	
	public Explosion (float x, float y) {
		this.x = x - OFFSET;
		this.y = y - OFFSET;
		tiempoEstado = 0;
		
		if(anim == null) {
			anim = new Animation<TextureRegion>(LONGITUD_FRAME, TextureRegion.split(new Texture("explosion.png"), TAM_IMAGEN, TAM_IMAGEN)[0]);
		}
	}
	
	public void update(float delta) {
		tiempoEstado += delta;

		if(anim.isAnimationFinished(tiempoEstado)) {
			eliminar = true;
		}
	}

	public void render(SpriteBatch batch) {
		batch.draw((TextureRegion) anim.getKeyFrame(tiempoEstado), x, y, TAM, TAM);
	}
}

