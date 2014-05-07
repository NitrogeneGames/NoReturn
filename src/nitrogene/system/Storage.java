package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;

public class Storage extends ShipSystem{

	public Storage(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius, short priority){
		super(c,x,y,img,map,maxhp,durability,maxpower,capacity,damageradius,priority);
		
	}
	
}
