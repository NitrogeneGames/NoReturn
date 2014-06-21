package nitrogene.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Slider {
	private float x,y,maxx,maxy,minx,miny,width,height;
	private boolean isGrabbed, visible;
	private Shape boundbox;

	public Slider(float x, float y, float width, float height, float maxx, float maxy, float minx, float miny){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.maxx=maxx;
		this.minx=minx;
		this.maxy=maxy;
		this.miny=miny;
		generateRectanlge();
		visible = true;
	}
	
	public void generateRectanlge(){
		boundbox = new Rectangle(x,y,width,height);
	}
	
	public void update(GameContainer gc){
		float temp[] = new float[5];
		if(maxx-minx<boundbox.getWidth()){
			temp[1] = boundbox.getMinX();
			temp[2] = boundbox.getMaxX();
		} else{
			temp[1] = minx;
			temp[2] = maxx;
		}
		if(maxy-miny<boundbox.getHeight()){
			temp[3] = boundbox.getMinY();
			temp[4] = boundbox.getMaxY();
		} else{
			temp[3] = miny;
			temp[4] = maxy;
		}
		
		if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if (gc.getInput().getMouseX()>=temp[1] && gc.getInput().getMouseX() <= temp[2] &&
					gc.getInput().getMouseY()>=temp[3] && gc.getInput().getMouseY() <= temp[4]){
				isGrabbed = true;
				registerMovement(gc);
			}else if (boundbox.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())){
				isGrabbed = true;
				registerMovement(gc);
			}else{
				isGrabbed = false;
			}
		}
		boundbox.setX(this.x);
		boundbox.setY(this.y);
	}
	
	private void registerMovement(GameContainer gc){
		this.x=gc.getInput().getMouseX()-10;
    	if(x<minx){
    		x=minx;
    	}
    	if(x>maxx){
    		x=maxx;
    	}
    	this.y=gc.getInput().getMouseY()-20;
    	if(y<minx){
    		y=miny;
    	}
    	if(y>maxx){
    		y=maxy;
    	}
	}
	
	public void render(Image normalimage, Image pressedimage){
		if(visible){
			if(isGrabbed){
				pressedimage.draw(x,y,width,height);
			}else{
				normalimage.draw(x,y,width,height);
			}
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
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
}
