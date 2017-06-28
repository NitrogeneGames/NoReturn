package nitrogene.npc;

import nitrogene.core.Craft;
import nitrogene.objecttree.PhysicalObject;

public class TaskFire extends Task {
	public PhysicalObject target;
	public int weaponID;
	public int taskID = 0;
	public TaskFire(NPCship s, PhysicalObject t, int wid){
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
		if(target.isDestroyed()) {
			this.isComplete = true;
		}
	}

	@Override
	public void close() {
		ship.laserlist.get(weaponID).getTimer().stop();
	}

	@Override
	public int getTaskID() {
		// TODO Auto-generated method stub
		return taskID;
	}
};
