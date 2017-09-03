package nitrogene.npc;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

import nitrogene.core.GameState;
import nitrogene.core.Resources;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;
import nitrogene.util.Target;
import nitrogene.world.ArenaMap;

public abstract class MovementTask extends Task {
	protected float setRange;
	public MovementTask(NPCship s, float r) {
		super(s);
		setRange = r;
	}
	boolean pathFound = false;
	public Path path;
	ArrayList<Line> pd;
	public void activate(int delta, float camX, float camY, float destx, float desty) {
		float initx = destx;
		float inity = desty;
		float accuracy = 1; //scale of 0 to 1 on how well the AI slows down to the target
		float rotation = this.ship.getRotation();
		
		float startx = ship.getRealCenterX();
		float starty = ship.getRealCenterY();
		float legy = (desty - starty);
		float legx = (destx - startx);
		float anglex = (float) Math.toDegrees((Math.atan2(legy,legx)));
		float hyp = (float) Math.pow(legx*legx + legy*legy, 0.5);		
		float height = ship.getHeight();
		//float yRange = (float) (-Math.cos(ship.getRotation()) * height/2);
		//float xRange = (float) (Math.sin(ship.getRotation()) * height/2);
		float xRange = 0;
		float yRange = 0;
		
		if(!pathFound) {
			path = ArenaMap.getPathShape(ship, startx, startx, destx, desty, 1);	
			pathFound = true;
		}
		/*if(pd==null || pd.size() < 1) {
			this.close();
			isComplete = true;
		} else {
			destx = pd.get(0).getX2();
			desty = pd.get(0).getY2();
		}*/
		/*if(pathFound) {
			Resources.appInstance.getGraphics().draw(p);
		}*/
		
		
		
		
		
		/*Line linePath1 = new Line(startx, starty, destx2, desty2);
		Line linePath2 = new Line(startx, starty, destx2, desty2);

		
		
		int counter = 0;
		
		while((!pathFound)) {
			boolean collisionFlag1 = false;
			boolean collisionFlag2 = false;
			ArrayList<PhysicalObject> issue1 = new ArrayList<PhysicalObject>();
			ArrayList<PhysicalObject> issue2 = new ArrayList<PhysicalObject>();
			for(PhysicalObject o : objs) {
				//System.out.println("searching object at " + o.getRealCenterX() + " " + o.getRealCenterY());
				//System.out.println("line is " + linePath1.getStart().x + " " + linePath2.getStart().y + " to " + linePath2.getEnd().x + " " + linePath2.getEnd().y);
				//System.out.println("cp1line is " + linePath1.getStart().x + " " + linePath1.getStart().y + " to " + linePath1.getEnd().x + " " + linePath1.getEnd().y);
				if(o.isColliding(linePath1) || o.isColliding(linePath1.transform(Transform.createTranslateTransform(xRange, yRange))) || o.isColliding(linePath1.transform(Transform.createTranslateTransform(-xRange, -yRange)))) {
					//System.out.println("cp2line is " + linePath1.getStart().x + " " + linePath1.getStart().y + " to " + linePath1.getEnd().x + " " + linePath1.getEnd().y);
					collisionFlag1 = true;
					issue1.add(o);
				}
				if(o.isColliding(linePath2) || o.isColliding(linePath2.transform(Transform.createTranslateTransform(xRange, yRange))) || o.isColliding(linePath2.transform(Transform.createTranslateTransform(-xRange, -yRange)))) {
					collisionFlag2 = true;
					issue2.add(o);
				}
			}
			//FIND CLOSEST PHYSICAL OBJECT THATS A PROBLEM
			if(issue1.size() > 0) {
				PhysicalObject smalles1 = null;
				for(PhysicalObject p : issue1) {
					if(smalles1 == null) {
						smalles1 = p;
					} else {
						smalles1 = Resources.getCloserObject(startx, starty, p, smalles1);
					}
				}
				//Line l1 = new Line(startx, starty, smalles1.getRealCenterX(), smalles1.getRealCenterY());
				System.out.println("line1 is " + linePath1.getStart().x + " " + linePath1.getStart().y + " to " + linePath1.getEnd().x + " " + linePath1.getEnd().y);
				linePath1 = (Line) linePath1.transform(Transform.createRotateTransform((float) Math.toRadians(1), startx, starty));
				System.out.println("line1 is now " + linePath1.getStart().x + " " + linePath1.getStart().y + " to " + linePath1.getEnd().x + " " + linePath1.getEnd().y);
			}
			if(issue2.size() > 0) {
				PhysicalObject smalles2 = null;
				for(PhysicalObject p : issue2) {
					if(smalles2 == null) {
						smalles2 = p;
					} else {
						smalles2 = Resources.getCloserObject(startx, starty, p, smalles2);
					}
				}
				System.out.println("line2 is " + linePath2.getStart().x + " " + linePath2.getStart().y + " to " + linePath2.getEnd().x + " " + linePath2.getEnd().y);
				linePath2 = (Line) linePath2.transform(Transform.createRotateTransform((float) Math.toRadians(-1), startx, starty));
				System.out.println("line2 is now " + linePath2.getStart().x + " " + linePath2.getStart().y + " to " + linePath2.getEnd().x + " " + linePath2.getEnd().y);
			}
			//are we colliding? if not, lets translate the lines and check
			if(!collisionFlag1) {
				pathFound = true;
				
				if(linePath1.getStart().x == startx && linePath1.getStart().y == starty) {
					destx = linePath1.getEnd().x;
					desty = linePath1.getEnd().y;
				} else {
					destx = linePath1.getStart().x;
					desty = linePath1.getStart().y;				
				}
				//System.out.println("Starting: " + startx + ", " + starty);
				System.out.println("New destination1: " + destx + ", " + desty + " after rotating " + counter);
			}else if(!collisionFlag2) {
				pathFound = true;
				if(linePath2.getStart().x == startx && linePath2.getStart().y == starty) {
					destx = linePath2.getEnd().x;
					desty = linePath2.getEnd().y;
				} else {
					destx = linePath2.getStart().x;
					desty = linePath2.getStart().y;				
				}
				System.out.println("New destination2: " + destx + ", " + desty + " after rotating " + counter);
			}
			counter++;
			if(counter > 10000) {
				pathFound = true;
			}
		}*/
		
		//THIS PART WORKS, ITLL GET U TO DESX, DESY
		
		float range = setRange;
		/*float range = 0;
		if(!(pd.size() > 1)) {
			range = setRange;
		}*/
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
		//CHECK FOR OBJECTS IN THE WAY
		
		if(initx == destx && inity == desty) {
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
				/*if(pd.size() > 1) {
					pd.remove(0);
				} else {
					this.isComplete = true;
				}*/
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
				/*if(pd.size() > 1) {
					pd.remove(0);
				} else {
					this.isComplete = true;
				}*/
			}
		}
		//transformLine(findDirectLine());
		this.ship.move(20,delta);
	}
}
