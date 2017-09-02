package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import nitrogene.core.AssetManager;

public class PlanetSprites {
	public static ArrayList<String> sprites = new ArrayList<String>();
	public static void load() {
		sprites.add("sun1");
		sprites.add("volcanicplanet");
		sprites.add("rockyplanet");
	}
}
