package nitrogene.weapon;

import java.util.ArrayList;

import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.core.GameState;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;
import nitrogene.core.Zoom;
import nitrogene.gui.AnimationImage;
import nitrogene.gui.Sprite;
import nitrogene.npc.NPCship;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.system.ShipSystem;
import nitrogene.util.AngledMovement;
import nitrogene.util.EnumStatus;
import nitrogene.util.Movement;
import nitrogene.util.Target;
import nitrogene.util.TickSystem;
import nitrogene.world.ArenaMap;
import nitrogene.world.Planet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

public class LaserLauncher extends ShipSystem{
	//Basic Variables for Laser Launcher
	private float desx, desy, camX, camY;
	public PhysicalObject target;
	public ArrayList<PhysicalProjcetile> slaserlist = new ArrayList<PhysicalProjcetile>();
	public int accuracy, timer, maxtime;
	private double mangle;
	private float mmangle, craftX, craftY;
	public boolean isRotating;
	private int interburst, outerburst, burstnumber;
	public Craft parent;
	private Sound firesound;
	public int laserId;
	public String name;
	private volatile int delta;
	public EnumWeapon enumtype;
	public boolean isTargetingObject = false;
	
	/*
	@Deprecated
	public LaserLauncher(Craft w, float xpos, float ypos, Image image, int accuracy, int time, float speed, int damage, float size, Image proj) throws SlickException{
		parent = w;
		this.x = xpos;
		this.y = ypos;
		desx = 0;
		desy = 0;
		this.accuracy = accuracy;
		mangle = 0;
		mmangle = 0f;
		this.maxtime = time;
		this.image = image;
		isRotating = false;
		craftX = 0;
		craftY = 0;
		camX = 0;
		camY = 0;
		this.speed = speed;
		this.damage = damage;
		this.size = size;
		proje = proj;
		firesound = new Sound("res/sound/laser1final.ogg");
	}
	
	@Deprecated
	public LaserLauncher(Craft w, float xpos, float ypos, Image image, int accuracy, int time, float speed, int damage, float size) throws SlickException{
		parent = w;
		this.x = xpos;
		this.y = ypos;
		desx = 0;
		desy = 0;
		this.accuracy = accuracy;
		mangle = 0;
		mmangle = 0f;
		this.maxtime = time;
		this.image = image;
		isRotating = false;
		craftX = 0;
		craftY = 0;
		camX = 0;
		camY = 0;
		this.speed = speed;
		this.damage = damage;
		this.size = size;
		proje = new Image("res/LaserV2ro.png");
		firesound = new Sound("res/sound/laser1final.ogg");
	}
	*/
	public void loadSprite(EnumWeapon stat) {
		mainimg = new Sprite(stat.image);
	}
	public LaserLauncher(Craft w, float xpos, float ypos, EnumWeapon stat, int id, String n, short priority) throws SlickException{
		//power usage 100 giga-watts
		super(w,xpos, ypos, stat.hp, 0, 100, 100f);
		parent = w;
		loadSprite(stat);
		enumtype = stat;
		name = n;
		desx = 0;
		desy = 0;
		this.accuracy = stat.accuracy;
		mangle = 0;
		mmangle = 0f;
		laserId = id;
		this.interburst = stat.interburst;
		this.outerburst = stat.outerburst;
		this.burstnumber = stat.burstnumber;
		try {
			this.firesound = new Sound(stat.firesound);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		isRotating = false;
		craftX = 0;
		craftY = 0;
		camX = 0;
		camY = 0;
	}
	
	public void load(ArenaMap map){
		this.map = map;
		boundbox = GlobalInformation.getHitbox(getSprite().getResourceReference());
		if(boundbox == null){
			//System.out.println(mainimg.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(getSprite().getImage().getWidth(), getSprite().getImage().getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	
	public void updateTarget(float desx, float desy){
		this.desx = desx;
		this.desy = desy;
		camX = 0;
		camY = 0;
		//isTargetingObject = false;
	}
	
	public void setTarget(float desx, float desy, float camX, float camY){
		this.desx = desx;
		this.desy = desy;
		this.camX = camX;
		this.camY = camY;
		isTargetingObject = false;
	}
	public void setTarget(float desx, float desy){
		this.desx = desx;
		this.desy = desy;
		camX = 0;
		camY = 0;
		isTargetingObject = false;
	}
	public void setTarget(PhysicalObject p){
		this.target = p;
		isTargetingObject = true;
	}
	
	public float getTargetX(){
		if(isTargetingObject) {
			if(!target.isDestroyed()){
				return target.getRealCenterX();
			}
		}
		return desx;
	}
	
	public float getTargetY(){
		if(isTargetingObject) {
			if(!target.isDestroyed()){
				return target.getRealCenterY();
			}
		}
		return desy;
	}
	public boolean greenImage = false;
	public void update(float craftX,float craftY,int delta){
		
		this.craftX = craftX;
		this.craftY = craftY;
		//if(this.getTimer().isPauseLocked){
		//	CursorSystem.changeCursor("redfire");
		//}else CursorSystem.changeCursor("greenfire");
		this.delta = delta;
		if(this.getHp() < 0){
			this.setStatus(EnumStatus.DESTROYED);
			setGreenImage(false);
		} else if(this.getTimer().active){
			this.setStatus(EnumStatus.ENGAGED);
			setGreenImage(true);
		} else if(this.getHp()<this.getMaxHp()){
			this.setStatus(EnumStatus.DAMAGED);
			setGreenImage(false);
		} else{
			this.setStatus(EnumStatus.READY);
			setGreenImage(false);
		}
	}
	public void setGreenImage(boolean b) {
		float rot = this.getSprite().getImage().getRotation();
		if(b && !greenImage) {
			mainimg = new Sprite(enumtype.image2);
			this.getSprite().setRotation(rot);
			greenImage = true;
		} else if(!b && greenImage) {
			mainimg = new Sprite(enumtype.image);
			this.getSprite().setRotation(rot);
			greenImage = false;
		}
	}
	public void update(float craftX, float craftY, float camX, float camY, int delta){
		this.craftX = craftX;
		this.craftY = craftY;
		this.camX = camX; 
		this.camY = camY;
		this.delta = delta;
	}
	
	public void render(Graphics g, float camX, float camY){
			if(isTargetingObject) {
				if(target.isDestroyed()) {
					this.setFire(0, 0, camX, camY, false);
				}
			}
	      double[] coords = parent.getRotatedCoordinates(this.getLockedX(), this.getLockedY());
	      this.setX((float) (parent.width/2+coords[0]));
		  this.setY((float) (parent.height/2+coords[1]));

	      float rota = Target.getRotation(this);
	      float dist = Math.abs(rota);
		  if(dist > 1) {
	    		  if(dist >= 100) {
	    			  if(dist >= 200) {
		    			  if(dist >= 300) {
		    				  	this.getSprite().rotate(rota/50*(delta/25f));
				       
		   				  } else {   //<300
		   					  	this.getSprite().rotate(rota/25*(delta/25f));
		   				  }
				      } else {  //<200
				    	  this.getSprite().rotate(rota/10*(delta/25f));
				      }
				  } else { //<100
				      this.getSprite().rotate(rota/5*(delta/25f));
			      }
	    		  //50,40,30,20
	      } else {
	    	  //this.getImage().setCenterOfRotation(this.getX(), this.getY());
	          this.getSprite().setRotation(this.getAngle());
	      }
	      //this.getImage().setCenterOfRotation(parent.getX(), parent.getY());
	      //this.getImage().setRotation(parent.getMovement().getRotationAngle());
	      this.getSprite().drawCentered(this.getX()+craftX, this.getY()+craftY);
	      
	      ArrayList<PhysicalProjcetile> slaserlistcopy = new ArrayList<PhysicalProjcetile>();
	      slaserlistcopy = slaserlist;
	      for(int a = 0; a < slaserlistcopy.size(); a++){
	    	  PhysicalProjcetile laser = slaserlistcopy.get(a);
	    	  /*if(laser.getX()>Zoom.getZoomWidth()/2+(parent.getX()+174)||
						laser.getX()+(laser.getSprite().getImage().getWidth()*laser.getScale())<parent.getX()-174-(Zoom.getZoomWidth()/2)||
						laser.getY()>Zoom.getZoomHeight()/2+(parent.getY()+88)||
						laser.getY()+(laser.getSprite().getImage().getHeight()*laser.getScale())<parent.getY()-88-(Zoom.getZoomHeight()/2)){
	    		  	laser=null;
					continue;
				}*/
	    	  	//float rotat = laser.getSprite().getImage().getRotation();
	    	  	float rotat = 0;
	    	  	float boundX = laser.getBoundbox().getX();
	    	  	float boundY = laser.getBoundbox().getY();
				laser.getSprite().draw(boundX, boundY,laser.getScale());
				if(GameState.debugMode) {
					g.draw(laser.getBoundbox());				
				}
				if(GlobalInformation.testMode) g.draw(laser.getBoundbox());
				//Resources.log(laser.getBoundbox().getX() + ", " + laser.getBoundbox().getY());
				laser = null;
	      }
	}
	public void fire() throws SlickException{
		int xOffset = GlobalInformation.getLaserOffsetX(enumtype.image2);
		int yOffset = GlobalInformation.getLaserOffsetY(enumtype.image2);
		
		Image laser = new Sprite(enumtype.projectile.image).getImage();
		float width = laser.getWidth()*enumtype.projectile.scale;
		float height = laser.getHeight()*enumtype.projectile.scale;

		float x = xOffset;
		float y = yOffset;
		double[] coords = getRotatedCoordinates((double) x, (double) y);
		float newX = (float) coords[0];
		float newY = (float) coords[1];
		PhysicalProjcetile temp = new PhysicalProjcetile(enumtype.projectile, newX + this.getX()+craftX- this.getSprite().getImage().getWidth()/2,newY + this.getY()+craftY- this.getSprite().getImage().getHeight()/2, Zoom.scale(camX)+getTargetX(), Zoom.scale(camY)+getTargetY(),
				accuracy, this.getAngle(), this);
		temp.load(enumtype.projectile.image, enumtype.projectile.scale, map);
		if(enumtype.projectile.tracking && isTargetingObject) {
			temp.setTargetObject(target);
		}
		slaserlist.add(temp);
	}
	public double[] getRotatedCoordinates(double x1, double y1) {

		double currentRotation = this.getSprite().getImage().getRotation();
		double hypotenuse = Math.sqrt(x1*x1 + y1*y1);
		double theta = (180 + Math.toDegrees(Math.atan2(y1,x1))); 
		double mangle = (theta + currentRotation);
		double x2 = -Math.cos(Math.toRadians(mangle)) * hypotenuse;
		double y2 = -Math.sin(Math.toRadians(mangle)) * hypotenuse;
		return new double[] {x2, y2};
	}
	
	public float getAngle(){
			double mecX = (getTargetX()+Zoom.scale(camX) - (this.getX()+craftX));
			double mecY = (getTargetY()+Zoom.scale(camY) - (this.getY()+craftY));
			mangle = Math.toDegrees(Math.atan2(mecY,mecX));
			mmangle = (float) mangle;
			return mmangle;
	}
	
	public WeaponTimer getTimer() {
		return TickSystem.getTimer(this);
	}
	public int getBurstNumber(){
		return burstnumber;
	}
	public int getInterburst(){
		return interburst;
	}
	public int getOuterburst(){
		return outerburst;
	}
	
	public void setFire(int x, int y, float camX, float camY, boolean b){
		if(!b){
			if(this.getTimer().getClock().isRunning()){
				this.getTimer().stop();
			}
		} else{
			if(Target.getTargetObject(x+camX, y+camY, map) != null) {
				if(Target.getTargetObject(x+camX, y+camY, map).getClass() == Planet.class) {
					//Target Planet
					Planet p = (Planet) Target.getTargetObject(x+camX, y+camY, map);				
					this.setTarget(p);
				}else if(Target.getTargetObject(camX + x, camY + y, map).getClass() == Craft.class ||
						Target.getTargetObject(camX + x, camY + y, map).getClass() ==  NPCship.class) {
					//Target Ship
					Craft p = (Craft) Target.getTargetObject(camX + x,camY + y, map);				
					this.setTarget(p);
				}
				
			} else {
			this.setTarget(x + camX, y + camY);
			isTargetingObject = false;
			}
			if(!this.getTimer().isLocked) {
				this.getTimer().start();
			}
		}
	}
	
	public void toggleFire(int x, int y, float camX, float camY){
		if(this.getTimer().getClock().isRunning()){
			this.getTimer().stop();
		}
		else{
			if(Target.getTargetObject(x+camX, y+camY, map) != null) {
				if(Target.getTargetObject(x+camX, y+camY, map).getClass() == Planet.class) {
					//Target Planet
					Planet p = (Planet) Target.getTargetObject(x+camX, y+camY, map);				
					this.setTarget(p.getRealCenterX(), p.getRealCenterY());
				}else if(Target.getTargetObject(camX + x, camY + y, map).getClass() == Craft.class ||
						Target.getTargetObject(camX + x, camY + y, map).getClass() ==  NPCship.class) {
					//Target Ship
					Craft p = (Craft) Target.getTargetObject(camX + x,camY + y, map);				
					this.setTarget(p.getRealCenterX(), p.getRealCenterY());
				}
			} else {
			this.setTarget(x + camX, y + camY);
			}
			//TickSystem.resume();
			this.getTimer().gameResume();
		}
	}
}
