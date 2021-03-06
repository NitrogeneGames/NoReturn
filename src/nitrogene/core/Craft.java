package nitrogene.core;

import java.util.ArrayList;

import nitrogene.gui.AnimationManager;
import nitrogene.gui.Sprite;
import nitrogene.gui.AnimationImage;
import nitrogene.inventory.Item;
import nitrogene.npc.NPCship;
import nitrogene.system.Capacitor;
import nitrogene.system.Core;
import nitrogene.system.Engine;
import nitrogene.system.LifeSupport;
import nitrogene.system.Shield;
import nitrogene.system.ShipSystem;
import nitrogene.util.AngledMovement;
import nitrogene.util.EnumHull;
import nitrogene.util.EnumStatus;
import nitrogene.util.Movement;
import nitrogene.util.TickSystem;
import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.EnumWeapon;
import nitrogene.weapon.WeaponTimer;
import nitrogene.world.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

//Instance of a ship in the game
//Craft can be used for any craft instance, can also be extended for specific uses (NPCShip for example)

public class Craft extends GameObject{
	
	//systems
	public String img;
	public Shield shield;
	public LifeSupport lifesupport;
	public Core core;
	public Capacitor capacitor;
	public EnumHull hulltype = EnumHull.NORMAL;
	public Engine engine;
	protected ArrayList<ShipSystem> systems;
	public ArrayList<LaserLauncher> laserlist = new ArrayList<LaserLauncher>();
	protected double hull, maxhull;
	public int maxWeapons;
	public ArrayList<int[]> weaponSlots = new ArrayList<int[]>();
	private ArrayList<Item> inventory;
	protected int cumulative;
	public String name = "";
	public float hullPercent = 100;
	public float shieldPercent = 100;
	

	public Craft(World world, float xpos, float ypos, String img, float scale) throws SlickException{
		//Main constructor
		//Takes the world instance, a location, image, and scalefactor as arguments
		super(world, xpos, ypos);
		this.scalefactor = scale;
		this.img = img;
		setDefaultMovement("angled");
		systems = new ArrayList<ShipSystem>(30);
		inventory = new ArrayList<Item>();
		hull = 100;
		maxhull = 100;
		delta = 0;
		//Create systems
		shield = new Shield(this,0,0,300,2,50,100f);
		engine = new Engine(this,0,0, 200,2,20,/*warpchage */ 100,50,100f);
		core = new Core(this,0,0, 1000,5,50,500f); 
		capacitor = new Capacitor(this,0,0, 1000,5,20,100000f);
		lifesupport = new LifeSupport(this,0,0, 200,2,50,100f);
		systems.add(lifesupport);
		systems.add(engine);
		systems.add(shield);
		systems.add(core);
		systems.add(capacitor);
		cumulative = 0;
		maxWeapons = 6;
	}

