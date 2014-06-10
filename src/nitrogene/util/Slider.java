package nitrogene.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Slider {
	private float x,y,maxx,maxy,minx,miny,width,height,storedmousex,storedmousey;
	private Image normalimage, pressedimage;
	private boolean isGrabbed, visible;
	private Shape boundbox;

	public Slider(float x, float y, float width, float height, Image normalimage, Image pressedimage, float maxx, float maxy, float minx, float miny){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.normalimage=normalimage;
		normalimage.setFilter(Image.FILTER_NEAREST);
		if(pressedimage!=null){
			this.pressedimage=pressedimage;
			pressedimage.setFilter(Image.FILTER_NEAREST);
		}else{
			pressedimage=normalimage;
		}
		this.maxx=maxx;
		this.minx=minx;
		this.maxy=maxy;
		this.miny=miny;
		generateRectanlge();
	}
	
	public void generateRectanlge(){
		boundbox = new Rectangle(x,y,width,height);
	}
	
	public void update(GameContainer gc){
		if (boundbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
	        {
	            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
	            {
	            	isGrabbed=true;
	            	this.x+=gc.getInput().getMouseX()-storedmousex;
	            	if(x<minx){
	            		x=minx;
	            	}
	            	if(x>maxx){
	            		x=maxx;
	            	}
	            	this.y+=gc.getInput().getMouseY()-storedmousey;
	            	if(y<minx){
	            		y=miny;
	            	}
	            	if(y>maxx){
	            		y=maxy;
	            	}
	            }else{
	            	isGrabbed=false;
	            }
	        }
		boundbox.setX(this.x);
		boundbox.setY(this.y);
		
		storedmousex=gc.getInput().getMouseX();
		storedmousey=gc.getInput().getMouseY();
	}
	
	public void render(){
		if(isGrabbed){
			pressedimage.draw(x,y,width,height);
		}else{
			normalimage.draw(x,y,width,height);
		}
	}

	public boolean getGrabbed(){
		return this.isGrabbed;
	}
	
	public void setVisible(boolean b){
		visible = b;
	}
	
	public boolean isVisible(){
		return visible;
	}
}
