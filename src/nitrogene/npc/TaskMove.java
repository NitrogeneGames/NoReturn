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
	
	private Line findDirectLine(){
		Line line;
		return line = new Line(ship.getX(),ship.getY(),desx,desy);
	}
	
	private Line[] transformLine(Line startline){
		Line slopeline = new Line(0,0);
		Line straightline = new Line(0,0);
		double legy = Math.abs(desy - ship.getY());
		double legx = Math.abs(desx - ship.getX());
		double hyp = Math.sqrt(Math.pow(legx, 2) + Math.pow(legy, 2));
		double anglex = Math.toDegrees((Math.acos(legx/hyp)));
		double angley = Math.toDegrees((Math.acos(legy/hyp)));
		anglex = Math.round(anglex);
		angley = Math.round(angley);
		
		float pointx, pointy;
		if(!(anglex==angley)){
			if(anglex>45){
				pointx = desx;
				pointy = (float) (desy + (legy-legx));
				slopeline.set(ship.getX(),ship.getY(),pointx,pointy);
				straightline.set(pointx,pointy,desx,desy);
			} if (anglex < 45){
				pointx = (float) (desx - (legx - legy));
				pointy = desy;
				slopeline.set(ship.getX(),ship.getY(),pointx,pointy);
				straightline.set(pointx,pointy,desx,desy);
			}
		}
		
		Line[] arrayline = new Line[2];
		arrayline[0] = slopeline;
		arrayline[1] = straightline;
		return arrayline;
	}
	
	@Override
	public void activate(int delta, float camX, float camY) {
		if(ship.getX()!=desx||ship.getY()!=desy){
			if(this.ship.getX() != desx) {
				if(desx > this.ship.getX()) {
					if(!ship.getMovement().getToggle(Direction.UNDERANGLE)) ship.getMovement().Toggle(Direction.UNDERANGLE);
				} else {
					if(!ship.getMovement().getToggle(Direction.UPPERANGLE)) this.ship.getMovement().Toggle(Direction.UPPERANGLE);
				}
			}
			if(this.ship.getY() != desy) {
				if(desy > this.ship.getY()) {
					if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				} else {
					if(!ship.getMovement().getToggle(Direction.BACKWARD)) this.ship.getMovement().Toggle(Direction.BACKWARD);
				}
			}
		}
		else{
			this.close(delta);
			ship.removeTask(this);
		}
		transformLine(findDirectLine());
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
