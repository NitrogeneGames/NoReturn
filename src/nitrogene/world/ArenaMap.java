package nitrogene.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.collision.Vector;
import nitrogene.core.Craft;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;

public class ArenaMap {
	private int planetnumber;
	private Image sun1, volcanicplanet;
	private Image img;
	private ArrayList<Planet> planetlist;
	private ArrayList<Craft> craftlist;
	private ArrayList<Image> imagelist;
	private ArrayList<Asteroid> asteroidlist;
	private long asteroidelapsed, asteroidstart;
	private int asteroiddelay;
	private Craft craft;
	private ArrayList<PhysicalObject> objlist;
	private ArrayList<DroppedItem> itemlist;
	private int upbound, downbound, rightbound, leftbound, mapwidth, mapheight,offsetx, offsety;
	private Timer asteroidtimer;
	Random random;
	
	public void tick() throws SlickException{
		//randomizer for delay
		asteroiddelay = random.nextInt(500)+500;
		//variable for belt seperation
		int n = 10;
		asteroidtimer.setInitialDelay(asteroiddelay);
		asteroidtimer.restart();
		asteroidstart = System.currentTimeMillis();
		
		this.getAsteroids().add(new Asteroid(0+random.nextInt(50),-1000,0,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(n+random.nextInt(50),-1000,0,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(2*n+random.nextInt(50),-1000,0,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		
		this.getAsteroids().add(new Asteroid(-1000,0+random.nextInt(50),mapwidth+1000,0,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(-1000,n+random.nextInt(50),mapwidth+1000,0,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(-1000,2*n+random.nextInt(50),mapwidth+1000,0,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		
		this.getAsteroids().add(new Asteroid(0+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(n+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(2*n+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000,img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this));
		
		this.getAsteroids().add(new Asteroid(-1000,0+mapheight+random.nextInt(50),mapwidth+1000,mapheight,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(-1000,n+mapheight+random.nextInt(50),mapwidth+1000,mapheight,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		this.getAsteroids().add(new Asteroid(-1000,2*n+mapheight+random.nextInt(50),mapwidth+1000,mapheight,img,Direction.FORWARD,random.nextFloat()*2f+2f,this));
		
	}
	
	public ArenaMap(int planetnumber, int offsetx, int offsety, int mapwidth, int mapheight, Craft craft) throws SlickException{
		this.planetnumber = planetnumber;
		objlist = new ArrayList<PhysicalObject>();
		itemlist = new ArrayList<DroppedItem>();
		planetlist = new ArrayList<Planet>();
		imagelist = new ArrayList<Image>();
		random = new Random();
		//star2 = new Image("res/star2.png");
		volcanicplanet = new Image("res/volcanicplanet2.png");
		volcanicplanet.setFilter(Image.FILTER_NEAREST);
		sun1 = new Image("res/sun_1.png");
		sun1.setFilter(Image.FILTER_NEAREST);
		//imagelist.add(star2);
		imagelist.add(sun1);
		imagelist.add(volcanicplanet);
		this.craft = craft;
		asteroidlist = new ArrayList<Asteroid>();
		img = new Image("res/asteroid1.png");
		img.setFilter(Image.FILTER_NEAREST);
		craftlist = new ArrayList<Craft>();
		asteroidtimer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					tick();
				} catch (SlickException e1) {
					e1.printStackTrace();
				}
			}
		});
		asteroiddelay = 1000;
		asteroidstart = System.currentTimeMillis();
		asteroidPause();
		
		this.mapwidth = mapwidth;
		this.mapheight = mapheight;
		this.offsetx = offsetx;
		this.offsety = offsety;
		this.upbound = offsety;
		this.downbound = mapheight - offsety;
		this.leftbound = offsetx;
		this.rightbound = mapwidth - offsetx;
	}
	
	public void generate(int offsetx, int offsety, int mapwidth, int mapheight, Craft craft){
		main:
		for(int e = 1; e <= planetnumber; e++){
			
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
				if(Math.sqrt(vec.distSQ(new Vector(planet.getRealCenterX(),planet.getRealCenterY()))) <= radius + 500 + (planet.getImage().getWidth()*planet.getScale()/2))
						{
					radius = random.nextInt(200)+200;
					vec.x = random.nextInt(mapwidth - (2*offsetx)) + offsetx;
					vec.y = random.nextInt(mapheight - (2*offsety)) + offsety;
					i=-1;
				}
			}
			
			if(Math.sqrt(vec.distSQ(new Vector(craft.getRealCenterX(),craft.getRealCenterY()))) <= (craft.getImage().getWidth()/2) + radius + 200){
				e--;
				continue main;
			}
			
			addPlanet((int)vec.x,(int)vec.y,imagelist.get(imagenum),maxhp,(radius*2)/imagelist.get(imagenum).getWidth());
			System.out.println("PLANET  "+vec.x+ "   :   "+vec.y);
		}
	}
	
	private void addPlanet(int centerx, int centery, Image image, int maxhp, int scalefactor){
		Planet planet = new Planet(centerx, centery, image, maxhp, scalefactor, this);
		objlist.add(planet);
		planetlist.add(planet);
	}
	
	public void addCraft(Craft craft){
		craftlist.add(craft);
	}
	
	public ArrayList<Planet> getPlanets(){
		return planetlist;
	}
	
	public void removePlanet(Planet planet){
		this.planetlist.remove(planet);
	}
	
	public void setPlanets(ArrayList<Planet> planets){
		this.planetlist = planets;
	}
	
	public void removePlanet(int i){
		this.planetlist.remove(i);
	}
	
	public ArrayList<Craft> getCrafts(){
		return craftlist;
	}
	
	public int getUpbound(){
		return upbound;
	}
	public int getDownbound(){
		return downbound;
	}
	public int getRightbound(){
		return rightbound;
	}
	public int getLeftbound(){
		return leftbound;
	}
	public int getMapWidth(){
		return mapwidth;
	}
	public int getMapHeight(){
		return mapheight;
	}
	public int getOffsetX(){
		return offsetx;
	}
	public int getOffsetY(){
		return offsety;
	}
	public void addDroppedItem(DroppedItem item){
		this.itemlist.add(item);
	}
	public ArrayList<DroppedItem> getDroppedItem(){
		return itemlist;
	}
	public ArrayList<Asteroid> getAsteroids(){
		return asteroidlist;
	}
	//any new item categories are added into the general list HERE!
	public ArrayList<PhysicalObject> getObjList(){
		ArrayList<PhysicalObject> objs = new ArrayList<PhysicalObject>();
		for(Planet mesh : planetlist){
			objs.add(mesh);
		}
		for(Craft craft : craftlist){
			objs.add(craft);
		}
		return objs;
	}
	public void asteroidPause(){
		asteroidelapsed = System.currentTimeMillis() - asteroidstart;
		if(asteroidtimer.isRunning()){
			asteroidtimer.stop();
		}
	}
	public void asteroidResume(){
		if((int) (asteroiddelay-asteroidelapsed)<0){
			asteroidtimer.setInitialDelay(0);
			asteroidtimer.setDelay(0);
		} else{
		asteroidtimer.setInitialDelay((int) (asteroiddelay-asteroidelapsed));
		asteroidtimer.setDelay((int) (asteroiddelay-asteroidelapsed));
		}
		asteroidtimer.start();
	}
}
