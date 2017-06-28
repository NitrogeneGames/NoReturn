package nitrogene.npc;

import nitrogene.core.Craft;

public class TaskFire extends Task {
	public Craft target;
	public int weaponID;
	public int taskID = 0;
	public TaskFire(NPCship s, Craft t, int wid){
		super(s);
		target = t;
		weaponID = wid;
		ship.laserlist.get(weaponID).getTimer().start();
	}

	@Override
	public void activate(int delta, float camX, float camY) {
		ship.laserlist.get(weaponID).update(craftX, craftY, camX, camY,delta);
		ship.laserlist.get(weaponID).setTarget((float) (target.getRealCenterX()), (float) (target.getRealCenterY()));
		//WeaponTimer t = ship.laserlist.get(weaponID).getTimer();
		
		//		ship.laserlist.get(weaponID)
	}

	@Override
	public void close(int delta) {
		//ship.laserlist.get(weaponID).getTimer().isLocked = true;
	}

	@Override
	public int getTaskID() {
		// TODO Auto-generated method stub
		return taskID;
	}
};
