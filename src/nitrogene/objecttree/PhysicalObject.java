package nitrogene.objecttree;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import nitrogene.collision.Vector;
import nitrogene.core.GlobalInformation;
import nitrogene.util.ImageBase;
import nitrogene.util.AngledMovement;
import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

public class PhysicalObject {
	protected AngledMovement angledmovement;
	protected String defaultmovement;
	protected float rotationalconstant;
	protected Movement movement;
	protected ArenaMap map;
	protected Shape boundbox, newboundbox;
	protected Image mainimg;
	protected float scalefactor;
	protected int delta;
	protected float rotation;
	protected float width, height;

	public PhysicalObject(float x, float y, Image img, float scalefactor, ArenaMap map){
		width = 0;
		height = 0;
		this.map = map;
		this.mainimg = img;
		this.scalefactor = scalefactor;
		boundbox = GlobalInformation.getImageData().get(img.getResourceReference());
		if(boundbox == null){
			System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		//this.boundbox = boundbox.transform(Transform.createScaleTransform(this.scalefactor, this.scalefactor));
		init(img.getWidth(), img.getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(x);
		this.setY(y);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	
	protected void setDefaultMovement(String s){
		if(s == "angled"){
			defaultmovement = "angled";
		} else if(s == "normal"){
			defaultmovement = "normal";
		}
	}
	
	protected void init(float width, float height){
		this.width = width;
		this.height = height;
	}
	public void moveAngled(int thrust, int delta){
		angledmovement.Accelerate(new Vector(newboundbox.getCenterX(),newboundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		
		this.setX(this.getBoundbox().getX()+((angledmovement.getDx()*gj)*mm));
		this.setY(this.getBoundbox().getY()+((angledmovement.getDy()*gj)*mm));
		
		this.rotation = angledmovement.getRotationAngle();
	}
	
	public void move(int thrust, int delta){
		movement.Accelerate(new Vector(newboundbox.getCenterX(),newboundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		
		this.setX(this.getBoundbox().getX()+((movement.getDx()*gj)*mm));
		this.setY(this.getBoundbox().getY()+((movement.getDy()*gj)*mm));
		
		rotation+=rotationalconstant*mm;
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
		double dist = Math.sqrt((getBoundbox().getCenterX()-obj.getCenterX())*(getBoundbox().getCenterX()-obj.getCenterX()) + (getBoundbox().getCenterY()-obj.getCenterY())*(getBoundbox().getCenterY()-obj.getCenterY()));
		if(this.getBoundbox().getX() + width + this.movement.getDx() >= dist || 
			this.getBoundbox().getY() + width + this.movement.getDy() >= dist){
			//if(obj instanceof ImageObject) {
			//	return obj.isColliding(obj);
			//} else {	    
				if(this.getBoundbox().intersects(obj.getBoundbox())) {
					return true;
				} else {
					return false;
				}
			//}
		}
		return false;
	}
	
	public boolean isContaining(float x, float y){
		if(this.getBoundbox().getCenterX() + width + this.getMovement().getDx() >= x ||
				this.getBoundbox().getCenterY() + height + this.getMovement().getDy() >= y ||
				this.getBoundbox().getCenterX() - width - this.getMovement().getDx() <= x ||
				this.getBoundbox().getCenterY() - height - this.getMovement().getDy() <= y
				){
		return this.getBoundbox().contains(x, y);
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
		return newboundbox;
	}
	public float getX(){
		return newboundbox.getX();
	}
	public float getY(){
		return newboundbox.getY();
	}
	public float getCenterX(){
		/*
		if(newboundbox!=null){
		return newboundbox.getCenterX();
		}else return 0f;
		*/
		return this.getImage().getCenterOfRotationX();
	}
	public float getCenterY(){
		/*
		if(newboundbox!=null){
		return newboundbox.getCenterY();
		}else{
			return 0f;
		}
		*/
		return this.getImage().getCenterOfRotationY();
	}
	public void setX(float x){
		newboundbox.setX(x);
	}
	public void setY(float y){
		newboundbox.setY(y);
	}
	public void setCenterX(float x){
		newboundbox.setCenterX(x);
		
	}
	public void setCenterY(float y){
		newboundbox.setCenterY(y);
	}
	public Movement getMovement(){
		if(defaultmovement == "angled"){
			return angledmovement;
		} else if(defaultmovement == "normal"){
			return movement;
		} else{
			System.out.println("CRITICAL: PhysicalObject.defaultmovement not set");
			return movement;
		}
	}
	public void setScale(float scale){
		this.scalefactor = scale;
	}
	public float getScale(){
		return this.scalefactor;
	}
	public Shape getBoundbox(){
		return newboundbox;
	}
	public Shape getOriginalBoundbox(){
		return boundbox;
	}
	protected void setBoundbox(Shape boundboxm){
		this.newboundbox = boundboxm;
	}
	public void update(int delta, float camX, float camY) {
	}
	public void setRotation(float rotation){
		this.rotation = rotation;
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
