package nitrogene.util;


import nitrogene.objecttree.PhysicalObject;
import nitrogene.weapon.LaserLauncher;
import nitrogene.world.World;
import nitrogene.world.Planet;

public class Target {
	
	//gui}
	
	public static float getRotation(LaserLauncher laser) {	
	  	  if((laser.getAngle() >= 0 && laser.getSprite().getImage().getRotation() >= 0) || (laser.getAngle() <= 0 && laser.getSprite().getImage().getRotation() <= 0))
	  	  {
	  		  return laser.getAngle()-laser.getSprite().getImage().getRotation();
	  	  } else {
	  		if(laser.getAngle() >= 0 && laser.getSprite().getImage().getRotation() <= 0) {
	  			float x = Math.abs(laser.getAngle());
	  			float y = Math.abs(laser.getSprite().getImage().getRotation());
	  			
	  			float oneeighty = (180 - x) + (180 - y);
	  			float zero = x + y;
	  			if(zero<oneeighty) {
	  				return zero;
	  			} else {
	  				return -oneeighty;
	  			}
	  		} else if(laser.getAngle() <= 0 && laser.getSprite().getImage().getRotation() >= 0) {
	  			float x = Math.abs(laser.getAngle());
	  			float y = Math.abs(laser.getSprite().getImage().getRotation());
	  			
	  			float oneeighty = (180 - x) + (180 - y);
	  			float zero = x + y;
	  			if(zero<oneeighty) {
	  				return -zero;
	  			} else {
	  				return oneeighty;
	  			}
	  		} else {
	  			return 0;
	  		}

	  	  }
	  	  	 
		}
	public static float getRotation(float l1, float l2) {	
	  	  if((l1 >= 0 && l2 >= 0) || (l1 <= 0 && l2 <= 0))
	  	  {
	  		  return l1-l2;
	  	  } else {
	  		if(l1 >= 0 && l2 <= 0) {
	  			float x = Math.abs(l1);
	  			float y = Math.abs(l2);
	  			
	  			float oneeighty = (180 - x) + (180 - y);
	  			float zero = x + y;
	  			if(zero<oneeighty) {
	  				return zero;
	  			} else {
	  				return -oneeighty;
	  			}
	  		} else if(l1 <= 0 && l2 >= 0) {
	  			float x = Math.abs(l1);
	  			float y = Math.abs(l2);
	  			
	  			float oneeighty = (180 - x) + (180 - y);
	  			float zero = x + y;
	  			if(zero<oneeighty) {
	  				return -zero;
	  			} else {
	  				return oneeighty;
	  			}
	  		} else {
	  			return 0;
	  		}

	  	  }
	  	  	 
		}
	/*
	public static float getRotation(LaserLauncher laser) {
			float a = (laser.getAngle());
			float a1 = (laser.getImage().getRotation());

			float rotation = Math.abs(a1-a);
			if(rotation <=180) {
				System.out.println(Math.toDegrees(a1-a));
					return a1-a;
			} else {
				a = 360-a;
				a1=360-a1;
				System.out.println(Math.toDegrees(a-a1));
				return a-a1;
			}
	} */
	public static PhysicalObject getTargetObject(float f, float g, World map) {
		/*
		for(BoxMesh box : boxmeshlist){
			if(CollisionLibrary.testBoxPoint(box.boundbox, f, g)){
				return box;
			}
		}
		*/
		for(PhysicalObject p : map.getObjList()){
				if(p.isContaining(f,g)){
					return p;
				}
		}
		return null;
		
	}
	public static double[] getRotatedCoordinates(double x, double y, PhysicalObject p) {

		double currentRotation = -p.getSprite().getImage().getRotation();
		double x1 = p.getCenterX() - x;
		double y1 = p.getCenterY() - y;
		double hypotenuse = Math.sqrt(x1*x1 + y1*y1);
		double theta = -(180 + Math.toDegrees(Math.atan2(y1,x1))); 
		double mangle = theta + currentRotation;
		double x2 = Math.cos(Math.toRadians(mangle)) * hypotenuse;
		double y2 = -Math.sin(Math.toRadians(mangle)) * hypotenuse;
		return new double[] {x2, y2};
	}
		
	}


