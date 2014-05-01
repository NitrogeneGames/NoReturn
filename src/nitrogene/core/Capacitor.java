package nitrogene.core;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class Capacitor extends ShipSystem{
	
	public Capacitor(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius, short priority){
		super(c,x,y,img,map,maxhp,durability,maxpower,capacity,damageradius,priority);
	}
	
	
}
