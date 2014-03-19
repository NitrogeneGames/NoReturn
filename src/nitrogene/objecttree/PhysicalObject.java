package nitrogene.objecttree;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import nitrogene.collision.Vector;
import nitrogene.util.ImageBase;
import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

public class PhysicalObject {
	protected Movement movement;
	protected ArenaMap map;
	protected Shape boundbox;
	protected Image mainimg;
	protected float scalefactor;
	protected int delta;
	protected float rotation;
	protected float width, height;

	public PhysicalObject(float width, float height, Image img, float scalefactor, ArenaMap map){
		width = 0;
		height = 0;
		this.map = map;
		this.mainimg = img;
		this.scalefactor = scalefactor;
		movement = new Movement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
	}
	
	protected void init(float width, float height){
		this.width = width;
		this.height = height;
	}
	public void move(int thrust, int delta){
		movement.Accelerate(new Vector(boundbox.getCenterX(),boundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		boundbox.setX(boundbox.getX()+((movement.getDx()*gj)*mm));
		boundbox.setY(boundbox.getY()+((movement.getDy()*gj)*mm));
	}
	/*
public boolean isColliding(PhysicalObject obj){
		

		double dist = Math.sqrt((boundbox.getCenterX()-obj.getCenterX())*(boundbox.getCenterX()-obj.getCenterX()) + (boundbox.getCenterY()-obj.getCenterY())*(boundbox.getCenterY()-obj.getCenterY()));
		if(this.boundbox.getCenterX() + width + this.movement.getDx() >= dist || 
			this.boundbox.getCenterY() + width + this.movement.getDy() >= dist){
			if(obj instanceof ImageObject) {
				return obj.isColliding(obj);
			} else {	    
				if(this.boundbox.intersects(obj.boundbox)) {
					ArrayList<int[]> points = getCollidingPoints(this, obj);
						boolean flag = false;
						for(int i = 0; i < points.size(); i++) {

							boolean flag1 = false;
							boolean flag2 = false;
							int x = (int) (points.get(i)[0]-getX());
							int y = (int) (points.get(i)[1]-getY());
							int x1 = (int) (points.get(i)[0]-obj.getX());
							int y1 = (int) (points.get(i)[1]-obj.getY());
						
							if(x < 0 || x >= this.mainimg.getWidth() || y < 0 || y >= this.mainimg.getHeight()) {
								flag1 = false;
							} else {
								Color c = mainimg.getColor((int) (x), (int)(y));
								flag1 = ( c.a == 0f );
							}				
							
							if(x1 < 0 || x1 >= obj.mainimg.getWidth() || y1 < 0 || y1 >= obj.mainimg.getHeight()) {
								flag2 = false;
							} else {
								Color c = mainimg.getColor((int) (x1), (int)(y1));
								flag2 = ( c.a == 0f );
							}
							if(flag1 == true && flag2 == true) {
								System.out.println("FLAG 3");
								return true;
							}
							
						}
						return flag;
				}
			}
		}
		return false;
	}
	 */
	public boolean isColliding(PhysicalObject obj){
		
		/*if(this.boundbox.getCenterX() + width + this.movement.getDx() >= obj.getCenterX() ||
				this.boundbox.getCenterY() + height + this.movement.getDy() >= obj.getCenterY() ||
				this.boundbox.getCenterX() - width - this.movement.getDx() <= obj.getCenterX() ||
				this.boundbox.getCenterY() - height - this.movement.getDy() <= obj.getY()
				){*/
		double dist = Math.sqrt((boundbox.getCenterX()-obj.getCenterX())*(boundbox.getCenterX()-obj.getCenterX()) + (boundbox.getCenterY()-obj.getCenterY())*(boundbox.getCenterY()-obj.getCenterY()));
		if(this.boundbox.getCenterX() + width + this.movement.getDx() >= dist || 
			this.boundbox.getCenterY() + width + this.movement.getDy() >= dist){
			if(obj instanceof ImageObject) {
				return obj.isColliding(obj);
			} else {	    
				if(this.boundbox.intersects(obj.boundbox)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean isContaining(float x, float y){
		if(this.boundbox.getCenterX() + width + this.movement.getDx() >= x ||
				this.boundbox.getCenterY() + height + this.movement.getDy() >= y ||
				this.boundbox.getCenterX() - width - this.movement.getDx() <= x ||
				this.boundbox.getCenterY() - height - this.movement.getDy() <= y
				){
		return this.boundbox.contains(x, y);
		}
		else return false;
	}
	
	public float getRotation(){
		return rotation;
	}
	
	public Image getImage(){
		return mainimg;
	}
	protected void changeOpac(float changepercent){
		mainimg.setImageColor(1f, 1f, 1f, changepercent * 0.1f);
	}
	public Shape getShape(){
		return boundbox;
	}
	public float getX(){
		return boundbox.getX();
	}
	public float getY(){
		return boundbox.getY();
	}
	public float getCenterX(){
		if(boundbox!=null){
		return boundbox.getCenterX();
		}else return 0f;
	}
	public float getCenterY(){
		if(boundbox!=null){
		return boundbox.getCenterY();
		}else return 0f;
	}
	public void setX(float x){
		boundbox.setX(x);
	}
	public void setY(float y){
		boundbox.setY(y);
	}
	public void setCenterX(float x){
		boundbox.setCenterX(x);
	}
	public void setCenterY(float y){
		boundbox.setCenterY(y);
	}
	public Movement getMovement(){
		return movement;
	}
	public void setScale(float scale){
		this.scalefactor = scale;
	}
	public float getScale(){
		return this.scalefactor;
	}
	public Shape getBoundbox(){
		return boundbox;
	}
	public void update(int delta) {
	}
	public static ArrayList<int[]> getCollidingPoints(PhysicalObject a, PhysicalObject a1) {
		ArrayList<int[]> list = new ArrayList<int[]>();
		for(int i = 0; i < a.boundbox.getPointCount(); i++) {
			if(a1.isContaining(a.boundbox.getPoint(i)[0], a.boundbox.getPoint(i)[1])) {
				list.add(new int[]{(int) a.boundbox.getPoint(i)[0], (int) a.boundbox.getPoint(i)[1]});
			}
		}
		return list;
	}
	
}
