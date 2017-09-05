package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import nitrogene.objecttree.PhysicalObject;

public class Sector {
	public static float width = 100;
	private ArrayList<PhysicalObject> objects = new ArrayList<PhysicalObject>();
	public Sector() {
		
	}
	public void addObject(PhysicalObject p) {
		objects.add(p);
	}
	public void removeObject(PhysicalObject p) {
		objects.remove(p);
	}
	public ArrayList<PhysicalObject> getObjects() {
		return objects;
	}

}
