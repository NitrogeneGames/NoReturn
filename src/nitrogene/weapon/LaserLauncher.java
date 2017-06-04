package nitrogene.weapon;

import java.util.ArrayList;

import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Zoom;
import nitrogene.npc.NPCship;
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
import org.newdawn.slick.geom.Polygon;

public class LaserLauncher extends ShipSystem{
	//Basic Variables for Laser Launcher
	private float desx, desy, camX, camY;
	public ArrayList<LaserProjectile> slaserlist = new ArrayList<LaserProjectile>();
	public int accuracy, timer, maxtime,  damage, planetdamage;
	private double mangle;
	private float mmangle, craftX, craftY, size, speed;
	public boolean isRotating;
	private int interburst, outerburst, burstnumber;
	public Craft parent;
	private Image proje;
	private Sound firesound;
	public int laserId;
	public String name;
	private volatile int delta;
	public EnumWeapon enumtype;
	
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
	
	public LaserLauncher(Craft w, float xpos, float ypos, EnumWeapon stat, int id, String n, short priority) throws SlickException{
		//power usage 100 giga-watts
		super(w,xpos, ypos, stat.hp, 0, 100, 100f);
		parent = w;
		if(stat.image == "res/Laser1.png") mainimg = (Image) AssetManager.get().get("standardlaser");
		else if(stat.image == "res/klaar_pulsar_2.png") mainimg = (Image) AssetManager.get().get("klaarpulsar");
		else mainimg = (Image) AssetManager.get().get("standardlaser");
		enumtype = stat;
		name = n;
		desx = 0;
		desy = 0;
		this.accuracy = stat.accuracy;
		mangle = 0;
		mmangle = 0f;
		laserId = id;
		this.planetdamage = stat.planetdamage;
		this.interburst = stat.interburst;
		this.outerburst = stat.outerburst;
		this.burstnumber = stat.burstnumber;
		try {
			this.proje = new Image(stat.laserimage);
			this.firesound = new Sound(stat.firesound);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		isRotating = false;
		craftX = 0;
		craftY = 0;
		camX = 0;
		camY = 0;
		this.speed = stat.speed;
		this.damage = stat.damage;
		this.size = stat.size;
	}
	
	public void load(ArenaMap map){
		this.map = map;
		boundbox = GlobalInformation.getImageData().get(mainimg.getResourceReference());
		if(boundbox == null){
			//System.out.println(mainimg.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(mainimg.getWidth(), mainimg.getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	
	public void setTarget(float desx, float desy){
		this.desx = desx;
		this.desy = desy;
		camX = 0;
		camY = 0;
	}
	
	public void setTarget(float desx, float desy, float camX, float camY){
		this.desx = desx;
		this.desy = desy;
		this.camX = camX;
		this.camY = camY;
	}
	
	public float getTargetX(){
		return desx;
	}
	
	public float getTargetY(){
		return desy;
	}
	
	public void update(float craftX,float craftY,int delta){
		this.craftX = craftX;
		this.craftY = craftY;
		//if(this.getTimer().isPauseLocked){
		//	CursorSystem.changeCursor("redfire");
		//}else CursorSystem.changeCursor("greenfire");
		this.delta = delta;
		if(this.getHp() < 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else if(this.getTimer().getClock().isRunning()){
			this.setStatus(EnumStatus.ENGAGED);
		} else if(this.getHp()<this.getMaxHp()){
			this.setStatus(EnumStatus.DAMAGED);
		} else{
			this.setStatus(EnumStatus.READY);
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
	      if(((this.getAngle()-this.getImage().getRotation()) != 0)) {
	    	  float rota = Target.getRotation(this);
	    	  float dist = Math.abs(rota);
	    		  if(dist >= 100) {
	    			  if(dist >= 200) {
	    				  if(dist >= 300) {
				       this.getImage().rotate(rota/50*(delta/25f));
				       
				      } else {   //<300
				       this.getImage().rotate(rota/25*(delta/25f));
				      }
				      } else {  //<200
				       this.getImage().rotate(rota/10*(delta/25f));
				      }
				      } else { //<100
				      this.getImage().rotate(rota/5*(delta/25f));
				      }
	    		  //50,40,30,20
	      } else {
	    	  //this.getImage().setCenterOfRotation(this.getX(), this.getY());
	          this.getImage().setRotation(this.getAngle());
	      }
	      //this.getImage().setCenterOfRotation(parent.getX(), parent.getY());
	      //this.getImage().setRotation(parent.getMovement().getRotationAngle());
	      this.getImage().draw(this.getX()+craftX, this.getY()+craftY, 0.8f);
	      
	      ArrayList<LaserProjectile> slaserlistcopy = new ArrayList<LaserProjectile>();
	      slaserlistcopy = slaserlist;
	      for(int a = 0; a < slaserlistcopy.size(); a++){
	    	  LaserProjectile laser = slaserlistcopy.get(a);
	    	  if(laser.getX()>Zoom.getZoomWidth()/2+(parent.getX()+174)||
						laser.getX()+(laser.getImage().getWidth()*laser.getScale())<parent.getX()+174-(Zoom.getZoomWidth()/2)||
						laser.getY()>Zoom.getZoomHeight()/2+(parent.getY()+88)||
						laser.getY()+(laser.getImage().getHeight()*laser.getScale())<parent.getY()+88-(Zoom.getZoomHeight()/2)){
	    		  	laser=null;
					continue;
				}
				laser.getImage().draw(laser.getBoundbox().getX(), laser.getBoundbox().getY(),laser.getScale());
				if(GlobalInformation.testMode) g.draw(laser.getBoundbox());
				laser = null;
	      }
	}
	public void fire() throws SlickException{
		LaserProjectile temp = new LaserProjectile(this.getX()+craftX,this.getY()+craftY, Zoom.scale(camX)+desx, Zoom.scale(camY)+desy,
				accuracy, speed, damage, planetdamage, this.getAngle(), this);
		temp.load(proje, size, map);
		slaserlist.add(temp);
	}
	
	public float getAngle(){
			double mecX = (desx+Zoom.scale(camX) - (this.getX()+craftX));
			double mecY = (desy+Zoom.scale(camY) - (this.getY()+craftY));
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
