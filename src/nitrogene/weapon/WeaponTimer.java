package nitrogene.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import nitrogene.core.CursorSystem;

import org.newdawn.slick.SlickException;

public class WeaponTimer {
   public Timer Clock;
   public boolean isPauseLocked = false;
   public boolean active = false;
   public boolean isLocked = false;
   public int tickTime = 10;
   public float interBurst = 0;
   public float outerBurst = 0;
   public int burstShot = 0;
   private boolean bursting = false;
   private int shot = 0;
   private long start;
   private long elapsed;
   private LaserLauncher w;
   private int counter = 0;
	ActionListener taskPerformer = new ActionListener() {
    	public void actionPerformed(ActionEvent evt) {
    		if(!isPauseLocked && active && !isLocked) {
    			tick();
    		}
    	}
    };
    public Timer getClock() {
    	return Clock;
    }
    public void setClock(Timer t) {
    	Clock = t;
    }
    public void tick() {
    	counter++;
    	if(counter >= outerBurst && !bursting) {
    		if(burstShot > 1) {
    			bursting = true;
    			action();
    			counter = 0;
    			shot++;
    		} else {
    			action();
    			counter = 0;
    		}
    	} else if(counter >= interBurst && bursting) {
    		action();
    		shot++;
    		counter = 0;
    		if(shot >= burstShot) {
    			bursting = false;
    			shot = 0;
    		}
    	}
    }
   public void action() {
	   try {
		w.fire();
		start = System.currentTimeMillis();
	} catch (SlickException e) {
		e.printStackTrace();
	}
   }
   public LaserLauncher getWeapon() {
	   return w;
   }
   public void setWeapon(LaserLauncher t) {
	   w = t;
   }
   public WeaponTimer(int c, LaserLauncher d) {
	   w = d; 
	   interBurst = d.getInterburst()/10;
	   outerBurst = d.getOuterburst()/10;
	   burstShot = d.getBurstNumber();
       Clock = new Timer(tickTime, taskPerformer);
       //CursorSystem.changeCursor("greenfire");
   }
   public WeaponTimer(LaserLauncher d) {
	   w = d;
	   interBurst = ((float)d.getInterburst())/10;
	   outerBurst = ((float)d.getOuterburst())/10;
	   burstShot = d.getBurstNumber();
       Clock = new Timer(tickTime, taskPerformer);
       //CursorSystem.changeCursor("greenfire");
   }
   public void start() {   
		this.start((int) (tickTime - elapsed));		
   }
   public void resume() {   
		this.resume((int) (tickTime - elapsed));		
   }
   public void start(int delay) {  
	    active = true;
	 	resume(delay);
   }
   public void stop() {
	   active = false;
	   pause();
   }
   public void resume(int delay) {
		start = System.currentTimeMillis();
		Clock.start();		   
   }
   public void pause() {
	   elapsed = System.currentTimeMillis() - start;
	   Clock.stop();
   }
   public void gameResume() {
	   if(!isLocked ) {
			this.resume();
	   } 
	   //CursorSystem.changeCursor("greenfire");
	   isPauseLocked = false;
   }
   public void gamePause() {
	   this.pause();
	   //CursorSystem.changeCursor("redfire");
	   isPauseLocked = true;
   }
   public float getMaxChargeTime(){
		return outerBurst;
   }
   public float getCurrentChargeTime(){
	   if(bursting) {
		   return outerBurst;
	   }
	   return counter;
   }
}