	@Override
	public void load(String img, float scalefactor, World map){
		//Called after assets are loaded so that the image is not null
		//Will properly load the sprite and boundbox
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = new Sprite(img);
		
		//Create boundbox
		boundbox = GlobalInformation.getHitbox(mainimg.getResourceReference());
		if(boundbox == null){
			Resources.log(mainimg.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		
		//Initialize movement and rotation information
		init(mainimg.getImage().getWidth(), mainimg.getImage().getHeight());
		centerOfRotation = new Point(getSprite().getImage().getCenterOfRotationX(),getSprite().getImage().getCenterOfRotationY());
		newboundbox = new Polygon(boundbox.getPoints());
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
		
		//Add weapon slots for different sprites
		if(this.getSprite().getResourceReference() == "res/klaarship6.png") {
			addSlot((int)(250*scalefactor), (int)(30*scalefactor));
			addSlot((int)(174*scalefactor), (int)(30*scalefactor));
			addSlot((int)(105*scalefactor), (int)(17*scalefactor));
			addSlot((int)(250*scalefactor), (int)(136*scalefactor));
			addSlot((int)(174*scalefactor), (int)(136*scalefactor));
			addSlot((int)(105*scalefactor), (int)(136*scalefactor));
			shield.setLocation(82,45);
			engine.setLocation(48,77);
			core.setLocation(82,83); 
			capacitor.setLocation(100,100);
			lifesupport.setLocation(82,125);
			
		} else if(this.getSprite().getResourceReference() == "res/humanship4.png") {
			addSlot((int)(35*scalefactor), (int)(22*scalefactor));
			addSlot((int)(35*scalefactor), (int)(168*scalefactor));
			addSlot((int)(94*scalefactor), (int)(22*scalefactor));
			addSlot((int)(94*scalefactor), (int)(168*scalefactor));
			addSlot((int)(160*scalefactor), (int)(47*scalefactor));
			addSlot((int)(160*scalefactor), (int)(143*scalefactor));
			shield.setLocation(43,70);
			engine.setLocation(43,120);
			core.setLocation(80,70); 
			capacitor.setLocation(120,95);
			lifesupport.setLocation(80,120);
		}
		
		//Call load function for all subsystems of the ship, since their load function is not called
		//by GameState
		shield.load("shieldsystemicon", 1f, map);
		engine.load("enginesystemicon", 1f, map);
		core.load("coresystemicon", 1f, map);
		lifesupport.load("oxygensystemicon", 1f, map);
		capacitor.load("coresystemicon", 1f, map);
		renderSystems();
	}
	public CraftData formData() {
		//Create a CraftData representing the craft and its weapons
		ArrayList<EnumWeapon> enums = new ArrayList<EnumWeapon>();
		for(int i = 0; i<laserlist.size(); i++) {
			enums.add(laserlist.get(i).enumtype);
		}
		return new CraftData(enums, this.name, hulltype);
	}
	public void addSlot(int x, int y) {
		//Add a weapon slot
		weaponSlots.add(new int[] {x, y});
	}
	public void loadWeapons(ArrayList<EnumWeapon> weapons) throws SlickException{
		//Call constructors for each weapon, then add to the laserlist
		if(weapons != null){
			for(int i = 0; i < weapons.size(); i++) {
				LaserLauncher temp = new LaserLauncher(this, weaponSlots.get(i)[0], weaponSlots.get(i)[1], weapons.get(i), i, weapons.get(i).formalname, (short)(i+5));
				temp.load(map);
				TickSystem.addTimer(new WeaponTimer(temp));
				laserlist.add(temp);
			}
		}
	}
	public void destroyWeapons() {
		//Stop all weapons on the craft
		for(int i = 0; i < laserlist.size(); i++) {
			laserlist.get(i).getTimer().stop();
		}
			
	}
	
	@Override
	public void update(int delta, float camX, float camY)
	{
		//Function called each tick to peform calculations
		
		
		//System.out.println("destroyed " + destroyed);
		//Send power to systems based on priorities
		
		//Power ship systems
		core.sendPower(capacitor);
		for(ShipSystem system : this.systems){
			if(system != null && system.getEnabled() &&
				capacitor.getStoredEnergy() >= system.getPowerUsage() && system.getStatus()!=EnumStatus.DESTROYED){
			capacitor.sendPower(system.getPowerUsage(), system);
			}
		}
		
		//Update the image to be properly rotated
		this.getSprite().setRotation(this.angledmovement.getRotationAngle());
		
		//Move the craft
		moveAngled(20, delta);
		
		//Rotate the boundbox to align with the image
		float carryx = this.getX();
		float carryy = this.getY();
		Point p = this.getCenterOfRotation();
		this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform((float)Math.toRadians(rotation),p.getX(),p.getY())));
		this.getBoundbox().setCenterX(this.getCenterX()+43);//43
		this.getBoundbox().setCenterY(this.getCenterY()+16);//16
		this.setX(carryx);
		this.setY(carryy);
		
		//Rotate all systems properly
		rotateSystem(this.core);
		rotateSystem(this.shield);
		rotateSystem(this.engine);
		rotateSystem(this.lifesupport);
		
		//Check if craft is out of HP, if it is then destroy it
		if(hull<=0){
			hull = 0;
			this.destroy();
			//GAME OVER
		}
		
		//Remove all destroyed lasers
		for(int i = 0; i < laserlist.size(); i++) {
			if(laserlist.get(i).isDestroyed()) {
				laserlist.remove(i);
				i--;
			}
		}
		
		//Update render variables
		shieldPercent = shield.getHp()/shield.getMaxHp();
		hullPercent = (float) (getHull()/getMaxHull());
	}
	
