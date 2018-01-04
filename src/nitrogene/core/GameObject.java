package nitrogene.core;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.Mover;

import nitrogene.gui.Sprite;
import nitrogene.npc.NPCship;
import nitrogene.util.AngledMovement;
import nitrogene.util.Movement;
import nitrogene.util.Vector;
import nitrogene.world.World;
import nitrogene.world.TravelPath;

public class GameObject implements Mover {
	
	//Class that represents an actual object in the game
	//Almost all objects are an instance of this class, or an extension of it
	//TODO - change to an interface (maybe?)
	
	
	protected AngledMovement angledmovement;
	protected String defaultmovement;
	protected float rotationalconstant;
	protected Movement movement;
	protected World map;
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
	protected Point centerOfRotation;
	public World world;
	public GameObject(World world, float x, float y){
		//Initializer, requires the world that the object is in as well as a location
		this.world = world;
		width = 0;
		height = 0;
		tempx = x;
		tempy = y;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void load(String img, float scalefactor, World map){
		//Load the image/boundbox since they do not exist during the constructor
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = new Sprite(img);
		
		//Load the boundbox
		boundbox = GlobalInformation.getHitbox(mainimg.getResourceReference());
		if(boundbox == null){
			//System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		boundbox = boundbox.transform(Transform.createScaleTransform(scalefactor, scalefactor));
		
		//Set image data as well as physicall location/rotations
		init(mainimg.getImage().getWidth(), mainimg.getImage().getHeight());
		newboundbox = new Polygon(boundbox.getPoints());
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		centerOfRotation = new Point(getSprite().getImage().getCenterOfRotationX(),getSprite().getImage().getCenterOfRotationY());
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	protected void setDefaultMovement(String s){
		//Decide if movement is angled or standard
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
		//Move the object using angled movement
		angledmovement.Accelerate(new Vector(newboundbox.getCenterX(),newboundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		this.setX(this.getBoundbox().getX()+((angledmovement.getDx()*gj)*mm));
		this.setY(this.getBoundbox().getY()+((angledmovement.getDy()*gj)*mm));
		
		this.rotation = angledmovement.getRotationAngle();
	}
	public Line move(float thrust, int delta){
		//Move the object along a line
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
	public Line move(float thrust, int delta, TravelPath path){
		//Move the object along a TravelPath (obsolete pathfinding system)
		float x1 = getX();
		float y1 = getY();
		movement.Accelerate(new Vector(newboundbox.getCenterX(),newboundbox.getCenterY()), delta);
		float mm = delta/1000f;
		float gj = thrust*1f;
		float deltaX = ((movement.getDx()*gj)*mm);
		float deltaY = ((movement.getDy()*gj)*mm);
		path.registerMovement(deltaX, deltaY);
		this.setX(this.getBoundbox().getX()+deltaX);
		this.setY(this.getBoundbox().getY()+deltaY);
		
		rotation+=rotationalconstant*mm;
		return new Line(x1, y1, getX(), getY());
	}
	/*
	public boolean isColliding(PhysicalObject obj){
		//Obsolete collision detection system

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
	public boolean isColliding(GameObject obj){
		//Check to see if 2 objects' boundboxes are colliding
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
		//Check to see if the object is colliding with a Shape
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
	
	public boolean isContaining(float x, float y){
		return this.getBoundbox().contains(x, y);
	}
	
	public float getRotation(){
		//Get rotation, range between -180 and 180
		if(rotation > 180) {
			return rotation - 360;
		} else if(rotation < -180) {
			return rotation + 360;
		}
		return rotation;
	}
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
		return this.getSprite().getImage().getCenterOfRotationX();
	}
	public float getCenterY(){
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
	public void setCenterOfRotation(Point p) {
		centerOfRotation = p;
	}
	public Point getCenterOfRotation() {
		return centerOfRotation;
	}
	public static ArrayList<int[]> getCollidingPoints(GameObject a, GameObject a1) {
		//Get points that are contained in both objects
		ArrayList<int[]> list = new ArrayList<int[]>();
		for(int i = 0; i < a.getBoundbox().getPointCount(); i++) {
			if(a1.isContaining(a.getBoundbox().getPoint(i)[0], a.getBoundbox().getPoint(i)[1])) {
				list.add(new int[]{(int) a.getBoundbox().getPoint(i)[0], (int) a.getBoundbox().getPoint(i)[1]});
			}
		}
		return list;
	}
	public boolean isOnScreen() {
		return GameState.screenBox.intersects(this.getBoundbox()) || 
				GameState.screenBox.contains(this.getBoundbox());
	}
	
}
