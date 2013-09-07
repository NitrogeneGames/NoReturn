package nitrogene.weapon;

import java.util.ArrayList;

import nitrogene.core.Craft;
import nitrogene.core.SLaser;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LaserLauncher {
	private float desx, desy, x, y, camX, camY;
	public ArrayList<SLaser> slaserlist = new ArrayList<SLaser>();
	public int accuracy, timer, maxtime;
	private double mangle;
	private float mmangle, craftX, craftY;
	public boolean isRotating;
	Image image;
	
	public LaserLauncher(float xpos, float ypos, Image image, int accuracy, int time){
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
	
	public void fire(float craftX, float craftY) throws SlickException{
		slaserlist.add(new SLaser(x+craftX,y+craftY, camX+desx, camY+desy, 0));
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

}
