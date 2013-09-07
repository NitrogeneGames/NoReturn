package nitrogene.core;

public class ShipSystem {
	private int hp, durability, maxpower, capacity;
	
	public ShipSystem(int maxhp, int durability, int maxpower, int capacity){
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
