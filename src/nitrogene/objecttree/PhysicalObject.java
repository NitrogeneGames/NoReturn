package nitrogene.objecttree;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import nitrogene.collision.Vector;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;
import nitrogene.gui.Sprite;
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
	protected Sprite mainimg;
	protected float scalefactor;
	protected int delta;
	protected float rotation;
	public float width;
	public float height;
	public boolean isLoaded = false;
	protected float tempx, tempy;
	protected boolean destroyed = false;

	public PhysicalObject(float x, float y){
		width = 0;
		height = 0;
		tempx = x;
		tempy = y;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void load(Image img, float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = new Sprite(img.copy());
		boundbox = GlobalInformation.getHitbox(img.getResourceReference());
		if(boundbox == null){
			//System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		boundbox = boundbox.transform(Transform.createScaleTransform(scalefactor, scalefactor));
		init(img.getWidth(), img.getHeight());
		newboundbox = new Polygon(boundbox.getPoints());
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	private void destroy(){
		destroyed = true;
		this.removeBoundbox();
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
	
	public Line move(float thrust, int delta){
		float x1 = getX();
		float y1 = getY();
		movement.Accelerate(new Vector(newboundbox.getCenterX(),newboundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		
		this.setX(this.getBoundbox().getX()+((movement.getDx()*gj)*mm));
		this.setY(this.getBoundbox().getY()+((movement.getDy()*gj)*mm));
		
		rotation+=rotationalconstant*mm;
		return new Line(x1, y1, getX(), getY());
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
				if(this.getBoundbox().intersects(obj.getBoundbox())) {
					return true;
				} else if(this.getBoundbox().contains(obj.getBoundbox())) {
					return true;
				} else if(obj.getBoundbox().contains(this.getBoundbox())) {
					return true;
				} else {
					return false;
				}
	}
	public boolean isColliding(Shape obj){
		if(this.getBoundbox().intersects(obj)) {
			return true;
		} else if(this.getBoundbox().contains(obj)) {
			return true;
		} else if(obj.contains(this.getBoundbox())) {
			return true;
		} else {
			return false;
		}
}
	public boolean isColliding(Line l) {
		if(this.getBoundbox().intersects(l)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isContaining(float x, float y){
		/*
		if(this.getBoundbox().getX() + width + this.getMovement().getDx() >= x ||
				this.getBoundbox().getY() + height + this.getMovement().getDy() >= y ||
				this.getBoundbox().getX() - width - this.getMovement().getDx() <= x ||
				this.getBoundbox().getY() - height - this.getMovement().getDy() <= y
				){
				*/
		return this.getBoundbox().contains(x, y);
		/*
		}
		else return false;
		*/
	}
	
	public float getRotation(){
		if(rotation > 180) {
			return rotation - 360;
		} else if(rotation < -180) {
			return rotation + 360;
		}
		return rotation;
	}
	
	/*public Image getImage(){
		if(mainimg != null) {
			return mainimg.getImage();
		} else {
			return null;
		}
	}*/
	public Sprite getSprite() {
		return mainimg;
	}
	protected void changeOpac(float changepercent){
		mainimg.getImage().setImageColor(1f, 1f, 1f, changepercent * 0.1f);
	}
	public Shape getShape(){
		return newboundbox;
	}
	public float getX(){
		if(newboundbox == null){
			return tempx;
		} else{
			return newboundbox.getX();
		}
	}
	public float getY(){
		if(newboundbox == null){
			return tempy;
		} else{
			return newboundbox.getY();
		}
	}
	public float getCenterX(){
		/*
		if(newboundbox!=null){
		return newboundbox.getCenterX();
		}else return 0f;
		*/
		return this.getSprite().getImage().getCenterOfRotationX();
	}
	public float getCenterY(){
		/*
		if(newboundbox!=null){
		return newboundbox.getCenterY();
		}else{
			return 0f;
		}
		*/
		return this.getSprite().getImage().getCenterOfRotationY();
	}
	public float getRealCenterX(){
		if(this.getSprite() != null) {
			return this.getX()+(this.getSprite().getImage().getWidth()*scalefactor/2);
		}
		return 0;
	}
	public float getRealCenterY(){
		if(this.getSprite() != null) {
			return this.getY()+(this.getSprite().getImage().getHeight()*scalefactor/2);
		}
		return 0;
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
			Resources.log("CRITICAL: PhysicalObject.defaultmovement not set");
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
	public void removeBoundbox() {
		this.boundbox = null;
		
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
