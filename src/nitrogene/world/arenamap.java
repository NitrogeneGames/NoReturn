package nitrogene.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import nitrogene.collision.Vector;
import nitrogene.core.Planet;

public class arenamap {
	private int planetnumber;
	private ArrayList<Planet> planetlist;
	Random random;
	
	public arenamap(int planetnumber, int offsetx, int offsety, int mapwidth, int mapheight){
		this.planetnumber = planetnumber;
		planetlist = new ArrayList<Planet>();
		random = new Random();
		generate(offsetx, offsety, mapwidth, mapheight);
	}
	
	private void generate(int offsetx, int offsety, int mapwidth, int mapheight){
		for(int e = 0; e < planetnumber; e++){
			Vector vec = new Vector();
			vec.x = random.nextInt(mapwidth - (2*offsetx)) + offsetx;
			vec.y = random.nextInt(mapheight - (2*offsety)) + offsety;
		}
	}
	
	private void addPlanet(int centerx, int centery, Image image, int maxhp, int scalefactor){
		Planet planet = new Planet(centerx, centery, image, maxhp, scalefactor);
		planetlist.add(planet);
	}
	
	public ArrayList<Planet> getPlanets(){
		return planetlist;
	}
}
