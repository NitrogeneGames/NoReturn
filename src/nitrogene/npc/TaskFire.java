package nitrogene.npc;

import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.weapon.WeaponTimer;

public class TaskFire extends Task {
	public Craft target;
	public int weaponID;
	public TaskFire(NPCship s, Craft t, int wid){
		super(s);
		target = t;
		weaponID = wid;
	}

	@Override
	public void activate(int delta) {
		System.out.println("FUS RO DAH");

		ship.laserlist.get(weaponID).setTarget(target.getX(), target.getY());
		
		//WeaponTimer t = ship.laserlist.get(weaponID).getTimer();
		
		//		ship.laserlist.get(weaponID)

	}

	@Override
	public void close(int delta) {
		//ship.laserlist.get(weaponID).getTimer().isLocked = true;
	}
};
