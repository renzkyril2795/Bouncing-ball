package com.renz.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.renz.game.bounce_now;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = bounce_now.WIDTH;
		config.height = bounce_now.HEIGHT;
		config.title = bounce_now.TITLE;
		new LwjglApplication(new bounce_now(), config);
	}
}
