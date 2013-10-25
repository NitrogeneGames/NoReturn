package nitrogene.objecttree;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.geom.Circle;

public class CircleObject extends PhysicalObject{
	private Circle boundbox;

	public CircleObject(float x, float y, float radius, ArenaMap map) {
		super(x, y, radius*2, radius*2, map);
		boundbox = new Circle(x, y, radius);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
}
