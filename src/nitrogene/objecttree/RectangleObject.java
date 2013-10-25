package nitrogene.objecttree;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.geom.Rectangle;

public class RectangleObject extends PhysicalObject{
	
	public RectangleObject(ArenaMap map){
		super(0f,0f,0f,0f, map);
		boundbox = new Rectangle(0,0,0,0);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float width, float height, ArenaMap map){
		super(0f,0f,width,height, map);
		boundbox = new Rectangle(0,0,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float x, float y, float width, float height, ArenaMap map){
		super(x,y,width,height, map);
		boundbox = new Rectangle(x,y,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
}
