package nitrogene.objecttree;

import java.awt.Shape;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

public class EllipseObject extends PhysicalObject{

	public EllipseObject(float x, float y, float width, float height, Image img, float scale, ArenaMap map) {
		super(width, height, img, scale, map);
		//change to two radii
		boundbox = new Ellipse(x, y, width, height);
	}

}
