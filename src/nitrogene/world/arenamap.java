package nitrogene.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.collision.Vector;
import nitrogene.core.Planet;

public class ArenaMap {
	private int planetnumber;
	private int plapro;
	private Image star2;
	private Image sun1;
	private int jj;
	private ArrayList<Planet> planetlist;
	private ArrayList<Image> imagelist;
	Random random;
	
	public ArenaMap(int planetnumber, int offsetx, int offsety, int mapwidth, int mapheight) throws SlickException{
		this.planetnumber = planetnumber;
		planetlist = new ArrayList<Planet>();
		imagelist = new ArrayList<Image>();
		random = new Random();
		//star2 = new Image("res/star2.png");
		sun1 = new Image("res/sun_1.png");
		//imagelist.add(star2);
		imagelist.add(sun1);
		generate(offsetx, offsety, mapwidth, mapheight);
	}
	
	private void generate(int offsetx, int offsety, int mapwidth, int mapheight){
		for(int e = 0; e < planetnumber; e++){
			Vector vec = new Vector();
			vec.x = random.nextInt(mapwidth - (2*offsetx)) + offsetx;
			vec.y = random.nextInt(mapheight - (2*offsety)) + offsety;
			int radius = random.nextInt(200)+200;
			int maxhp = random.nextInt(1000) +500;
			int imagenum = random.nextInt(imagelist.size());
			
			//check for planet validity
			for(int i = 0; i < planetlist.size(); i++){
				Planet planet = planetlist.get(i);
				//radius of this planet + other planet + constant (for ship) + factor for amt of planets total
				if(Math.sqrt(vec.distSQ(planet.boundbox.center)) <= radius + 300 + planet.boundbox.radius){
					radius = random.nextInt(200)+200;
					vec.x = random.nextInt(mapwidth - (2*offsetx)) + offsetx;
					vec.y = random.nextInt(mapheight - (2*offsety)) + offsety;
					System.out.println(Math.sqrt(vec.distSQ(planet.boundbox.center)) + "  " + (radius+300+planet.boundbox.radius));
					i=0;
				}
				System.out.println("AND ONE");
			}
			addPlanet((int)vec.x,(int)vec.y,imagelist.get(imagenum),maxhp,(radius*2)/imagelist.get(imagenum).getWidth());
			System.out.println(planetlist.size());
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
