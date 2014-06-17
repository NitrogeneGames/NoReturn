package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.EnumStatus;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class ShipSystem extends PhysicalObject{
	private int hp, durability, maxpower, capacity, maxhp;
	private EnumStatus status;
	public Craft craft;
	private long powerstorage;
	private float x1;
	private float y1;
	private float m;
	private float r;
	private short priority;
	public float[] getRelations() {
		return new float[]{m,r};
	}
	//The damagebox for damage collision/proximity is the boundbox of CircleObject
	
	public ShipSystem(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius, short priority){
		super(x,y);
		setDefaultMovement("normal");
		this.hp = maxhp;
		this.powerstorage = 0;
		this.maxhp = maxhp;
		this.durability = durability;
		this.maxpower = maxpower;
		this.capacity = capacity;
		this.craft = c;
		this.priority = priority;
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
	
	@Override
	public void update(int delta, float camX, float camY){
		
		if(hp <= 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else{
			this.setStatus(EnumStatus.READY);
		}
	}
	
	//THIS FUNCTION IS REQUIRED (EVEN IF AMOUNT IS 0) -> ALL SYSTEMS MUST RECEIVE POWER
	public void sendPower(int amount){
		if(capacity+amount <= capacity && maxpower <= amount){
			powerstorage += amount;
		} else if(maxpower > amount){
			this.hp-=1;
			//Damage the system for providing too much power
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
	public int getCapacity(){
		return capacity;
	}
	public void setStatus(EnumStatus status){
		this.status = status;
	}
	public EnumStatus getStatus(){
		return status;
	}
	public long getPowerStorage(){
		return powerstorage;
	}
	public boolean acquirePower(int amount) {
		if(this.capacity>=amount){
			this.capacity -= amount;
			return true;
		} else{
			return false;
		}
	}
}