	public void renderSystems() {
		//Draw the ship systems, are already rotated correctly
		if(!destroyed) {
			this.core.getSprite().drawCentered(this.core.getX(),this.core.getY());
			//rotateSystem(this.core);
			
			this.shield.getSprite().drawCentered(this.shield.getX(),this.shield.getY());
			//rotateSystem(this.shield);
			
			
			this.engine.getSprite().drawCentered(this.engine.getX(),this.engine.getY());
			//rotateSystem(this.engine);
			
			this.lifesupport.getSprite().drawCentered(this.lifesupport.getX(),this.lifesupport.getY());
			//rotateSystem(this.lifesupport);
		}
	
	}
	public void rotateSystem(ShipSystem s) {
		//Update new rotated coordinates since the systems are docked on the ship
		s.getSprite().setRotation(this.getRotation());
		double[] coords = getRotatedCoordinates(s.getLockedX(), s.getLockedY());
		s.setX((float) (this.getX()+this.width/2+coords[0]));
		s.setY((float) (this.getY()+this.height/2+coords[1]));
	}
	public double[] getRotatedCoordinates(double x, double y) {
		//Calculate new position relative to the craft's 0, 0
		//Takes a docked position as arguments, then returns the rotated coordinates for docked objects		
		//Uses pythagorean theorem/basic trig
		double currentRotation = -this.getSprite().getImage().getRotation();
		double x1 = this.getCenterX() - x;
		double y1 = this.getCenterY() - y;
		double hypotenuse = Math.sqrt(x1*x1 + y1*y1);
		double theta = -(180 + Math.toDegrees(Math.atan2(y1,x1))); 
		double mangle = theta + currentRotation;
		double x2 = Math.cos(Math.toRadians(mangle)) * hypotenuse;
		double y2 = -Math.sin(Math.toRadians(mangle)) * hypotenuse;
		return new double[] {x2, y2};
	}
	public float getShieldX(){
		return getCenterX() - shield.getSprite().getImage().getWidth()/2*1.2f;
	}
	public float getShieldY(){
		return getCenterY() - shield.getSprite().getImage().getHeight()/2*1.2f;
	}
	public double getHull(){
		return hull;
	}
	public double getMaxHull(){
		return maxhull;
	}
	public void changeHull(double change){
		hull += change;
		/*if(hull<=0){
			this.destroy();
			//GAME OVER
		}*/
	}
	public void damageHull(double change){
		changeHull(-change);
	}
	public void setHull(double change){
		hull = change;
	}
	private void destroy(){
		//Function to cleaen up all variables when the craft is destroyed
		destroyed = true;
		try {
			AnimationManager.createExplosion(this.getRealCenterX(), this.getRealCenterY(), 3f, 100);
		} catch (SlickException e) {
			Resources.log("failed to create explosion animation");
		}
		this.getMovement().forceStop();
		this.mainimg = null;
		this.removeBoundbox();
		destroyWeapons();
		map.getCrafts().remove(this);
	}
	public void addToInventory(ArrayList<Item> itemlist){
		for(Item item: itemlist){
			inventory.add(item);
		}
	}
	public float getHeight() {
		return this.getSprite().getImage().getHeight();
	}
	public float getWidth() {
		return this.getSprite().getImage().getWidth();
	}
	public ArrayList<Item> getInventory(){
		return this.inventory;
	}
}
