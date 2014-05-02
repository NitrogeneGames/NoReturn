package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Core extends ShipSystem{
	private int maxpower;
	private int maxhp;
	private int powerRate;

	public Core(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int damageradius, int powerRate) throws SlickException {
		super(c,x,y,img, map, maxhp, durability, maxpower, capacity, damageradius, (short)0);
		this.maxpower = maxpower;
		this.powerRate = powerRate;
	}
	
	//Core under 30% health stars to 
	public int getPower(){
		double zz = getHp()/maxhp;
		if(zz<0.3) return (int) (zz*3.33*maxpower);
		else return maxpower;
	}
	
	public void damage(int damage){
		setHp(getHp()-damage);
		if(getHp() > maxhp) setHp(maxhp);
	}
	
	public void repair(int repair){
		setHp(getHp()+repair);
		if(getHp() < 0) setHp(0);
	}
	public int getPowerRate(){
		return this.powerRate;
	}
}
