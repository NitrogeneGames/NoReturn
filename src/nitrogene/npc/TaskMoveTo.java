package nitrogene.npc;

import nitrogene.core.Zoom;
import nitrogene.util.Direction;
import nitrogene.util.Target;

import org.newdawn.slick.geom.Line;

public class TaskMoveTo extends MovementTask {
	private float desx, desy, cx, cy;
	private Line line1;
	private Line line2;
	public int taskID = 1;
	
	public TaskMoveTo(NPCship s, float desx, float desy, float range){
		super(s, range);
		this.desx = desx;
		this.desy = desy;
		findlines();
	}
	
	//plot course to destination coordinates
	private void findlines(){
		//Line line1 = new Line(cx, desx);
		//Line line2 = new Line(cy, desy);
	}
	
	private Line findDirectLine(){
		Line line;
		return line = new Line(ship.getX(),ship.getY(),desx,desy);
	}
	
	
	@Override
	public void activate(int delta, float camX, float camY) {
		super.activate(delta, camX, camY, desx, desy);
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
	
}
