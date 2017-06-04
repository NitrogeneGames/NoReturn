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
   private int shot, maxshot;
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
    	if(counter >= 100) {
    		action();
    		counter = 0;
    	}
    }
   public void action() {
	   shot++;
	   /*if(shot <= w.getBurstNumber()){
		   Clock.setDelay(w.getInterburst());
	   } else{
		   Clock.setDelay(w.getOuterburst());
		   shot = 1;
	   }*/
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
		return 100;
   }
   public float getCurrentChargeTime(){
	   return counter;
   }
}
