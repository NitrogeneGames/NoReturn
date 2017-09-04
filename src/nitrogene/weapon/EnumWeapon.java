package nitrogene.weapon;

import org.newdawn.slick.UnicodeFont;

import nitrogene.core.GlobalInformation;

public enum EnumWeapon{
	//IMPORTANT: Providing a different scale requires a new set of boundbox rotation constants!
	//Test
	//Name, Launcher image, Accuracy, Speed, Damage, Laser Size, Laser image, Fire sound, Time between shots, Time between bursts, Number of shots per bursts
	BASIC (20, new ProjectileData("proj1", 100F, 0.7F, 10), "Basic Laser", "laser1", "laser1Green",3, 100, "res/sound/laser1final.ogg", 0, 2000, 1),
	SPLIT (50, new ProjectileData("proj1", 100F, 0.6F, 20), "Split Laser", "laser2", "laser2Green", 5, 100, "res/sound/laser1final.ogg", 10, 2500, 2),
	SPLIT2 (80, new ProjectileData("proj1", 100F, 0.6F, 10), "Split Laser Mk.2", "laser2", "laser2Green", 7, 100, "res/sound/laser1final.ogg", 10, 3000, 3),
	PULSAR (80, new ProjectileData("proj2", 70F, 0.7F, 10), "Pulsar", "laser3", "laser3Green", 1, 100, "res/sound/laser1final.ogg", 200, 3000, 2),
	PULSAR2 (150, new ProjectileData("proj2", 70F, 0.7F, 20), "Pulsar Mk.2", "laser3", "laser3Green", 2, 100, "res/sound/laser1final.ogg", 200, 3500, 3),
	VELOX (250, new ProjectileData("proj2", 100F, 0.6F, 15), "Velox Laser", "laser3", "laser3Green", 7, 100, "res/sound/laser1final.ogg", 200, 5000, 5),
	VELOX2 (350, new ProjectileData("proj2", 100F, 0.6f, 20), "Velox Laser Mk.2", "laser3", "laser3Green", 7, 100, "res/sound/laser1final.ogg", 200, 5000, 5),
	IMMINEO (200, new ProjectileData("proj1", 50F, true, 130, 0.7F, 10), "Immineo Laser", "laser1", "laser1Green", 3, 100, "res/sound/laser1final.ogg", 0, 2000, 1),
	IMMINEO2 (300, new ProjectileData("proj1", 50F, true, 130, 0.7F, 20), "Immineo Laser Mk.2", "laser1", "laser1Green", 3, 100, "res/sound/laser1final.ogg", 0, 2500, 1),
	DEMOLITION (300, new ProjectileData("proj1", 20F, 1.5F, 100), "Demolition Laser", "wep1", "wep1Green", 15, 100, "res/sound/laser1final.ogg", 0, 7000, 1),
	PRECISION (100, new ProjectileData("proj1", 130F, 0.3F, 3), "Precision Laser", "wep2", "wep2Green", 3, 100, "res/sound/laser1final.ogg", 0, 500, 1),
	PDI (40, new ProjectileData("proj1", 200F, 0.3F, 1), "Point Defense Interceptor", "ion1", "ion1Green", 1, 100, "res/sound/laser1final.ogg", 0, 150, 1),  
	MINING (100, new ProjectileData("proj1", 100F, 0.7F, 10, 10, 100), "Mining Laser", "wep1", "wep1Green", 0, 100, "res/sound/laser1final.ogg", 0, 2000, 1);
	
	public ProjectileData projectile;
	public int accuracy; //Accuracy
	public String image;
	public String image2;
	public String firesound;
	public int interburst, outerburst, burstnumber;
	public String formalname;
	public int hp;
	public boolean animated1;
	public boolean animated2;
	public UnicodeFont font;
	public String shortenedString;
	public int xLaunchOffset;
	public int yLaunchOffset;
	public int cost;
	//im is the launcehrs image
	//laserimage is the projectiles image
	//interburst is time inbetween shots in a burst --> for non-burst, make this 0
	//outerburst is the time between bursts --> for non-burst, use this as the time varaible between single shots
	//burstnumber is the amt of shots in a burst --> for a non-burst, make this 1 (single shot per burst)
	EnumWeapon(int cost, ProjectileData l, String formalname, String im, String im2, int accuracy1, int hp, String firesound, int interburst, int outerburst, int burstnumber) {
		this.cost = cost;
		this.projectile = l;
		this.formalname = formalname;
		this.image = im;
		this.image2 = im2;
		this.hp = hp;
		this.accuracy = accuracy1;
		this.firesound = firesound;
		this.interburst = interburst;
		this.outerburst = outerburst;
		this.burstnumber = burstnumber;
	}
	public void loadFont() {
		String n = formalname;
		if(GlobalInformation.uniFont.getWidth(n) < 104) {
			font = GlobalInformation.uniFont;
		} else if(GlobalInformation.uniFont2.getWidth(n) < 104) {
			font = GlobalInformation.uniFont2;
		} else if(GlobalInformation.uniFont3.getWidth(n) < 104) {
			font = GlobalInformation.uniFont3;
		} else {
			font = GlobalInformation.uniFont3;
			while(GlobalInformation.uniFont3.getWidth(n) > 104) {
				n = n.substring(0, n.length() - 4);			
				n = n + "...";
			}
			if(n.charAt(n.length()-4) == ' ') {
				n = n.substring(0, n.length()-4) + "...";
			}
		}
		this.shortenedString = n;
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
