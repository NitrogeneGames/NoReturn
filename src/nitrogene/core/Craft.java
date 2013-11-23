package nitrogene.core;

import java.util.ArrayList;

import nitrogene.collision.AABB;
import nitrogene.collision.Vector;
import nitrogene.objecttree.RectangleObject;
import nitrogene.util.Direction;
import nitrogene.util.Movement;
import nitrogene.util.TickSystem;
import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.EnumWeapon;
import nitrogene.weapon.WeaponTimer;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Craft extends RectangleObject{
	
	//systems
	public Shield shield;
	public LifeSupport lifesupport;
	public Core core;
	public Engine engine;
	public ArrayList<LaserLauncher> laserlist = new ArrayList<LaserLauncher>();
	protected double hull;
	LaserLauncher primary1;

	protected volatile boolean destroyed = true;
	protected int cumulative;
	
	public Craft(float xpos, float ypos, Image img, float scale, ArenaMap map) throws SlickException{
		super(xpos, ypos, img.getWidth(), img.getHeight(), img, scale, map);

		hull = 100;
		shield = new Shield(82,45,new Image("res/icon/shieldsystem.png"), map, 300,2,30,1000,1,50);
		delta = 0;
		engine = new Engine(48,77,new Image("res/icon/enginesystem.png"), map, 200,2,20,1000,20,/*warpchage */ 100,50);
		core = new Core(82,83,new Image("res/icon/coresystem.png"), map, 1000,5,100,2000,50); 
		lifesupport = new LifeSupport(82,125,new Image("res/icon/oxygensystem.png"),map,200,2,5,1000,50);
		cumulative = 0;
		
		primary1 = new LaserLauncher(this, map, 135, 17, EnumWeapon.BASIC);
		TickSystem.addTimer(new WeaponTimer(primary1));
		laserlist.add(primary1);
	}
	
	@Override
	public void update(int delta)
	{
		//Clock
		if(cumulative >= 1000){
			//1 second cumulative
			lifesupport.tick();
			cumulative = 0;
		}
		
		move(20, delta);
	}
	

	public float getShieldX(){
		return boundbox.getCenterX() - shield.getImage().getWidth()/2*1.2f;
	}
	public float getShieldY(){
		return boundbox.getCenterY() - shield.getImage().getHeight()/2*1.2f;
	}
	public double getHull(){
		return hull;
	}
	public void changeHull(double change){
		hull += change;
		if(hull<=0){
			this.destroy();
			//GAME OVER
		}
	}
	public void setHull(double change){
		hull = change;
		if(hull<=0){
			this.destroy();
			//GAME OVER
		}
	}
	
	private void destroy(){
		//EXPLOSION!
	}
}
