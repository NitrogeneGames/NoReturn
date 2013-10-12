package nitrogene.util;

import java.util.ArrayList;

import nitrogene.collision.AABB;
import nitrogene.collision.CollisionLibrary;
import nitrogene.collision.Vector;
import nitrogene.core.BoxMesh;
import nitrogene.core.Craft;
import nitrogene.core.Planet;
import nitrogene.core.SLaser;
import nitrogene.core.Zoom;
import nitrogene.weapon.LaserLauncher;
import nitrogene.world.ArenaMap;

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
	
	public Object getTargetObject(float f, float g, ArrayList<BoxMesh> boxmeshlist, ArenaMap map) {
		for(BoxMesh box : boxmeshlist){
			if(CollisionLibrary.testBoxPoint(box.boundbox, f, g)){
				return box;
			}
		}
		for(Planet planet : map.getPlanets()){
				if(CollisionLibrary.testCirclePoint(planet.boundbox, f, g)) {
					return planet;
				}
		}
		return null;
		
	}
		
	}


