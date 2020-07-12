package com.ruiznavas.shipg.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class CamaraJuego {
	private OrthographicCamera camera;
	private StretchViewport viewport;

	public CamaraJuego(int ancho, int alto) {
		camera = new OrthographicCamera();
		viewport = new StretchViewport(ancho, alto, camera);
		viewport.apply();
		camera.position.set(ancho/2,alto/2,0);
		camera.update();
	}
	
	public Matrix4 combined() {
		return camera.combined;
	}
	
	public void update(int ancho, int alto) {
		viewport.update(ancho, alto);
	}
	
	public Vector2 getInputEnMundoJuego() {
		Vector3 entradaPantalla = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
		Vector3 desproyectado = camera.unproject(entradaPantalla);
		
		return new Vector2(desproyectado.x, desproyectado.y);
	}
	
}

