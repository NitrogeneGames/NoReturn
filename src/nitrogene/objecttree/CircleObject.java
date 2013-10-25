package nitrogene.objecttree;

import org.newdawn.slick.geom.Circle;

public class CircleObject extends PhysicalObject{
	private Circle boundbox;

	public CircleObject(float x, float y, float radius) {
		super(x, y, radius*2, radius*2);
		boundbox = new Circle(x, y, radius);
	}
}
