package nitrogene.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import nitrogene.weapon.WeaponTimer;

import org.newdawn.slick.SlickException;

public class TickSystem {
	private boolean isPaused = false;
	public ArrayList<WeaponTimer> timers;

	public void pause() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).pause();
		}
	}
	public void resume() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).start();
		}
	}
	public void gamePause() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).gamePause();
		}
		isPaused = true;
	}
	public void gameResume() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).gameResume();
		}
		isPaused = false;
	}
	public TickSystem() {
			timers = new ArrayList<WeaponTimer>();
	}
	public void addTimer(WeaponTimer t) {
		timers.add(t);
	}
	public void removeTimer(WeaponTimer t) {
		for (int i = 0; i < timers.size(); i++) {
			if(timers.get(i).equals(t)) {
				timers.remove(i);
			}
		}
	}
	public boolean isPaused() {
		return isPaused;
	}
}
