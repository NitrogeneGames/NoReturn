package nitrogene.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.SlickException;

public class TickSystem {
	public void init() {

	}
	public void pause() {
		//PAUSE ALL TIMERS
	}
	public void resume() {
		//RESUME ALL TIMERS
	}
	/*
	 * 				timer1.stop();
				elapsed = System.currentTimeMillis() - start;
	 * timer1.setInitialDelay((int) (1000 - elapsed));
				timer1.start();
	if(timer1.isRunning() == false) {
		firetoggle = true;
		timer1.setInitialDelay((int) (1000 - elapsed));
		timer1.start();
		start = System.currentTimeMillis();
	}
				if(timer1.isRunning() == true) {
				elapsed = System.currentTimeMillis() - start;
				timer1.stop();
			}
	
				if(timer1.isRunning() == false && firetoggle == true) {
				timer1.setInitialDelay((int) (1000 - elapsed));
				timer1.start();
				start = System.currentTimeMillis();
			}
	*/
	public TickSystem() {
		ActionListener taskPerformer = new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {

	    		tick((Timer) evt.getSource());
	    	}
	    };
	       //timer1 = new Timer(1000, taskPerformer);
	}
	public void addTimer(Timer t) {
		
	}
	public void removeTimer(Timer t) {
		
	}
	public void tick(Timer t){
		//start = System.currentTimeMillis();


			//craft.laserlist.get(0).fire();

	}
}
