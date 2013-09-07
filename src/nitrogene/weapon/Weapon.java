package nitrogene.weapon;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Weapon{
	BASIC (0, 3, 1000, 100F, 10, 0.7F),
    OP (0, 0, 100, 100F, 1000, 3F);

	
	public int accuracy; //Accuracy
	public int time; //Fire Speed
	public float speed; //Laser Speed
	public int damage; //DUH
	public float size;
	public Image image;

	Weapon(int im, int accuracy1, int time1, float speed1, int damage1, float size1) {
		if(im == 0) {			
			try {
				this.image = new Image("res/Laser1.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}

		this.accuracy = accuracy1;
		this.time = time1;
		this.speed = speed1;
		this.damage = damage1;
		this.size = size1;
		
	}
    
}
