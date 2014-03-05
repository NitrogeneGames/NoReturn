package nitrogene.objecttree;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import nitrogene.collision.Vector;
import nitrogene.core.Craft;
import nitrogene.util.ImageBase;
import nitrogene.util.Movement;
import nitrogene.weapon.SLaser;
import nitrogene.world.ArenaMap;

public class ImageObject extends PhysicalObject{
	private float x;
	private float y;
	public ImageObject(float width, float height, Image img, float scalefactor, ArenaMap map){
		super(width, height, img, scalefactor, map);
		ImageBase.registerImage(this.mainimg);
	}
	
	@Override
	public void move(int thrust, int delta){
		movement.Accelerate(new Vector(getCenterX(),getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		setX(getX()+((movement.getDx()*gj)*mm));
		setY(getY()+((movement.getDy()*gj)*mm));
	}
	
	@Override
	public boolean isColliding(PhysicalObject obj){
		double dist = Math.sqrt((this.getCenterX()-obj.getCenterX())*(this.getCenterX()-obj.getCenterX()) + (this.getCenterY()-obj.getCenterY())*(this.getCenterY()-obj.getCenterY()));
		if(this.getCenterX() + width + this.movement.getDx() >= dist || 
			this.getCenterY() + width + this.movement.getDy() >= dist){
			
					if(ImageBase.contains(this.mainimg)) {
						ArrayList<int[]> pixels = ImageBase.getPixels(this.mainimg);
						for(int i = 0; i < pixels.size(); i++) {
							if(this.getImage().getRotation() == 180) {
								if(obj.isContaining((mainimg.getWidth() - pixels.get(i)[0]) + this.getX(), (mainimg.getHeight() - pixels.get(i)[1]) + this.getY())) {
									return true;								
								}
							} else {
								if(obj.isContaining((/*mainimg.getWidth() - */pixels.get(i)[0]) + this.getX(), (/*mainimg.getHeight() -*/ pixels.get(i)[1]) + this.getY())) {
									return true;								
								}
							}
						}
					}
				}
		
		return false;
		
	}
	@Override
	public boolean isContaining(float x, float y){
		if(getCenterX() + width + this.movement.getDx() >= x ||
				getCenterY() + height + this.movement.getDy() >= y ||
				getCenterX() - width - this.movement.getDx() <= x ||
				getCenterY() - height - this.movement.getDy() <= y
				){
		return pointOnImage(x, y);
		}
		else return false;
	}
	
	@Override
	public float getX(){
		return this.x;
	}
	
	@Override
	public float getY(){
		return this.y;
	}
	
	@Override
	public float getCenterX(){
		return getX() + mainimg.getWidth()/2;
	}
	
	@Override
	public float getCenterY(){
		return getY() + mainimg.getHeight()/2;
	}
	
	@Override
	public void setX(float x){
		this.x = x;
	}
	
	@Override
	public void setY(float y){
		this.y = y;
	}
	
	@Override
	public void setCenterX(float x){
		this.x = x - mainimg.getWidth()/2;
	}
	
	@Override
	public void setCenterY(float y){
		this.y = y - mainimg.getHeight()/2;
	}
	public boolean pointOnImage(float x, float y) {
		if(x-getX() < 0 || x-getX() >= mainimg.getWidth() || y-getY() < 0 || y-getY() >= mainimg.getHeight()) {
			return false;
		}
		Color c = mainimg.getColor((int) (x-getX()), (int)(y-getY()));
		return c.a == 0f;
	}
}
