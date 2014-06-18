package nitrogene.system;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Core extends ShipSystem{
	private float maxpower;
	private int maxhp;
	private float powerRate;
	private boolean enabled;

	public Core(Craft c, float x, float y, int maxhp, int durability, int damageradius, float powerRate) throws SlickException {
		super(c,x,y, maxhp, durability, damageradius, 0f);
		this.powerRate = powerRate;
		this.maxpower = powerRate;
	}
	
	//Core under 30% health stars to 
	public float getPower(){
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
	public float getPowerRate(){
		return this.powerRate;
	}
	
	public void sendPower(Capacitor cap){
		cap.receivePower(this.powerRate);
	}
	
	public void deviatePowerRate(float pwrrate){
		if(pwrrate <= maxpower){
			powerRate = pwrrate;
		}
	}
}
