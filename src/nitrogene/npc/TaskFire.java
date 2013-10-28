package nitrogene.npc;

import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;

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
		System.out.println("FUS DO RAH");
		ship.laserlist.get(weaponID).getTimer().isLocked = false;
		ship.laserlist.get(weaponID).setTarget(target.getX(), target.getY());
		try {
			ship.laserlist.get(weaponID).fire();
			//ship.laserlist.get(weaponID).
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close(int delta) {
		//ship.laserlist.get(weaponID).getTimer().isLocked = true;
	}
};
