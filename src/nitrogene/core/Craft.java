package nitrogene.core;

import java.util.ArrayList;

import nitrogene.gui.AnimationManager;
import nitrogene.gui.Sprite;
import nitrogene.gui.AnimationImage;
import nitrogene.inventory.Item;
import nitrogene.npc.NPCship;
import nitrogene.objecttree.PhysicalObject;
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
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

public class Craft extends PhysicalObject{
	
	//systems
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
	public Craft(float xpos, float ypos) throws SlickException{
		super(xpos, ypos);
		setDefaultMovement("angled");
		systems = new ArrayList<ShipSystem>(30);
		inventory = new ArrayList<Item>();
		hull = 100;
		maxhull = 100;
		shield = new Shield(this,82,45,300,2,50,100f);
		delta = 0;
		engine = new Engine(this,48,77, 200,2,20,/*warpchage */ 100,50,100f);
		core = new Core(this,82,83, 1000,5,50,500f); 
		capacitor = new Capacitor(this,100,100, 1000,5,20,100000f);
		lifesupport = new LifeSupport(this,82,125, 200,2,50,100f);
		systems.add(lifesupport);
		systems.add(engine);
		systems.add(shield);
		cumulative = 0;
		maxWeapons = 6;
	}
	@Override
	public void load(String img, float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = new Sprite(img);
		boundbox = GlobalInformation.getHitbox(mainimg.getResourceReference());
		if(boundbox == null){
			Resources.log(mainimg.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(mainimg.getImage().getWidth(), mainimg.getImage().getHeight());
		newboundbox = new Polygon(boundbox.getPoints());
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
		
		addSlot((int)(250*scalefactor), (int)(30*scalefactor));
		addSlot((int)(174*scalefactor), (int)(30*scalefactor));
		addSlot((int)(105*scalefactor), (int)(17*scalefactor));
		addSlot((int)(250*scalefactor), (int)(136*scalefactor));
		addSlot((int)(174*scalefactor), (int)(136*scalefactor));
		addSlot((int)(105*scalefactor), (int)(136*scalefactor));
		
		shield.load("shieldsystemicon", 1f, map);
		engine.load("enginesystemicon", 1f, map);
		core.load("coresystemicon", 1f, map);
		lifesupport.load("oxygensystemicon", 1f, map);
		capacitor.load("coresystemicon", 1f, map);
		renderSystems();
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
				LaserLauncher temp = new LaserLauncher(this, weaponSlots.get(i)[0], weaponSlots.get(i)[1], weapons.get(i), i, weapons.get(i).formalname, (short)(i+5));
				temp.load(map);
				TickSystem.addTimer(new WeaponTimer(temp));
				laserlist.add(temp);
			}
		}
	}
	public void destroyWeapons() {
		for(int i = 0; i < laserlist.size(); i++) {
			laserlist.get(i).getTimer().stop();
		}
			
	}
	
	@Override
	public void update(int delta, float camX, float camY)
	{
		//System.out.println("destroyed " + destroyed);
		//Send power to systems based on priorities
		core.sendPower(capacitor);
		for(ShipSystem system : this.systems){
			if(system != null && system.getEnabled() &&
				capacitor.getStoredEnergy() >= system.getPowerUsage() && system.getStatus()!=EnumStatus.DESTROYED){
			capacitor.sendPower(system.getPowerUsage(), system);
			}
		}
		
		this.getSprite().setRotation(this.angledmovement.getRotationAngle());
		
		moveAngled(20, delta);
		
		float carryx = this.getX();
		float carryy = this.getY();
		this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform((float)Math.toRadians(rotation),174,88)));
		this.getBoundbox().setCenterX(this.getCenterX()+43);//43
		this.getBoundbox().setCenterY(this.getCenterY()+16);//16
		this.setX(carryx);
		this.setY(carryy);
		rotateSystem(this.core);
		rotateSystem(this.shield);
		rotateSystem(this.engine);
		rotateSystem(this.lifesupport);
		if(hull<=0){
			this.destroy();
			//GAME OVER
		}
	}
	
	public void renderSystems() {
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
		s.getSprite().setRotation(this.getRotation());
		double[] coords = getRotatedCoordinates(s.getLockedX(), s.getLockedY());
		s.setX((float) (this.getX()+this.width/2+coords[0]));
		s.setY((float) (this.getY()+this.height/2+coords[1]));
	}
	public double[] getRotatedCoordinates(double x, double y) {

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
