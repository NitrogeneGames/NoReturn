package nitrogene.util;


import nitrogene.core.Planet;
import nitrogene.weapon.LaserLauncher;
import nitrogene.world.ArenaMap;

public class Target {
	
	//gui}
	public static float getRotation(LaserLauncher laser) {
		//return getRotationNew(laser);

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
	public static float getRotationNew(LaserLauncher laser) {
			float news = (laser.getAngle());
			float old = (laser.getImage().getRotation());
			if (news < 0) news += 360;
			if (old < 0) old += 360;
			float dist1 = Math.abs(news - old); //DISTANCE WHEN GOING CLOCKWISE
			float enddist;
			boolean nbo = false;
			if (news >= old) nbo = true;
			if(dist1 < 180) {
				//SHORTEST DISTANCE IS SMALLER TO LARGER WHEN GOING CLOCKWISE
				enddist = dist1;
				if(!nbo) {
					enddist = -enddist;
				}
			} else {
				//SHORTEST DISTANCE IS LARGER TO SMALLER WHEN GOING CLOCKWISE
				enddist = Math.abs(180 - dist1);
				if(!nbo) {
					enddist = -enddist;
				}
			}
			return enddist;
	}
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


