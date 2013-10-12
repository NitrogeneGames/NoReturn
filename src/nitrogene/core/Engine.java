package nitrogene.core;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Engine extends ShipSystem{
	private int thrust, warpcharge, warp;
	private int maxhp;
	private boolean locked;
	private Image im;

	public Engine(float x, float y, int maxhp, int durability, int maxpower, int capacity, int thrust, int warpcharge, int damageradius) throws SlickException {
		super(x, y, maxhp, durability, maxpower, capacity, damageradius);
		im = new Image("res/icon/enginesystem.png");
		this.setImage(im);
		this.thrust = thrust;
		this.warpcharge = warpcharge;
		this.maxhp = getHp();
		warp = warpcharge;
		locked = false;
	}
	
	public boolean countWarp(){
		warp -= 1;
		if(warp > 0) return false;
		if(warp == 0){
			warp = warpcharge;
			return true;
		}
		return false;
	}
	
	public void lock(){
		locked = true;
	}
	
	public void unlock(){
		locked = false;
	}
	
	public void damage(int damage){
		setHp(getHp() - damage);
		if(getHp() <= 0){
			locked = true;
			setHp(0);
		}
	}
	
	public void repair(int repair, boolean unlock){
		setHp(getHp() + repair);
		if(getHp() > maxhp){
			setHp(maxhp);
		}
		if(getHp() > 0 && !locked && unlock){
			locked = true;
		}
	}
	
	public int getThrust(){
		if(!locked){
		return thrust;
		}
		return 0;
	}
	
	public void modifyThrust(int dthrust){
		thrust += dthrust;
	}
	
	public void changeThrust(int newthrust){
		thrust = newthrust;
	}
	
	public int getWarpcharge(){
		return warpcharge;
	}
}
