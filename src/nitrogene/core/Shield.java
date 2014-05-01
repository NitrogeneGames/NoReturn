package nitrogene.core;

import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends ShipSystem{
	
	private int damageInd;
	public float shieldPercentage;
	public int shieldQuality;
	
	public Shield(Craft c, float x, float y, Image img, ArenaMap map, int maxhp, int durability, int maxpower, int capacity, int shieldQuality, int damageradius, short priority) throws SlickException{
		super(c, x, y, img, map, maxhp, durability, maxpower, capacity, damageradius, priority);
		damageInd = 0;
		shieldPercentage = 100;
		this.shieldQuality = shieldQuality;
	}
	
	/*
	 * damageInd: 0-blocked 1-tremor 2-pierced
	 * shieldQuality: 1-Normal 2-Good 3-Extreme
	 */
	public int damage(double damage){
		damage *= 15/shieldQuality;
		damageInd = 0;
		if(shieldPercentage == 0){
			damageInd = 2;
		}
		if(shieldPercentage < damage || shieldPercentage == (float) damage){
			damageInd = 1;
			shieldPercentage = 0;
		}
		if(shieldPercentage > damage){
			shieldPercentage -= damage;
		}
		changeOpac(shieldPercentage);
		return damageInd;
	}
	
	/*
	 * Shield Recharge based on health given in percent, and multiplies by shield quality
	 * max percentage = 100
	 */
	public void recharge(double health){
		if (shieldPercentage <= 100){
		shieldPercentage += health * shieldQuality;
		}
		if (shieldPercentage > 100){
			shieldPercentage = 100;
		}
	}
	/*
	 * Input a percentage to define the shield level.
	 * Use public shieldPercentage * 10 to find the percentage of the sheilds in percent (0-100%)
	 */
	
	public double shieldStrength(){
		return shieldPercentage;
	}
}
