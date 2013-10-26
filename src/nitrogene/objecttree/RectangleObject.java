package nitrogene.objecttree;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class RectangleObject extends PhysicalObject{
	
	public RectangleObject(Image img, float scale, ArenaMap map){
		super(0f,0f,img, scale, map);
		boundbox = new Rectangle(0,0,0,0);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float width, float height, Image img, float scale, ArenaMap map){
		super(0f,0f, img, scale, map);
		boundbox = new Rectangle(0,0,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float x, float y, float width, float height, Image img, float scale, ArenaMap map){
		super(x,y,img, scale, map);
		boundbox = new Rectangle(x,y,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
}
