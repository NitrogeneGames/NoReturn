package nitrogene.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import nitrogene.core.CursorSystem;
import nitrogene.util.EnumStatus;

import org.newdawn.slick.SlickException;

public class WeaponTimer {
   public boolean isPauseLocked = false;
   public boolean active = false;
   public boolean isLocked = false;
   public int tickTime = 1;
   public float interBurst = 0;
   public float outerBurst = 0;
   public int burstShot = 0;
   public boolean bursting = false;
   private int shot = 0;
   private long start;
   private long elapsed;
   public LaserLauncher parent;
   private int counter = 0;
	//ActionListener taskPerformer = new ActionListener() {//event to call tick() every 10 miliseconds
	public void update(int delta) {
		if(!isPauseLocked && !isLocked) {
			tick(delta);
		}
	}
    //};
    public void tick(int delta) {//called every 10 miliseconds, will count up to the set time then call action() to fire a laser
		counter = counter + delta;
    	if(counter > outerBurst) {
    		counter = (int) outerBurst;
    	}
    	if(parent.getStatus() == EnumStatus.ENGAGED) {
	    	if(counter >= outerBurst && !bursting) {
	    		if(burstShot > 1) {
	    			if(action()) {
		    			bursting = true;
		    			counter = 0;
		    			shot++;
	    			}
	    		} else {
	    			if(action()) {
	    				counter = 0;
	    			}
	    		}
	    	} else if(counter >= interBurst && bursting) {
	    		if(action()) {
		    		shot++;
		    		counter = 0;
		    		if(shot >= burstShot) {
		    			bursting = false;
		    			shot = 0;
		    		}
	    		}
	    	}
    	} else {
			bursting = false;
			shot = 0;
    	}
    }
   public boolean action() {//called when the timer wants to fire a laser
	   try {
		   	boolean success = parent.fire();
			if(success) {
				start = System.currentTimeMillis();
			}
			return success;
	   } catch (SlickException e) {
		   	e.printStackTrace();
	   }
	   return false;
   }
   public LaserLauncher getWeapon() {
	   return parent;
   }
   public void setWeapon(LaserLauncher t) {//assigns a weapon to the timer, will trigger the weapon to fire as needed
	   parent = t;
   }
   //interburst = time between burst shots, outerburst = time to charge, burstshot = shots in burst
   public WeaponTimer(int c, LaserLauncher d) {
	   parent = d; 
	   interBurst = d.getInterburst();
	   outerBurst = d.getOuterburst();
	   burstShot = d.getBurstNumber();
       //Clock = new Timer(tickTime, taskPerformer);
       //CursorSystem.changeCursor("greenfire");
   }
   public WeaponTimer(LaserLauncher d) {
	   parent = d;
	   interBurst = ((float)d.getInterburst());
	   outerBurst = ((float)d.getOuterburst());
	   burstShot = d.getBurstNumber();
       //Clock = new Timer(tickTime, taskPerformer);
       //Clock.start();
       //CursorSystem.changeCursor("greenfire");
   }
   public void start() {   
		this.start((int) (tickTime - elapsed));		
   }
   public void resume() {   
		this.resume((int) (tickTime - elapsed));		
   }
   public void start(int delay) { //activate the laser if not already active 
	    active = true;
	 	resume(delay);
   }
   public void stop() { //called by the game istelf to deactivate the laser, ie laser breaks
	   //counter = 0;
	   if(bursting) {
		   counter = 0;
		   bursting = false;
	   }
	   active = false;
	   pause();
   }
   public void resume(int delay) { //resume function called by gameResume and start
		start = System.currentTimeMillis();
		//Clock.start();		   
   }
   public void pause() { //pause function called by gamePause and stop
	   elapsed = System.currentTimeMillis() - start;
	   //Clock.stop();
   }
   public void gameResume() { //resume the timer when game is unpaused
	   if(!isLocked ) {
			this.resume();
	   } 
	   //CursorSystem.changeCursor("greenfire");
	   isPauseLocked = false;
   }
   public void gamePause() { //called when the ESC button is pressed, needs to freeze the timer
	   this.pause();
	   //CursorSystem.changeCursor("redfire");
	   isPauseLocked = true;
   }
   public float getMaxChargeTime(){
	   	if(bursting && interBurst*((float)burstShot)*10 >= 500) { //only counts for bursts longer than 1 second in total
	   		return burstShot; //return max shots, makes hotbar show burst progress
	   	}
		return outerBurst; //return max charge
   }
   public float getCurrentChargeTime(){
	   if(bursting && interBurst*((float)burstShot)*10 >= 500) {
		   return shot; //return current shot for hotbar burst progress
	   } else if(bursting) {
		   return outerBurst;
	   }
	   return counter; //return current charge
   }
}
