package nitrogene.weapon;

import org.newdawn.slick.Image;



public class LaserStat {
	public int accuracy; //Accruacy
	public int time; //Fire Speed
	public float speed; //Laser Speed
	public int damage; //DUH
	public float size;
	public Image image;
	public LaserStat(Image im, int accuracy1, int time1, float speed1, int damage1, float size1) {
		this.accuracy = accuracy1;
		this.time = time1;
		this.speed = speed1;
		this.damage = damage1;
		this.size = size1;
		this.image = im;
	}
}
