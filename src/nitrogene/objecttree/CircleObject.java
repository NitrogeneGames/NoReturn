package nitrogene.objecttree;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

public class CircleObject extends PhysicalObject{

	public CircleObject(float x, float y, float radius, Image img, float scale, ArenaMap map) {
		super(radius*2, radius*2, img, scale, map);
		boundbox = new Circle(x, y, radius);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
}
