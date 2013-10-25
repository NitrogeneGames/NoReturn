package nitrogene.objecttree;

import org.newdawn.slick.geom.Shape;

import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

public class PhysicalObject {
	protected Movement movement;
	protected ArenaMap map;
	protected Shape boundbox;
	protected int delta;
	protected float width, height;

	public PhysicalObject(float x, float y, float width, float height, ArenaMap map){
		this.boundbox.setX(x);
		this.boundbox.setY(y);
		width = 0;
		height = 0;
		this.map = map;
		movement = new Movement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
	}
	
	protected void init(float width, float height){
		this.width = width;
		this.height = height;
	}
	
	public void update(int delta){
		this.delta = delta;
		move(3);
	}
	
	private void move(int thrust){
		float mm = delta/1000f;
		float gj = thrust*1f;
		boundbox.setX(boundbox.getX()+((movement.getDx()*gj)*mm));
		boundbox.setY(boundbox.getY()+((movement.getDy()*gj)*mm));
	}
	
	public boolean isColliding(PhysicalObject obj){
		if(this.boundbox.getCenterX() + width + this.movement.getDx() >= obj.boundbox.getCenterX() ||
				this.boundbox.getCenterY() + height + this.movement.getDy() >= obj.boundbox.getCenterY() ||
				this.boundbox.getCenterX() - width - this.movement.getDx() <= obj.boundbox.getCenterX() ||
				this.boundbox.getCenterY() - height - this.movement.getDy() <= obj.boundbox.getCenterY()
				){
		return this.boundbox.intersects(obj.boundbox);
		}
		else return false;
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
		return boundbox.getCenterX();
	}
	public float getCenterY(){
		return boundbox.getCenterY();
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
}
