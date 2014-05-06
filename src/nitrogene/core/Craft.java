package nitrogene.core;

import java.util.ArrayList;

import nitrogene.objecttree.RectangleObject;
import nitrogene.system.Core;
import nitrogene.system.Engine;
import nitrogene.system.LifeSupport;
import nitrogene.system.Shield;
import nitrogene.system.ShipSystem;
import nitrogene.util.EnumHull;
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
	public EnumHull hulltype = EnumHull.NORMAL;
	public Engine engine;
	public ArrayList<LaserLauncher> laserlist = new ArrayList<LaserLauncher>();
	protected double hull;
	public int maxWeapons;
	public ArrayList<int[]> weaponSlots = new ArrayList<int[]>();
	private ArrayList<Item> inventory;
	protected volatile boolean destroyed = true;
	protected int cumulative;
	public String name = "";
	public Craft(float xpos, float ypos, Image img, float scale, ArenaMap map) throws SlickException{
		super(img.getWidth(), img.getHeight(), img.copy(), scale, map);
		inventory = new ArrayList<Item>();
		this.setX(700);
		this.setY(700);
		hull = 100;
		shield = new Shield(this,82,45,new Image("res/icon/shieldsystem.png"), map, 300,2,30,1000,1,50,(short)1);
		delta = 0;
		engine = new Engine(this,48,77,new Image("res/icon/enginesystem.png"), map, 200,2,20,1000,20,/*warpchage */ 100,50,(short)2);
		core = new Core(this,82,83,new Image("res/icon/coresystem.png"), map, 1000,5,100,2000,50,50); 
		lifesupport = new LifeSupport(this,82,125,new Image("res/icon/oxygensystem.png"),map,200,2,5,1000,50,(short)3);
		cumulative = 0;
		maxWeapons = 6;
		addSlot((int)(250*scale), (int)(30*scale));
		addSlot((int)(174*scale), (int)(30*scale));
		addSlot((int)(105*scale), (int)(17*scale));
		addSlot((int)(250*scale), (int)(136*scale));
		addSlot((int)(174*scale), (int)(136*scale));
		addSlot((int)(105*scale), (int)(136*scale));
	}
	public CraftData formData() {
		ArrayList<EnumWeapon> enums = new ArrayList<EnumWeapon>();
		for(int i = 0; i<laserlist.size(); i++) {
			enums.add(laserlist.get(i).enumtype);
		}
		return new CraftData(enums, this.name, hulltype);
	}
	public void addSlot(int x, int y) {
		weaponSlots.add(new int[] {x, y});
	}
	public void loadWeapons(ArrayList<EnumWeapon> weapons) throws SlickException{
		if(weapons != null){
			for(int i = 0; i < weapons.size(); i++) {
				LaserLauncher temp = new LaserLauncher(this, map, weaponSlots.get(i)[0], weaponSlots.get(i)[1], weapons.get(i), i, weapons.get(i).formalname, (short)(i+4));
				TickSystem.addTimer(new WeaponTimer(temp));
				laserlist.add(temp);
			}
		}
	}
	
	@Override
	public void update(int delta)
	{
		//Send power to systems based on priorities
		
		//Clock
		if(cumulative >= 1000){
			//1 second cumulative
			lifesupport.tick();
			cumulative = 0;
		}
		
		this.mainimg.setRotation(0);
		this.mainimg.setRotation(this.movement.getRotationAngle());
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
		if(s.rotation != this.getMovement().getRotationAngle()) {
		s.getImage().setRotation(this.mainimg.getRotation());
		double mangle = (double) s.getShipAngle();
		double radius = (double) s.getShipRadius();
		System.out.println("center: " + this.getCenterX() + ", " + this.getCenterY());
		System.out.println("imagecenter: " + this.getImage().getCenterOfRotationX() + ", " + this.getImage().getCenterOfRotationY());
		//float mangle = s.getRelations()[0];
		//float radius = s.getRelations()[1];
		double x2 = (double) (radius * Math.cos(mangle + Math.toRadians(this.getMovement().getRotationAngle()-s.rotation)));
		double y2 = (double) (radius * Math.sin(mangle + Math.toRadians(this.getMovement().getRotationAngle()-s.rotation)));
		//s.rotation = this.getMovement().getRotationAngle();
		s.setCenterX((float)(this.getImage().getCenterOfRotationX() + x2));
		s.setCenterY((float)(this.getImage().getCenterOfRotationY() + y2));
		}

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
	
	public ArrayList<Item> getInventory(){
		return this.inventory;
	}
}
