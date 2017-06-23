package nitrogene.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.collision.Vector;
import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.inventory.DroppedItem;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;

public class ArenaMap {
	private int planetnumber;
	private ArrayList<Planet> planetlist;
	private ArrayList<Craft> craftlist;
	private ArrayList<Asteroid> asteroidlist;
	private long asteroidelapsed, asteroidstart;
	private int asteroiddelay;
	private Craft craft;
	private ArrayList<PhysicalObject> objlist;
	private ArrayList<DroppedItem> itemlist;
	private int upbound, downbound, rightbound, leftbound, mapwidth, mapheight,offsetx, offsety;
	private Timer asteroidtimer;
	public boolean isLoaded;
	Random random;
	
	public void tick() throws SlickException{
		//randomizer for delay
		asteroiddelay = random.nextInt(500)+500;
		//variable for belt seperation
		int n = 10;
		asteroidtimer.setInitialDelay(asteroiddelay);
		asteroidtimer.restart();
		asteroidstart = System.currentTimeMillis();
		
		Image img = (Image) AssetManager.get().get("asteroid");
		
		Asteroid a1 = new Asteroid(0+random.nextInt(50),-1000,0,mapheight+1000);
		a1.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(a1);
		Asteroid a2 = new Asteroid(n+random.nextInt(50),-1000,0,mapheight+1000);
		a2.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(a2);
		Asteroid a3 = new Asteroid(2*n+random.nextInt(50),-1000,0,mapheight+1000);
		a3.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(a3);
		
		Asteroid b1 = new Asteroid(-1000,0+random.nextInt(50),mapwidth+1000,0);
		b1.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(b1);
		Asteroid b2 = new Asteroid(-1000,n+random.nextInt(50),mapwidth+1000,0);
		b2.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(b2);
		Asteroid b3 = new Asteroid(-1000,2*n+random.nextInt(50),mapwidth+1000,0);
		b3.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(b3);
		
		Asteroid c1 = new Asteroid(0+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000);
		c1.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(c1);
		Asteroid c2 = new Asteroid(n+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000);
		c2.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(c2);
		Asteroid c3 = new Asteroid(2*n+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000);
		c3.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(c3);
		
		Asteroid d1 = new Asteroid(-1000,0+mapheight+random.nextInt(50),mapwidth+1000,mapheight);
		d1.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(d1);
		Asteroid d2 = new Asteroid(-1000,n+mapheight+random.nextInt(50),mapwidth+1000,mapheight);
		d2.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(d2);
		Asteroid d3 = new Asteroid(-1000,2*n+mapheight+random.nextInt(50),mapwidth+1000,mapheight);
		d3.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
		this.getAsteroids().add(d3);
	}
	
	public ArenaMap(int planetnumber, int offsetx, int offsety, int mapwidth, int mapheight, Craft craft) throws SlickException{
		this.planetnumber = planetnumber;
		objlist = new ArrayList<PhysicalObject>();
		itemlist = new ArrayList<DroppedItem>();
		planetlist = new ArrayList<Planet>();
		random = new Random();
		//star2 = new Image("res/star2.png");
		this.craft = craft;
		asteroidlist = new ArrayList<Asteroid>();
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
			
			addPlanet((int)vec.x,(int)vec.y,maxhp,radius);
			System.out.println("PLANET  "+vec.x+ "   :   "+vec.y);
		}
	}
	
	private void addPlanet(int centerx, int centery, int maxhp, int radius){
		Planet planet = new Planet(centerx, centery,  maxhp, radius);
		this.loadPlanet(planet);
		objlist.add(planet);
		planetlist.add(planet);
	}
	
	public void loadPlanets(){
		ArrayList<Image> imagelist = new ArrayList<Image>();
		imagelist.add((Image) AssetManager.get().get("sun1"));
		imagelist.add((Image) AssetManager.get().get("volcanicplnaet"));
		for(Planet p : this.getPlanets()){
			int imagenum = random.nextInt(imagelist.size());
			p.load(imagelist.get(imagenum), (p.getRadius()*2)/imagelist.get(imagenum).getWidth(), this);
		}
	}
	
	private void loadPlanet(Planet p){
		ArrayList<Image> imagelist = new ArrayList<Image>();
		imagelist.add((Image) AssetManager.get().get("sun1"));
		imagelist.add((Image) AssetManager.get().get("volcanicplanet"));
		int imagenum = random.nextInt(imagelist.size());
		p.load(imagelist.get(imagenum), (p.getRadius()*2)/imagelist.get(imagenum).getWidth(), this);
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
