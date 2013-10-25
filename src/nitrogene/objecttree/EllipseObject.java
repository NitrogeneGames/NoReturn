package nitrogene.objecttree;

import java.awt.Shape;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.geom.Ellipse;

public class EllipseObject extends PhysicalObject{
	private Ellipse boundbox;

	public EllipseObject(float x, float y, float width, float height, ArenaMap map) {
		super(x, y, width, height, map);
		//change to two radii
		boundbox = new Ellipse(x, y, width, height);
	}

}
