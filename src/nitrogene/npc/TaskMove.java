package nitrogene.npc;

import nitrogene.util.Direction;

import org.newdawn.slick.geom.Line;

public class TaskMove extends Task {
	private float desx, desy, cx, cy;
	private Line line1;
	private Line line2;
	public int taskID = 1;
	public TaskMove(NPCship s, float desx, float desy){
		super(s);
		this.desx = desx;
		this.desy = desy;
		findlines();
	}
	
	//plot course to destination coordinates
	private void findlines(){
		//Line line1 = new Line(cx, desx);
		//Line line2 = new Line(cy, desy);
	}
	
	private void findobstruction(){
		
	}

	@Override
	public void activate(int delta, float camX, float camY) {
		if(this.ship.getX() != desx) {
			if(desx > this.ship.getX()) {
				if(!ship.getMovement().getToggle(Direction.RIGHT)) ship.getMovement().Toggle(Direction.RIGHT);
			} else {
				if(!ship.getMovement().getToggle(Direction.LEFT)) this.ship.getMovement().Toggle(Direction.LEFT);
			}
		}
		if(this.ship.getY() != desy) {
			if(desy > this.ship.getY()) {
				if(!ship.getMovement().getToggle(Direction.UP)) this.ship.getMovement().Toggle(Direction.UP);
			} else {
				if(!ship.getMovement().getToggle(Direction.DOWN)) this.ship.getMovement().Toggle(Direction.DOWN);
			}
		} else {
			this.close(delta);
			ship.removeTask(this); 
		}
		this.ship.move(20,delta);
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
	
}
