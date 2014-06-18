package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.util.EnumStatus;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class Capacitor extends ShipSystem{
	private float capacity, stored;
	
	public Capacitor(Craft c, float x, float y, int maxhp, int durability, int damageradius, float capacity){
		super(c,x,y,maxhp,durability,damageradius,0f);
		this.capacity = capacity;
		this.stored = 0f;
	}
	
	public void sendPower(float amt, ShipSystem system){
		system.receivePower(amt);
	}
	
	public void receivePower(float amount, Core core){
		this.powerReceived = amount;
		if(amount<=capacity+amount){
			capacity+=amount;
		}
	}
	
	public float getStoredEnergy(){
		return stored;
	}
}
