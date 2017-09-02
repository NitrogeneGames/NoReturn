package nitrogene.weapon;


public enum EnumWeapon{
	//IMPORTANT: Providing a different scale requires a new set of boundbox rotation constants!
	//Name, Launcher image, Accuracy, Speed, Damage, Size, Laser image, Fire sound, Time between shots, Time between bursts, Number of shots per bursts
	BASIC ("Basic Laser", "laser1", "laser1Green",3, 100F, 10, 10, 0.7F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2000, 1),
	SPLIT ("Split Laser", "laser2", "laser2Green", 5, 100F, 20, 20, 0.6F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 10, 2500, 2),
	SPLIT2 ("Split Laser Mk.2", "laser2", "laser2Green", 7, 100F, 20, 20, 0.6F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 10, 3000, 3),
	PULSAR ("Pulsar", "laser3", "laser3Green", 1, 70f, 10, 10, 0.7f, 100, "res/pulsar_bolt_fixed.png", "res/sound/laser1final.ogg", 200, 3000, 2),
	PULSAR2 ("Pulsar Mk.2", "laser3", "laser3Green", 2, 70f, 20, 20, 0.7f, 100, "res/pulsar_bolt_fixed.png", "res/sound/laser1final.ogg", 200, 3500, 3),
	VELOX ("Velox Laser", "laser3", "laser3Green", 7, 100f, 15, 15, 0.6f, 100, "res/pulsar_bolt_fixed.png", "res/sound/laser1final.ogg", 200, 5000, 5),
	VELOX2 ("Velox Laser Mk.2", "laser3", "laser3Green", 7, 100f, 20, 20, 0.8f, 100, "res/pulsar_bolt_fixed.png", "res/sound/laser1final.ogg", 200, 5000, 5),
	IMMINEO ("Immineo Laser", "laser1", "laser1Green", 3, 100F, 10, 10, 0.7F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2000, 1),
	IMMINEO2 ("Immineo Laser Mk.2", "laser1", "laser1Green", 3, 100F, 20, 20, 0.7F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2500, 1),
	DEMOLITION ("Demolition Laser", "wep1", "wep1Green", 15, 20F, 100, 100, 1.5F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 7000, 1),
	PRECISION ("Precision Laser", "wep2", "wep2Green", 3, 130F, 3, 3, 0.3F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 500, 1),
	PDI ("Point Defense Interceptor", "wep2", "wep2Green", 2, 200F, 2, 1, 0.3F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 150, 1),
    //TESTING ("Testing Laser", "res/Laser1.png", 0, 100F, 1000, 1000, 3F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 100, 1),
	MINING ("Mining Laser", "wep1", "wep1Green", 0, 100F, 10, 100, 0.7F, 100, "res/LaserV2ro.png", "res/sound/laser1final.ogg", 0, 2000, 1);
	
	public int accuracy; //Accuracy
	public float speed; //Laser Speed
	public int damage, planetdamage; //DUH
	public float size;
	public String image;
	public String image2;
	public String laserimage;
	public String firesound;
	public int interburst, outerburst, burstnumber;
	public String formalname;
	public int hp;
	public boolean animated1;
	public boolean animated2;
	//im is the launcehrs image
	//laserimage is the projectiles image
	//interburst is time inbetween shots in a burst --> for non-burst, make this 0
	//outerburst is the time between bursts --> for non-burst, use this as the time varaible between single shots
	//burstnumber is the amt of shots in a burst --> for a non-burst, make this 1 (single shot per burst)
	EnumWeapon(String formalname, String im, boolean animated1, String im2, boolean animated2, int accuracy1, float speed1, int damage1, int planetdamage, float size1, int hp, String laserimage, String firesound, int interburst, int outerburst, int burstnumber) {
		this.formalname = formalname;
		this.image = im;
		this.image2 = im2;
		this.animated1 = animated1;
		this.animated2 = animated2;
		this.hp = hp;
		this.laserimage = laserimage;
		this.accuracy = accuracy1;
		this.speed = speed1;
		this.damage = damage1;
		this.planetdamage = planetdamage;
		this.size = size1;
		this.firesound = firesound;
		this.interburst = interburst;
		this.outerburst = outerburst;
		this.burstnumber = burstnumber;
	}
	EnumWeapon(String formalname, String im, String im2, int accuracy1, float speed1, int damage1, int planetdamage, float size1, int hp, String laserimage, String firesound, int interburst, int outerburst, int burstnumber) {
		this(formalname, im, false, im2, false, accuracy1, speed1, damage1, planetdamage, size1, hp, laserimage, firesound, interburst, outerburst,burstnumber);
	}
	EnumWeapon(String formalname, String im, int accuracy1, float speed1, int damage1, int planetdamage, float size1, int hp, String laserimage, String firesound, int interburst, int outerburst, int burstnumber) {
		this(formalname, im, false, im, false, accuracy1, speed1, damage1, planetdamage, size1, hp, laserimage, firesound, interburst, outerburst,burstnumber);
	}
	public static EnumWeapon getWeaponFromFormalName(String fname) {
		for(int i = 0; i<(EnumWeapon.values().length); i++) {
			EnumWeapon e = EnumWeapon.values()[i];
			if(e.formalname.equals(fname)) {
				return e;
			}
		}
		return null;
	}
    
}
