package nitrogene.util;

import java.util.ArrayList;

import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.WeaponTimer;

public class TickSystem {
	private static boolean isPaused = false;
	public static ArrayList<WeaponTimer> timers = new ArrayList<WeaponTimer>();
	static ArrayList<WeaponTimer> markForDead = new ArrayList<WeaponTimer>();
	public static void update(int delta) {
		
		for(WeaponTimer t : timers) {
			if(t.parent.parent.isDestroyed()) {
				markForDead.add(t);
			} else {
				t.update(delta);
			}
		}
		for(WeaponTimer t : markForDead) {
			removeTimer(t);
		}
		if(markForDead.size() > 0) {
			markForDead = new ArrayList<WeaponTimer>();
		}
	}
	public static void pause() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).pause();
		}
	}
	public static void resume() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).start();
		}
	}
	public static void gamePause() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).gamePause();
		}
		isPaused = true;
	}
	public static void gameResume() {
		for (int i = 0; i < timers.size(); i++) {
			timers.get(i).gameResume();
		}
		isPaused = false;
	}
	public TickSystem() {
			timers = new ArrayList<WeaponTimer>();
	}
	public static void addTimer(WeaponTimer t) {
		timers.add(t);
	}
	public static void removeTimer(WeaponTimer t) {
		for (int i = 0; i < timers.size(); i++) {
			if(timers.get(i).equals(t)) {
				timers.remove(i);
			}
		}
	}
	public static WeaponTimer getTimer(LaserLauncher t) {
		for (int i = 0; i < timers.size(); i++) {
			if(timers.get(i).getWeapon().equals(t)) {
				return  timers.get(i);
			}
		}
		return null;
	}
	public static boolean isPaused() {
		return isPaused;
	}
}
