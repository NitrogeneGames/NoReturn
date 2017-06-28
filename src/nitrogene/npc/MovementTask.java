package nitrogene.npc;

import nitrogene.util.Direction;
import nitrogene.util.Target;

public abstract class MovementTask extends Task {
	protected float range;
	public MovementTask(NPCship s, float r) {
		super(s);
		range = r;
	}
	public void activate(int delta, float camX, float camY, float desty, float destx) {
		float accuracy = 1; //scale of 0 to 1 on how well the AI slows down to the target
		float rotation = this.ship.getRotation();
		//System.out.println(rotation);
		float legy = (desty - ship.getY());

		float legx = (destx - ship.getX());
		float anglex = (float) Math.toDegrees((Math.atan2(legy,legx)));
		float hyp = (float) Math.pow(legx*legx + legy*legy, 0.5);
		
		
		//OH BOI ITS MECHANICS TIME
		float Vnet =  ship.getMovement().getDirVelocity();
		//d = hyp, Vi = Vnet, Vf = 0
		//Vf2 = Vi2 + 2ad
		//Vi2 = -2ad
		//a = Vi2/-2d
		float accel = (float) ((float)(Math.pow(Vnet, 2)*-.5)/(hyp-range));
		float cons = 10;
		accel = accel/cons;
		///System.out.println(ship.getMovement().maxDirAccel + " max accel");
		//System.out.println(Vnet + " current velocity");
		/*if(Math.abs(accel/1000) > ship.getMovement().maxDirAccel) {
			ship.getMovement().setDirAcceleration(accel);
			if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);	
		} else {
			if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);	
		}*/
		
		
		
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
			if(range > hyp && (Math.abs(accel) > ship.getMovement().maxDirAccel*2*accuracy)) {
				if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				if(!ship.getMovement().getToggle(Direction.BACKWARD)) this.ship.getMovement().Toggle(Direction.BACKWARD);
			} else if(Math.abs(accel) > ship.getMovement().maxDirAccel*2*accuracy && Vnet > 0) {
				if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				if(!ship.getMovement().getToggle(Direction.BACKWARD)) this.ship.getMovement().Toggle(Direction.BACKWARD);
			} else if (hyp > range){
				if(Math.abs(theta) > 30) {
					if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				} else 	if(Math.abs(theta) <= 30) {
					if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);		
				}
			}
		}else{
			this.isComplete = true;
		}
		//transformLine(findDirectLine());
		this.ship.move(20,delta);
	}
}
