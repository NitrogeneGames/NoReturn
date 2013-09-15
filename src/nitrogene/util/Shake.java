package nitrogene.util;

import java.util.Random;


public class Shake {
	private Random random;
	private float rumbleX;
	private float rumbleY;
	private float rumbleTime;
	private float currentRumbleTime;
	private float rumblePower;
	private float currentRumblePower;
	
	
	public Shake(){
		random = new Random();
		rumbleX = 0;
		rumbleY = 0;
		rumbleTime = 0;
		rumblePower = 0;
		currentRumbleTime = 0;
		currentRumblePower = 0;
	}
	
	public void update(int delta){
		if(currentRumbleTime <= rumbleTime) {
	         currentRumblePower = rumblePower * ((rumbleTime - currentRumbleTime) / rumbleTime);
	         
	         rumbleX = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
	         rumbleY = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
	         
	         currentRumbleTime += delta;
		}
		else{
			rumbleX = 0;
			rumbleY = 0;
		}
	}
	
	public float getDx(){
		return rumbleX*4;
	}
	
	public float getDy(){
		return rumbleY;
	}
	
	public void shakeObject(float power, int time) {
	      rumblePower = power;
	      rumbleTime = time;
	      currentRumbleTime = 0;
	      rumbleX = 0;
	      rumbleY = 0;
	   }
	
}
