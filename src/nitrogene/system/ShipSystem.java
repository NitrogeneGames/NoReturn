package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.core.GlobalInformation;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AngledMovement;
import nitrogene.util.EnumStatus;
import nitrogene.util.Movement;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

public class ShipSystem extends PhysicalObject{
	protected int hp;
	private int durability;
	private int maxpower;
	protected int maxhp;
	private EnumStatus status;
	protected int functionality; //1-10 for how functional the system is.
	public Craft craft;
	private float x1;
	private float y1;
	private float m;
	private float r;
	protected float powerusage;
	protected float powerReceived;
	private float powerNeeded;
	private boolean enabled;
	public float[] getRelations() {
		return new float[]{m,r};
	}
	//The damagebox for damage collision/proximity is the boundbox of CircleObject
	
	public ShipSystem(Craft c, float x, float y, int maxhp, int durability, int damageradius, float powerNeeded){
		super(x,y);
		setDefaultMovement("normal");
		this.hp = maxhp;
		this.maxhp = maxhp;
		this.durability = durability;
		this.powerNeeded = powerNeeded;
		this.craft = c;
		x1 = x;
		y1 = y;
		//x1 = -(craft.getX()+(craft.getBoundbox().getWidth()*craft.getScale()) - (x+img.getWidth()*this.getScale()));
		//y1 = -(craft.getY()+(craft.getBoundbox().getHeight()*craft.getScale()) - (y+img.getHeight()*this.getScale()));
		if(y1 <= 0) {
			m = (float) Math.atan2((double) y1,(double) x1);
		} else {
			m = (float) Math.atan2((double) y1, (double) x1) + (float) Math.PI;
		}
		//System.out.println(mangle + " ANGLE"); //SHOWS 2 different numbers, idk why
		r = (float) Math.sqrt(x1*x1 + y1*y1);
	}
	public float rotation = 0;
	
	public void load(Image img, float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = img;
		boundbox = GlobalInformation.getImageData().get(img.getResourceReference());
		if(boundbox == null){
			System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(img.getWidth(), img.getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
	}
	
	@Override
	public void update(int delta, float camX, float camY){
		
		if(hp <= 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else if(hp<maxhp){
			this.setStatus(EnumStatus.DAMAGED);
		} else if(this.functionality<10){
			this.setStatus(EnumStatus.POWER);
		} else{
			this.setStatus(EnumStatus.READY);
		}
	}
	
	//THIS FUNCTION IS REQUIRED (EVEN IF AMOUNT IS 0) -> ALL SYSTEMS MUST RECEIVE POWER
	public void receivePower(float amount){
		this.powerReceived = amount;
		if(amount < powerusage){
			this.functionality = (int)Math.round((amount/powerusage)*10);
		} else if(amount > powerusage){
			this.hp-=1;
		}
	}
	public int getHp(){
		return hp;
	}
	public void setHp(int hp){
		this.hp = hp;
	}
	public int getMaxHp(){
		return this.maxhp;
	}
	public void registerHit(float x, float y, int damage){
		//call when ship boundbox is hit by projectile
		//register explosion elsewhere (this just registers the damage to the system)
		if(this.isContaining(x,y)){
			this.setHp(getHp()-damage);
		}
	}
	public int getDurability(){
		return durability;
	}
	public int getMaxPower(){
		return maxpower;
	}
	public float getX1(){
		return x1;
	}
	public float getY1(){
		return y1;
	}
	public float getShipAngle(){
		return m;
	}
	public float getShipRadius() {
		return r;
	}
	public void setStatus(EnumStatus status){
		this.status = status;
	}
	public EnumStatus getStatus(){
		return status;
	}
	public float getPowerUsage(){
		return powerusage;
	}
	public float getPowerReceived(){
		return powerReceived;
	}
	public void setEnabled(boolean e){
		enabled = e;
	}
	public boolean getEnabled(){
		return enabled;
	}
}
