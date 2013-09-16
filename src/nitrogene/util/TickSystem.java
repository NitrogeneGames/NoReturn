package nitrogene.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import nitrogene.weapon.WeaponTimer;

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

	       //timer1 = new Timer(1000, taskPerformer);
	}
	public void addTimer(WeaponTimer t) {
		
	}
	public void removeTimer(WeaponTimer t) {
		
	}
}
