package nitrogene.core;

import java.util.ArrayList;

import nitrogene.objecttree.RectangleObject;
import nitrogene.util.TickSystem;
import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.EnumWeapon;
import nitrogene.weapon.WeaponTimer;
import nitrogene.world.ArenaMap;
import nitrogene.world.Item;

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
	public int maxWeapons;
	public ArrayList<int[]> weaponSlots = new ArrayList<int[]>();
	private ArrayList<Item> inventory;
	protected volatile boolean destroyed = true;
	protected int cumulative;
	
	public Craft(float xpos, float ypos, Image img, float scale, ArenaMap map) throws SlickException{
		super(img.getWidth(), img.getHeight(), img.copy(), scale, map);
		inventory = new ArrayList<Item>();
		this.setX(700);
		this.setY(700);
		hull = 100;
		shield = new Shield(82,45,new Image("res/icon/shieldsystem.png"), map, 300,2,30,1000,1,50);
		delta = 0;
		engine = new Engine(48,77,new Image("res/icon/enginesystem.png"), map, 200,2,20,1000,20,/*warpchage */ 100,50);
		core = new Core(82,83,new Image("res/icon/coresystem.png"), map, 1000,5,100,2000,50); 
		lifesupport = new LifeSupport(82,125,new Image("res/icon/oxygensystem.png"),map,200,2,5,1000,50);
		cumulative = 0;
		maxWeapons = 6;
		addSlot((int)(250*scale), (int)(30*scale));
		addSlot((int)(174*scale), (int)(30*scale));
		addSlot((int)(105*scale), (int)(17*scale));
		addSlot((int)(250*scale), (int)(136*scale));
		addSlot((int)(174*scale), (int)(136*scale));
		addSlot((int)(105*scale), (int)(136*scale));
	}
	public void addSlot(int x, int y) {
		weaponSlots.add(new int[] {x, y});
	}
	public void loadSystems(ArrayList<EnumWeapon> weapons){
		if(weapons != null){
			for(int i = 0; i < weapons.size(); i++) {
				LaserLauncher temp = new LaserLauncher(this, map, weaponSlots.get(i)[0], weaponSlots.get(i)[1], weapons.get(i), i, weapons.get(i).formalname);
				TickSystem.addTimer(new WeaponTimer(temp));
				laserlist.add(temp);
			}
			/*
			if(weapons.size() >){
				primary3 = new LaserLauncher(this, map, 135, 17, weapons.get(2));
				TickSystem.addTimer(new WeaponTimer(primary3));
				laserlist.add(primary3);
			}
			if(weapons.get(3) != null){
				primary4 = new LaserLauncher(this, map, 135, 17, weapons.get(3));
				TickSystem.addTimer(new WeaponTimer(primary4));
				laserlist.add(primary4);
			}
			if(weapons.get(4) != null){
				primary5 = new LaserLauncher(this, map, 135, 17, weapons.get(4));
				TickSystem.addTimer(new WeaponTimer(primary5));
				laserlist.add(primary5);
			}
			if(weapons.get(5) != null){
				primary6 = new LaserLauncher(this, map, 135, 17, weapons.get(5));
				TickSystem.addTimer(new WeaponTimer(primary6));
				laserlist.add(primary6);
			}
			*/
			
		}
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
		
		this.mainimg.setRotation(0);
		this.mainimg.rotate(this.movement.getRotationAngle());
		move(20, delta);
	}
	
	public void renderSystems() {
		this.shield.getImage().draw(this.getShieldX(),this.getShieldY(),1.2f);
		//rotateSystem(this.shield);
		//systems
		this.core.getImage().drawCentered(this.core.getX()+this.getX(),this.core.getY()+this.getY());
		rotateSystem(this.core);
		
		this.shield.getImage().drawCentered(this.shield.getX()+this.getX(),this.shield.getY()+this.getY());
		rotateSystem(this.shield);
		
		
		this.engine.getImage().drawCentered(this.engine.getX()+this.getX(),this.engine.getY()+this.getY());
		rotateSystem(this.engine);
		
		this.lifesupport.getImage().drawCentered(this.lifesupport.getX()+this.getX(),this.lifesupport.getY()+this.getY());
		rotateSystem(this.lifesupport);
	
	}
	public void rotateSystem(ShipSystem s) {
		s.getImage().setRotation(this.mainimg.getRotation());
		float x1 = (s.getCenterX() - this.getCenterX());
		float y1 = (s.getCenterY() - this.getCenterY());
		float mangle = (float) Math.atan(y1/x1);
		//System.out.println(mangle + " ANGLE"); //SHOWS 2 different numbers, idk why
		float radius = (float) Math.sqrt(x1*x1 + y1*y1);
		//System.out.println(radius + " RADIUS");
		float x2 = (float) (radius * Math.cos(-mangle + this.mainimg.getRotation()));
		float y2 = (float) (radius * Math.sin(-mangle + this.mainimg.getRotation())); 
		s.setCenterX(x2);
		s.setCenterY(y2);
	}
	public float getShieldX(){
		return getCenterX() - shield.getImage().getWidth()/2*1.2f;
	}
	public float getShieldY(){
		return getCenterY() - shield.getImage().getHeight()/2*1.2f;
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
	
	public void addToInventory(ArrayList<Item> itemlist){
		for(Item item: itemlist){
			inventory.add(item);
		}
	}
}
