package nitrogene.npc;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import nitrogene.core.GameObject;
import nitrogene.core.GameState;
import nitrogene.core.Resources;
import nitrogene.util.Direction;
import nitrogene.util.Target;
import nitrogene.world.World;
import nitrogene.world.TravelPath;

public abstract class MovementTask extends Task {
	
	
	public static boolean pathFinding = false;
	
	
	
	protected float setRange;
	float lastX = 0;
	float lastY = 0;
	private ArrayList<GameObject> ignore = new ArrayList<GameObject>();
	public void addIgnore(GameObject o) {
		if(ignore.contains(o)) ignore.add(o);
	}
	public MovementTask(NPCship s, float r) {
		super(s);
		setRange = r;
	}
	public MovementTask(NPCship s, float r, GameObject ign) {
		super(s);
		setRange = r;
		this.ignore.add(ign);
	}
	boolean pathFound = false;
	public Path image;
	public TravelPath path;
	public void activate(int delta, float camX, float camY, float destx, float desty) {
		float initx = destx;
		float inity = desty;
		float accuracy = 1; //scale of 0 to 1 on how well the AI slows down to the target
		float rotation = this.ship.getRotation();
		
		float startx = ship.getRealCenterX();
		float starty = ship.getRealCenterY();
		float legy = (desty - starty);
		float legx = (destx - startx);
		
		float hyp = (float) Math.pow(legx*legx + legy*legy, 0.5);
		float height = ship.getHeight();
		//float yRange = (float) (-Math.cos(ship.getRotation()) * height/2);
		//float xRange = (float) (Math.sin(ship.getRotation()) * height/2);
		float xRange = 0;
		float yRange = 0;
		if(!pathFound && pathFinding) {
			lastX = 0;
			lastY = 0;
			pathFound = true;
			ArrayList<float[]> pathCoords = Resources.getPath(ship, startx, startx, destx, desty, 50, ignore);
			path = Resources.toTravelPath(pathCoords);
			image = Resources.toPathShape(pathCoords);
		}
		float anglex;
		if(!pathFinding) {
			anglex = (float) Math.toDegrees((Math.atan2(legy,legx)));
		} else {
			anglex = path.getAngle();
		}
		float range = setRange;

		float Vnet =  ship.getMovement().getDirVelocity();
		//d = hyp, Vi = Vnet, Vf = 0
		//Vf2 = Vi2 + 2ad
		//Vi2 = -2ad
		//a = Vi2/-2d
		float accel = (float) ((float)(Math.pow(Vnet, 2)*-.5)/(hyp-range));
		float cons = 10;
		accel = accel/cons;
		
		float theta = Target.getRotation(rotation, anglex);
		//System.out.println("current rotation: " + rotation);
		//float theta2 = Target.getRotation(path.lastTheta, path.getAngle());
		//System.out.println(theta + " theta");
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
			//vi = Vnet
			//vf = Vturn (3f)
			//d = path.getTurnDist()
			//if a >= ship.getMovement().maxDirAccel
			
			//Vf2 = Vi2 + 2ad
			//Vf2 - Vi2 = -2ad
			//a = (Vi2 - Vf2)/2d
			//Resources.log(path.distToTurn() + ", " + Vnet);
			if(pathFinding) {
				float turnSpeed = 14f;
				float acc = (Vnet*Vnet - turnSpeed*turnSpeed)/(2*path.distToTurn());
				acc = acc/cons;
				if(acc >= ship.getMovement().maxDirAccel && Vnet > turnSpeed) {
					if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				} else if(Math.abs(theta) > 30 ) {
					if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				} else if(Math.abs(theta) <= 30) {
					if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);		
				}
			} else {
				if(Math.abs(theta) > 30 ) {
					if(ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);
				} else if(Math.abs(theta) <= 30) {
					if(!ship.getMovement().getToggle(Direction.FORWARD)) this.ship.getMovement().Toggle(Direction.FORWARD);		
				}
			}
		}
		if(pathFinding) {
			path.registerMovement(ship.getX() - lastX, ship.getY() - lastY);
		}
		lastX = ship.getX();
		lastY = ship.getY();
		if(hyp < 100) {
			this.isComplete = true;
			this.close();
		}
		/*if(initx == destx && inity == desty) {
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

			}
		} else {
			if(Math.abs(ship.getX()-destx) > 30||Math.abs(ship.getY()-desty) > 30){
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
			} else {

			}
		}*/
		//transformLine(findDirectLine());
		//this.ship.move(20,delta,path);
	}
}
