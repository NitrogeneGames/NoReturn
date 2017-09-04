package nitrogene.npc;


import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;
import nitrogene.util.Target;

public class TaskFollow extends MovementTask {
	public PhysicalObject target;
	public int taskID = 1;
	public TaskFollow(NPCship s, PhysicalObject c, float r){
		super(s, r, c);
		target = c;
	}
	
	@Override
	public void activate(int delta, float camX, float camY) {
		if(target.isDestroyed()) {
			isComplete = true;
			this.close();
		} else {
			float destx = target.getX();
			float desty = target.getY();
			super.activate(delta, camX, camY, destx, desty);
		}

	}

	@Override
	public void close() {
		ship.getMovement().forceStop();
	}
	@Override
	public int getTaskID() {
		// TODO Auto-generated method stub
		return taskID;
	}
	
	//create path from lines
	
	
	//find time between paths
	
	
	//calculate acceleration and toggles
	
	
	//execute with timer
	


};
