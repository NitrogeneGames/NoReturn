package nitrogene.objecttree;

import org.newdawn.slick.geom.Rectangle;

public class RectangleObject extends PhysicalObject{
	private Rectangle boundbox;
	
	public RectangleObject(){
		super(0f,0f,0f,0f);
		boundbox = new Rectangle(0,0,0,0);
	}
	
	public RectangleObject(float width, float height){
		super(0f,0f,width,height);
		boundbox = new Rectangle(0,0,width,height);
	}
	
	public RectangleObject(float x, float y, float width, float height){
		super(x,y,width,height);
		boundbox = new Rectangle(x,y,width,height);
	}
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
}
