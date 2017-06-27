package nitrogene.npc;

import nitrogene.core.Zoom;
import nitrogene.util.Direction;
import nitrogene.util.Target;

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
	
	private Line findDirectLine(){
		Line line;
		return line = new Line(ship.getX(),ship.getY(),desx,desy);
	}
	
	
	@Override
	public void activate(int delta, float camX, float camY) {
		float rotation = this.ship.getRotation();
		//System.out.println(rotation);
		float legy = (desy - ship.getY());
		float legx = (desx - ship.getX());
		float anglex = (float) Math.toDegrees((Math.atan(legy/legx)));
		float theta = Target.getRotation(rotation, anglex);
		if(ship.getX()!=desx||ship.getY()!=desy){
			if(Math.abs(theta) < 2) {
				if(ship.getMovement().getToggle(Direction.UNDERANGLE)) ship.getMovement().Toggle(Direction.UNDERANGLE);
				if(ship.getMovement().getToggle(Direction.UPPERANGLE)) ship.getMovement().Toggle(Direction.UPPERANGLE);
			}
			else if(theta > 0) {
				if(!ship.getMovement().getToggle(Direction.UPPERANGLE)) ship.getMovement().Toggle(Direction.UPPERANGLE);
				if(ship.getMovement().getToggle(Direction.UNDERANGLE)) ship.getMovement().Toggle(Direction.UNDERANGLE);
			} else {
				if(!ship.getMovement().getToggle(Direction.UNDERANGLE)) this.ship.getMovement().Toggle(Direction.UNDERANGLE);
				if(ship.getMovement().getToggle(Direction.UPPERANGLE)) ship.getMovement().Toggle(Direction.UPPERANGLE);
			}
			if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
		}
		else{
			this.close(delta);
			ship.removeTask(this);
		}
		//transformLine(findDirectLine());
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
