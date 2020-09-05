package ca.davidhui.withinus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ca.davidhui.withinus.WithinUs;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Within Us";
		config.width = 1280;
		config.height = 720;
//		config.useHDPI = true;
		new LwjglApplication(new WithinUs(), config);
	}
}
