package nitrogene.core;

import org.newdawn.slick.Image;

public class Planet extends CircleMesh{
	private int maxhp;
	private int hp;

	public Planet(float centerx, float centery, Image theimage, int maxhp) {
		super(centerx, centery, theimage);
		this.maxhp = maxhp;
		hp = maxhp;
	}
	
	public void damage(int damage){
		hp -= damage;
		if(hp <= 0){
			hp = 0;
			//explode();
		}
	}
	
	public int getHp(){
		return hp;
	}
	
	public int getMaxHp(){
		return maxhp;
	}

}
