package nitrogene.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import nitrogene.core.CursorSystem;

import org.newdawn.slick.SlickException;

public class WeaponTimer {
   public Timer Clock;
   public boolean isPauseLocked = false;
   public boolean isLocked = false;
   public int tickTime = 1000;
   private int shot, maxshot;
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
	   shot++;
	   if(shot <= w.getBurstNumber()){
		   Clock.setDelay(w.getInterburst());
	   } else{
		   Clock.setDelay(w.getOuterburst());
		   shot = 1;
	   }
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
	   shot = 1;
       Clock = new Timer(c, taskPerformer);
       //CursorSystem.changeCursor("greenfire");
   }
   public WeaponTimer(LaserLauncher d) {
	   w = d;
	   shot = 1;
       Clock = new Timer(d.getInterburst(), taskPerformer);
       //CursorSystem.changeCursor("greenfire");
   }
   public void start() {   
		this.start((int) (tickTime - elapsed));		
   }
   public void start(int delay) {   
	    isLocked = false;
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
	   shot = 1;
	   CursorSystem.changeCursor("greenfire");
	   isPauseLocked = false;
   }
   public void gamePause() {
	   this.pause();
	   CursorSystem.changeCursor("redfire");
	   isPauseLocked = true;
   }
   public float getMaxChargeTime(){
		return w.getOuterburst();
   }
   public float getCurrentChargeTime(){
	   return System.currentTimeMillis() - start;
   }
}
