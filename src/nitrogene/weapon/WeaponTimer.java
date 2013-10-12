package nitrogene.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.SlickException;

public class WeaponTimer {
   public Timer Clock;
   public boolean isPauseLocked = false;
   public boolean isLocked = false;
   public int tickTime = 1000;
   private long start;
   private long elapsed;
   private LaserLauncher w;
	ActionListener taskPerformer = new ActionListener() {
    	public void actionPerformed(ActionEvent evt) {
    		tick();
    	}
    };
    public Timer getClock() {
    	return Clock;
    }
    public void setClock(Timer t) {
    	Clock = t;
    }
   public void tick() {
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
       Clock = new Timer(c, taskPerformer);
   }
   public WeaponTimer(LaserLauncher d) {
	   w = d;
       Clock = new Timer(1000, taskPerformer);
   }
   public void start() {   
		this.start((int) (tickTime - elapsed));		
   }
   public void start(int delay) {   
	    isLocked = false;
		Clock.setInitialDelay(delay);
		start = System.currentTimeMillis();
		Clock.start();		
  }
   public void pause() {
	   elapsed = System.currentTimeMillis() - start;
	   Clock.stop();
   }
   public void gameResume() {
	   if(!isLocked ) {
			this.start();
	   } 
	   isPauseLocked = false;
   }
   public void gamePause() {
	   this.pause();
	   isPauseLocked = true;
   }
   
}
