package nitrogene.util;

import nitrogene.core.Craft;
import nitrogene.core.SLaser;
import nitrogene.core.Zoom;
import nitrogene.weapon.LaserLauncher;

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
	public static void updateLasers(Craft craft, float camX, float camY) {
		for(int p = 0; p<craft.laserlist.size(); p++){
			   LaserLauncher cannon = craft.laserlist.get(p);
			   cannon.update((float) (craft.getX() ), (float) (craft.getY()));
			      if(((cannon.getAngle()-cannon.getImage().getRotation()) != 0)) {
			    	  float rota = Target.getRotation(cannon);
			    	  float dist = Math.abs(rota);
			    		  if(dist >= 100) {
			    			  if(dist >= 200) {
			    				  if(dist >= 300) {
						       cannon.getImage().rotate(rota/50);
						       
						      } else {   //<300
						       cannon.getImage().rotate(rota/40);
						      }
						      } else {  //<200
						       cannon.getImage().rotate(rota/30);
						      }
						      } else { //<100
						      cannon.getImage().rotate(rota/20);
						      }
			      } else {
			          cannon.getImage().setRotation(cannon.getAngle());
			      }
			      cannon.getImage().draw(cannon.getX()+craft.getX(), cannon.getY()+craft.getY());
			      
			      //bullet draw
			      for(int i = 0;i<craft.laserlist.get(p).slaserlist.size();i++){
						SLaser laser = craft.laserlist.get(p).slaserlist.get(i);
						if (laser.isRotated == false){
							laser.isRotated = true;
							//basicTestLaser.play(1f, 0.5f);
							laser.getImage().setCenterOfRotation(cannon.getImage().getWidth()/2,cannon.getImage().getHeight()/2);
							laser.getImage().rotate(laser.getAngle());
						}
						if(laser.location.getX()-laser.getImage().getWidth()>Zoom.getZoomWidth()+camX||
								laser.location.getX()+laser.getImage().getWidth()<camX||
								laser.location.getY()-laser.getImage().getHeight()>Zoom.getZoomHeight()+camY||
								laser.location.getY()+laser.getImage().getHeight()<camY){
							laser = null;
							continue;
						}
						laser.getImage().draw(laser.location.getX(), laser.location.getY(),laser.getSize());
						laser = null;
				}
			}
		
		}
	}


