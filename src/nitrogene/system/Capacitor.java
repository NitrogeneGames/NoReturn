package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class Capacitor extends ShipSystem{
	
	public Capacitor(Craft c, float x, float y, int maxhp, int durability, int maxpower, int capacity, int damageradius, short priority){
		super(c,x,y,maxhp,durability,maxpower,capacity,damageradius,priority);
	}
	
	
}
