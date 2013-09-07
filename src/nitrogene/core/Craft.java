package nitrogene.core;

import java.util.ArrayList;

import nitrogene.collision.AABB;
import nitrogene.util.Direction;
import nitrogene.util.Movement;
import nitrogene.weapon.LaserLauncher;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Craft {
	
	//systems
	public Shield shield;
	public LifeSupport lifesupport;
	public Core core;
	public Engine engine;
	public ArrayList<LaserLauncher> laserlist = new ArrayList<LaserLauncher>();
	LaserLauncher primary1;

	private Image craftimage = null, laser1;
	public Movement movement;
	private float x = 0, y = 0;
	public AABB boundbox = null;
	private int delta, cumulative;
	public Craft(float xpos, float ypos) throws SlickException{
		craftimage = new Image("res/klaarship4.png");
		laser1 = new Image("res/laser1.png");
		x = xpos;
		y = ypos;
		boundbox = new AABB(craftimage.getWidth(), craftimage.getHeight());
		updateAABB(x,y);
		shield = new Shield(1);
		delta = 0;
		movement = new Movement();
		engine = new Engine(200,2,20,1000,20,/*warpchage */ 100);
		core = new Core(500,5,100,2000);
		lifesupport = new LifeSupport(200,2,5,1000);
		cumulative = 0;
		
		primary1 = new LaserLauncher(135, 17, laser1, 20, 1000);
		laserlist.add(primary1);
	}
	
	public void updateAABB(float xpos, float ypos){
		boundbox.update(boundbox.getCenter(xpos,ypos));
	}
	
	public void update(int delta, float camX, float camY)
	{
		this.delta = delta;
		cumulative += delta;
		
		//Clock
		if(cumulative >= 1000){
			//1 second cumulative
			lifesupport.tick();
			cumulative = 0;
		}
		
		
		movement.Accelerate();
	}
	public void setX(float x1){
		x = x1;
	}
	public void setY(float y1){
		y = y1;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public Image getImage(){
		return craftimage;
	}
	public float getShieldX(){
		return boundbox.center.x - shield.getImage().getWidth()/2;
	}
	public float getShieldY(){
		return boundbox.center.y - shield.getImage().getHeight()/2;
	}
	
	//# is the constant
	public void move(int thrust){
		float mm = delta/1000f;
		float gj = thrust*1f;
		y += ((movement.getDy()*gj)*mm);
		x += ((movement.getDx()*gj)*mm);
	}
}
