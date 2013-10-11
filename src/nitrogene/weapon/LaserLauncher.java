package nitrogene.weapon;

import java.util.ArrayList;

import nitrogene.core.Craft;
import nitrogene.core.SLaser;
import nitrogene.core.Zoom;
import nitrogene.util.TickSystem;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LaserLauncher {
	private float desx, desy, x, y, camX, camY;
	public ArrayList<SLaser> slaserlist = new ArrayList<SLaser>();
	public int accuracy, timer, maxtime,  damage;
	private double mangle;
	private float mmangle, craftX, craftY, size, speed;
	public boolean isRotating;
	public Craft parent;
	Image image;
	private String proje;
	
	public LaserLauncher(Craft w, float xpos, float ypos, Image image, int accuracy, int time, float speed, int damage, float size, String proj){
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
	}
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
		proje = "res/LaserV2ro.png";
	}
	public LaserLauncher(Craft w, float xpos, float ypos, EnumWeapon stat){
		parent = w;
		this.x = xpos;
		this.y = ypos;
		desx = 0;
		desy = 0;
		this.accuracy = stat.accuracy;
		mangle = 0;
		mmangle = 0f;
		this.maxtime = stat.time;
		try {
			this.image = new Image(stat.image);
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
		this.proje = stat.laserimage;
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
	
	public void update(float craftX,float craftY){
		this.craftX = craftX;
		this.craftY = craftY;
	}
	
	public void fire() throws SlickException{
		slaserlist.add(new SLaser((float) ((x+craftX) * 1),(float) ((y+craftY)* 1), camX+desx, camY+desy, accuracy, speed, damage, size, proje));
	}
	
	public float getAngle(){
			double mecX = (desx+camX - (x+craftX));
			double mecY = (desy+camY - (y+craftY));
			mangle = Math.toDegrees(Math.atan2(mecY,mecX));
			mmangle = (float) mangle;
			return mmangle;
	}
	
	public Image getImage(){
		return image;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	public WeaponTimer getTimer() {
		return TickSystem.getTimer(this);
	}

}
