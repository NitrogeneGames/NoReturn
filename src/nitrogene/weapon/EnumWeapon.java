package nitrogene.weapon;

import org.newdawn.slick.Sound;

public enum EnumWeapon{
	BASIC ("res/Laser1.png", 3, 1000, 100F, 10, 0.7F, "res/LaserV2ro.png", "res/sound/laser1final.ogg"),
    OP ("res/Laser1.png", 0, 100, 100F, 1000, 3F, "res/LaserV2ro.png", "res/sound/laser1final.ogg");

	
	public int accuracy; //Accuracy
	public int time; //Fire Speed
	public float speed; //Laser Speed
	public int damage; //DUH
	public float size;
	public String image;
	public String laserimage;
	public String firesound;
	//im is the launcehrs image
	//laserimage is the projectiles image
	EnumWeapon(String im, int accuracy1, int time1, float speed1, int damage1, float size1, String laserimage, String firesound) {
		this.image = im;
		this.laserimage = laserimage;
		this.accuracy = accuracy1;
		this.time = time1;
		this.speed = speed1;
		this.damage = damage1;
		this.size = size1;
		this.firesound = firesound;
	}
    
}
