package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LifeSupport extends ShipSystem{
	private int percentoxygen;
	private int maxhp;

	public LifeSupport(Craft c, float x, float y, int maxhp, int durability, int damageradius, float powerNeeded) throws SlickException {
		super(c, x, y, maxhp, durability, damageradius, powerNeeded);
		this.maxhp = maxhp;
	}
	
	//REMEMBER: TICK TO UPDATE OXYGEN
	//above 30% health: gain oxygen, under 10%: lose oxygen, in middle: neutral
	public void tick(){
		double ll = getHp()/maxhp;
		if(ll > 0.3){
			if(percentoxygen == 100) percentoxygen = 100;
			else percentoxygen++;
		}
		else if(ll<0.1)percentoxygen--;
	}
	
	public int getOxygen(){
		return percentoxygen;
	}
	
	public void damage(int damage){
		setHp(getHp()-damage);
		if(getHp() > maxhp) setHp(maxhp);
	}
	
	public void repair(int repair){
		setHp(getHp()+repair);
		if(getHp() < 0) setHp(0);
	}
	
}
