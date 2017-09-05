package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.util.EnumStatus;
import nitrogene.world.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends ShipSystem{
	
	private int damageInd;
	public float shieldPercentage;
	public int shieldQuality;
	
	public Shield(Craft c, float x, float y, int maxhp, int durability, int damageradius, float powerNeeded) throws SlickException{
		super(c, x, y, maxhp, durability, damageradius, powerNeeded);
		damageInd = 0;
		shieldPercentage = 100;
	}
	
	@Override
	public void update(int delta, float camX, float camY){
		if(functionality>=8){
			shieldQuality=3;
		} else if(functionality>=4 && functionality < 8){
			shieldQuality=2;
		} else if(functionality<4){
			shieldQuality=1;
		}
		
		if(hp <= 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else if(hp<maxhp){
			this.setStatus(EnumStatus.DAMAGED);
		} else if(this.functionality<10){
			this.setStatus(EnumStatus.POWER);
		} else{
			this.setStatus(EnumStatus.READY);
		}
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
