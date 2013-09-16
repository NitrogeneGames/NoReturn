package nitrogene.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.SlickException;

public class WeaponTimer {
   public Timer Clock;
   public boolean isPauseLocked = false;
   public boolean isLocked = false;
   public int tickTime;
   private LaserLauncher w;
	ActionListener taskPerformer = new ActionListener() {
    	public void actionPerformed(ActionEvent evt) {
    		tick();
    	}
    };
   public void tick() {
	   try {
		w.fire();
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
	   Clock.start();
   }
   public void pause() {
	   Clock.stop();
   }
   public void gameResume() {
	   if(isLocked != true) {
	   Clock.start();
	   } 
	   isPauseLocked = false;
   }
   public void gamePause() {
	   Clock.stop();
	   isPauseLocked = true;
   }
   
}
