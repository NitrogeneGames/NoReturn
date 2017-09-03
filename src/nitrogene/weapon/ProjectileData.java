package nitrogene.weapon;

public class ProjectileData {
	public String image;
	public float speed;
	public boolean tracking;
	public float turningAngle;
	public float scale;
	public int damage;
	public int shipDamage;
	public int planetDamage;
	public int hp;
	//image is projectile image
	//speed is float for speed, total pixels moved
	//homing boolean is to enable/disable homing
	//angle is the speed in degrees/second of homing
	//scale is the size
	//damage is general damage
	//planetdamage overrides damage when hitting planets
	//shipdamage overrides damage when hitting ships (not yet)
	//hp is a soon to be health for projectiles so they can get shot down
	public ProjectileData(String image, float speed, boolean homing, float angle, float scale, int damage, int shipDamage, int planetDamage, int hp) {
		this.image = image;
		this.speed = speed;
		this.tracking = homing;
		this.turningAngle = angle;
		this.scale = scale;
		this.damage = damage;
		this.shipDamage = shipDamage;
		this.planetDamage = planetDamage;
		this.hp = hp;
	}
	public ProjectileData(String image, float speed, boolean homing, float angle, float scale, int damage, int shipDamage, int planetDamage) {
		this(image, speed, homing, angle, scale, damage, shipDamage, planetDamage, 10);
	}
	public ProjectileData(String image, float speed, boolean homing, float angle, float scale, int damage, int hp) {
		this(image, speed, homing, angle, scale, damage, damage, damage, hp);
	}
	public ProjectileData(String image, float speed, boolean homing, float angle, float scale, int damage) {
		this(image, speed, homing, angle, scale, damage, damage, damage, 10);
	}
	public ProjectileData(String image, float speed, float scale, int damage) {
		this(image, speed, false, 0, scale, damage, damage, damage, 10);
	}
	public ProjectileData(String image, float speed, float scale, int damage, int shipDamage, int planetDamage) {
		this(image, speed, false, 0, scale, damage, shipDamage, planetDamage, 10);
	}
}
