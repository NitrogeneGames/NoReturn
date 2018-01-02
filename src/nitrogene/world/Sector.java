package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import nitrogene.core.GameObject;

public class Sector {
	public static float width = 100;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public Sector() {
		
	}
	public void addObject(GameObject p) {
		objects.add(p);
	}
	public void removeObject(GameObject p) {
		objects.remove(p);
	}
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

}
