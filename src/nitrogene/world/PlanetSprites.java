package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import nitrogene.core.AssetManager;

public class PlanetSprites {
	public static ArrayList<Image> sprites = new ArrayList<Image>();
	public static void load() {
		sprites.add((Image) AssetManager.get().get("sun1"));
		sprites.add((Image) AssetManager.get().get("volcanicplanet"));
		sprites.add((Image) AssetManager.get().get("rockyplanet"));
	}
}
