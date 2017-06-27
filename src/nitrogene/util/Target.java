package nitrogene.util;


import nitrogene.weapon.LaserLauncher;
import nitrogene.world.ArenaMap;
import nitrogene.world.Planet;

public class Target {
	
	//gui}
	
	public static float getRotation(LaserLauncher laser) {	
	  	  if((laser.getAngle() >= 0 && laser.getImage().getRotation() >= 0) || (laser.getAngle() <= 0 && laser.getImage().getRotation() <= 0))
	  	  {
	  		  return laser.getAngle()-laser.getImage().getRotation();
	  	  } else {
	  		if(laser.getAngle() >= 0 && laser.getImage().getRotation() <= 0) {
	  			float x = Math.abs(laser.getAngle());
	  			float y = Math.abs(laser.getImage().getRotation());
	  			
	  			float oneeighty = (180 - x) + (180 - y);
	  			float zero = x + y;
	  			if(zero<oneeighty) {
	  				return zero;
	  			} else {
	  				return -oneeighty;
	  			}
	  		} else if(laser.getAngle() <= 0 && laser.getImage().getRotation() >= 0) {
	  			float x = Math.abs(laser.getAngle());
	  			float y = Math.abs(laser.getImage().getRotation());
	  			
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
	public static Object getTargetObject(float f, float g, ArenaMap map) {
		/*
		for(BoxMesh box : boxmeshlist){
			if(CollisionLibrary.testBoxPoint(box.boundbox, f, g)){
				return box;
			}
		}
		*/
		for(Planet planet : map.getPlanets()){
				if(planet.isContaining(f,g)){
					return planet;
				}
		}
		return null;
		
	}
		
	}


