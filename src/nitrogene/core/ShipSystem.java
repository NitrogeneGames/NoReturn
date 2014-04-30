package nitrogene.core;

import nitrogene.collision.Circle;
import nitrogene.collision.CollisionLibrary;
import nitrogene.objecttree.CircleObject;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.EnumStatus;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class ShipSystem extends CircleObject{
	private int hp, durability, maxpower, capacity, maxhp;
	private EnumStatus status;
	public Craft craft;
	private float x1;
	private float y1;
	private float m;
	private float r;
	public float[] getRelations() {
		return new float[]{m,r};
	}
	//The damagebox for damage collision/proximity is the boundbox of CircleObject
	
	public ShipSystem(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius){
		super(x,y, damageradius, img, 1, map);
		this.hp = maxhp;
		this.maxhp = maxhp;
		this.durability = durability;
		this.maxpower = maxpower;
		this.capacity = capacity;
		this.craft = c;
		x1 = -(craft.getCenterX() - this.getCenterX());
		y1 = -(craft.getCenterY() - this.getCenterY());
		m = (float) Math.atan(y1/x1);
		//System.out.println(mangle + " ANGLE"); //SHOWS 2 different numbers, idk why
		r = (float) Math.sqrt(x1*x1 + y1*y1);
	}
	public float rotation = 0;
	
	@Override
	public void update(int delta){
		if(hp <= 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else{
			this.setStatus(EnumStatus.READY);
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
	public int getMaxpower(){
		return maxpower;
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
}
