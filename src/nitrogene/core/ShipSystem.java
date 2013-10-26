package nitrogene.core;

import nitrogene.collision.Circle;
import nitrogene.collision.CollisionLibrary;
import nitrogene.objecttree.CircleObject;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class ShipSystem extends CircleObject{
	private int hp, durability, maxpower, capacity;
	
	//The damagebox for damage collision/proximity is the boundbox of CircleObject
	
	public ShipSystem(float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius){
		super(x,y, damageradius, img, 1, map);
		this.hp = maxhp;
		this.durability = durability;
		this.maxpower = maxpower;
		this.capacity = capacity;
	}
	
	public int getHp(){
		return hp;
	}
	public void setHp(int hp){
		this.hp = hp;
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
}
