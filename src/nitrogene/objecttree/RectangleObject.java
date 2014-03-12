package nitrogene.objecttree;

import java.util.ArrayList;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

public class RectangleObject extends PhysicalObject{
	
	public RectangleObject(Image img, float scale, ArenaMap map){
		super(0f,0f,img, scale);
		registerMovement(map);
		boundbox = new Rectangle(0,0,0,0);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float width, float height, Image img, float scale, ArenaMap map){
		super(0f,0f, img, scale);
		boundbox = new Rectangle(0,0,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float x, float y, float width, float height, Image img, float scale, ArenaMap map){
		super(x,y,img, scale);
		registerMovement(map);
		boundbox = new Rectangle(x,y,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
	}
	
	public RectangleObject(float x, float y, float width, float height, float rotation, Image img, float scale, ArenaMap map){
		super(x,y,img, scale);
		registerMovement(map);
		boundbox = new Rectangle(x,y,width,height);
		init(boundbox.getWidth(), boundbox.getHeight());
		rotate(rotation);
	}
	
	public void rotate(float rotate){
		boundbox.transform(Transform.createRotateTransform((float) Math.toRadians((double)rotate)));
		rotation += rotate;
	}
	public void setRotate(float setting){
		boundbox.transform(Transform.createRotateTransform((setting - rotation), boundbox.getCenterY(), boundbox.getCenterY()));
	}
}
