package nitrogene.npc;

import org.newdawn.slick.geom.Line;

public class TaskPathfind extends Task {
	private int desx, desy, cx, cy;
	private Line line1;
	private Line line2;
	
	public TaskPathfind(NPCship s, int desx, int desy, int cx, int cy){
		super(s);
		this.desx = desx;
		this.desy = desy;
		this.cx = cx;
		this.cy = cy;
		findlines();
	}
	
	//plot course to destination coordinates
	private void findlines(){
		Line line1 = new Line(cx, desx);
		Line line2 = new Line(cy, desy);
	}
	
	private void findobstruction(){
		
	}

	@Override
	public void activate(int delta, float camX, float camY) {
		
	}

	@Override
	public void close(int delta, float camX, float camY) {
		
	}
	
	
	//create path from lines
	
	
	//find time between paths
	
	
	//calculate acceleration and toggles
	
	
	//execute with timer
	
}
