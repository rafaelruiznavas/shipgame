package com.ruiznavas.shipg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ruiznavas.shipg.ShipGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = ShipGame.ANCHO;
		config.height = ShipGame.ALTO;
		config.resizable = true;
		new LwjglApplication(new ShipGame(), config);
	}
}
