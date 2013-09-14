package nitrogene.core;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends ShipSystem{
	
	private Image shieldimage;
	private int damageInd;
	public float shieldPercentage;
	public int shieldQuality;
	private Image im;
	
	public Shield(float x, float y, int maxhp, int durability, int maxpower, int capacity, int shieldQuality, int damageradius) throws SlickException{
		super(x, y, maxhp, durability, maxpower, capacity, damageradius);
		damageInd = 0;
		shieldPercentage = 100;
		shieldimage = new Image("res/shieldV2.png");
		im = new Image("res/icon/shieldsystem.png");
		this.setImage(im);
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
		if(shieldPercentage < damage || shieldPercentage == damage){
			damageInd = 1;
			shieldPercentage = 0;
		}
		if(shieldPercentage > damage){
			shieldPercentage -= damage;
		}
		changeOpac();
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
	private void changeOpac(){
		shieldimage.setImageColor(1f, 1f, 1f, shieldPercentage * 0.1f);
	}
	
	public double shieldStrength(){
		return shieldPercentage;
	}
	public Image getShieldImage(){
		return shieldimage;
	}
}
