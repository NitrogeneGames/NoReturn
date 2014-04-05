package nitrogene.weapon;

import org.newdawn.slick.Sound;

public enum EnumWeapon{
	//Name, Launcher image, Accuracy, Speed, Damage, Size, Laser image, Fire sound, Time between shots, Time between bursts, Number of shots per bursts
	BASIC ("res/Laser1.png", 3, 100F, 10, 0.7F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2000, 1),
	SPLIT ("res/Laser1.png", 5, 100F, 20, 0.6F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 10, 2500, 2),
	SPLIT2 ("res/Laser1.png", 7, 100F, 20, 0.5F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 10, 3000, 3),
	PULSAR ("res/klaar_pulsar_2.png", 1, 70f, 20, 0.7f, "res/pulsar_bolt.png", "res/sound/laser1final.ogg", 200, 3000, 2),
	PULSAR2 ("res/klaar_pulsar_2.png", 2, 70f, 20, 0.7f, "res/pulsar_bolt.png", "res/sound/laser1final.ogg", 200, 3500, 3),
	VELOX ("res/klaar_pulsar_2.png", 7, 100f, 15, 0.6f, "res/pulsar_bolt.png", "res/sound/laser1final.ogg", 200, 5000, 5),
	VELOX2 ("res/klaar_pulsar_2.png", 7, 100f, 20, 0.8f, "res/pulsar_bolt.png", "res/sound/laser1final.ogg", 200, 5000, 5),
	IMMINEO ("res/Laser1.png", 3, 100F, 10, 0.7F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2000, 1),
	IMMINEO2 ("res/Laser1.png", 3, 100F, 20, 0.7F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2500, 1),
	DEMOLITION ("res/Laser1.png", 15, 20F, 100, 1.5F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 7000, 1),
	PRECISION ("res/Laser1.png", 3, 130F, 3, 0.4F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 500, 1),
	PDI ("res/Laser1.png", 2, 200F, 2, 0.3F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 150, 1),
    TESTING ("res/Laser1.png", 0, 100F, 1000, 3F, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 100, 1);
	
	public int accuracy; //Accuracy
	public float speed; //Laser Speed
	public int damage; //DUH
	public float size;
	public String image;
	public String laserimage;
	public String firesound;
	public int interburst, outerburst, burstnumber;
	//im is the launcehrs image
	//laserimage is the projectiles image
	//interburst is time inbetween shots in a burst --> for non-burst, make this 0
	//outerburst is the time between bursts --> for non-burst, use this as the time varaible between single shots
	//burstnumber is the amt of shots in a burst --> for a non-burst, make this 1 (single shot per burst)
	EnumWeapon(String im, int accuracy1, float speed1, int damage1, float size1, String laserimage, String firesound, int interburst, int outerburst, int burstnumber) {
		this.image = im;
		this.laserimage = laserimage;
		this.accuracy = accuracy1;
		this.speed = speed1;
		this.damage = damage1;
		this.size = size1;
		this.firesound = firesound;
		this.interburst = interburst;
		this.outerburst = outerburst;
		this.burstnumber = burstnumber;
	}
    
}
