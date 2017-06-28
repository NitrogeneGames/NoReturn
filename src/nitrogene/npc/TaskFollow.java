package nitrogene.npc;


import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;
import nitrogene.util.Target;

public class TaskFollow extends MovementTask {
	public PhysicalObject target;
	public int taskID = 1;
	public TaskFollow(NPCship s, PhysicalObject c){
		super(s);
		target = c;
	}
	
	@Override
	public void activate(int delta, float camX, float camY) {
		float destx = target.getX();
		float desty = target.getY();
		super.activate(delta, camX, camY, desty, destx);
	}

	@Override
	public void close(int delta) {
		
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
