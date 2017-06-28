package nitrogene.npc;

import nitrogene.util.Direction;
import nitrogene.util.Target;

public abstract class MovementTask extends Task {

	public MovementTask(NPCship s) {
		super(s);
	}
	public void activate(int delta, float camX, float camY, float desty, float destx) {
		float rotation = this.ship.getRotation();
		//System.out.println(rotation);
		float legy = (desty - ship.getY());

		float legx = (destx - ship.getX());
		float anglex = (float) Math.toDegrees((Math.atan2(legy,legx)));
		float theta = Target.getRotation(rotation, anglex);
		if(ship.getX()!=destx||ship.getY()!=desty){
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
			if(Math.abs(theta) > 30) {
				if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
			} else 	if(Math.abs(theta) < 30) {
				if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);		
			}
		}else{
			this.close(delta);
			ship.removeTask(this);
		}
		//transformLine(findDirectLine());
		this.ship.move(20,delta);
	}
}
