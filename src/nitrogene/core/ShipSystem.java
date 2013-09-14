package nitrogene.core;

import nitrogene.collision.Circle;
import nitrogene.collision.CollisionLibrary;

import org.newdawn.slick.Image;

public class ShipSystem {
	private int hp, durability, maxpower, capacity;
	private float x,y;
	private Image im;
	private Circle damagebox;
	
	public ShipSystem(float x, float y, int maxhp, int durability, int maxpower, int capacity, int damageradius){
		this.hp = maxhp;
		this.durability = durability;
		this.maxpower = maxpower;
		this.capacity = capacity;
		this.x = x;
		this.y = y;
		damagebox = new Circle(damageradius);
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
		if(CollisionLibrary.testCirclePoint(damagebox, x, y)){
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
	public float getX(){
		return x;
	}
	public float getY(){
	return y;
	}
	public void setImage(Image im){
		this.im = im;
	}
	public Image getImage(){
		return im;
	}
}
