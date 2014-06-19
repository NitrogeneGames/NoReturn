package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.util.EnumStatus;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LifeSupport extends ShipSystem{
	private float percentoxygen;
	private float changerate;
	private boolean gravitational;
	private int maxhp;

	public LifeSupport(Craft c, float x, float y, int maxhp, int durability, int damageradius, float powerNeeded) throws SlickException {
		super(c, x, y, maxhp, durability, damageradius, powerNeeded);
		this.maxhp = maxhp;
		this.percentoxygen = 100;
		this.changerate = 0;
		this.gravitational = true;
	}
	
	//REMEMBER: TICK TO UPDATE OXYGEN
	//above 30% health: gain oxygen, under 10%: lose oxygen, in middle: neutral
	@Override
	public void update(int delta, float camX, float camY){
		if(functionality >= 10){
			changerate = 10*(delta/1000f);
		} else if(hp<(maxhp*0.3)){
			if(functionality>=5){
				changerate = -3*(delta/1000f);
			} else if(functionality < 5){
				changerate = -10*(delta/1000f);
			}
		} else if(functionality >= 5 && functionality < 10){
			changerate = 0f;
		} else if(functionality < 5){
			changerate = -3*(delta/1000f)*(0.5f*(5-functionality));
		} else{
			System.out.println("CRITICAL FUNCTIONALITY ERROR:  " + functionality);
		}
		
		percentoxygen += changerate;
		System.out.println("LIFE SUPPORT: rate of change: " + changerate);
		
		if(hp <= 0){
			this.setStatus(EnumStatus.DESTROYED);
		} else if(this.functionality<10){
			this.setStatus(EnumStatus.POWER);
		} else{
			this.setStatus(EnumStatus.READY);
		}
	}
	
	public float getOxygen(){
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
