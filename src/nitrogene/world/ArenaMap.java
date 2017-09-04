package nitrogene.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;

import nitrogene.collision.Vector;
import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.core.Resources;
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
	public void loadCraft(Craft c) {
		/*int cons = 10;
		int counter = 0;
		boolean flag = false;
		while(flag == false && counter < 100) {
			flag = true;
			forl:*/
			for(Planet p : getPlanets()) {
				if(p.isColliding(c)) {
					/*flag = false;
					if(counter >= 50) {
						c.setX(c.getX() + cons);
					} else {
						c.setX(c.getY() + cons);
					}
					break forl;*/
					System.out.println("Collision found");
				}
			}
		//}
		
		this.craftlist.add(c);
	}
	public void tick() throws SlickException{
		//randomizer for delay
		asteroiddelay = random.nextInt(500)+500;
		//variable for belt seperation
		int n = 10;
		//variable for the amount of asteroids to spawn per tick
		int asteroid_amt = 3;
		asteroidtimer.setInitialDelay(asteroiddelay);
		asteroidtimer.restart();
		asteroidstart = System.currentTimeMillis();
		
		String img = "asteroid";
		
		for(int i=0; i<asteroid_amt; i++){
			//Downward moving asteroids
			Asteroid a = new Asteroid((n*i)+random.nextInt(50),-1000,0,mapheight+1000);
			a.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
			this.getAsteroids().add(a);
			
			//Right moving asteroids (on the bottom)
			Asteroid b = new Asteroid(-1000,(n*i)+random.nextInt(50),mapwidth+1000,0);
			b.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
			this.getAsteroids().add(b);
			
			Asteroid c = new Asteroid((n*i)+mapwidth+random.nextInt(50),-1000,mapwidth,mapheight+1000);
			c.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
			this.getAsteroids().add(c);
			
			Asteroid d = new Asteroid(-1000,(n*i)+mapheight+random.nextInt(50),mapwidth+1000,mapheight);
			d.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
			this.getAsteroids().add(d);
		}
		
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
	
	public void generate(int offsetx, int offsety, int mapwidth, int mapheight, Craft c) throws SlickException{
		//Generate initial amount of planets
		float counter = 0;
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
				if(Math.sqrt(vec.distSQ(new Vector(planet.getRealCenterX(),planet.getRealCenterY()))) <= radius + 500 + (planet.getSprite().getImage().getWidth()*planet.getScale()/2))
						{
					radius = random.nextInt(200)+200;
					vec.x = random.nextInt(mapwidth - (2*offsetx)) + offsetx;
					vec.y = random.nextInt(mapheight - (2*offsety)) + offsety;
					i=-1;
				}
			}
			
			if(Math.sqrt(vec.distSQ(new Vector(c.getRealCenterX(),c.getRealCenterY()))) <= (c.getSprite().getImage().getWidth()/2) + radius + 200){				
				counter++;
				if(counter < 10) {
					e--;
					continue main;
				} else {
					Resources.log("no room for planet to be made?");
					break main;
				}
			}

			addPlanet((int)vec.x,(int)vec.y,maxhp,radius);
			Resources.log("PLANET  "+vec.x+ "   :   "+vec.y);
		}
		
		generateAsteroids();
	}
	
	private void generateAsteroids() throws SlickException{
		//Generates initial amount of asteroids
				String img = "asteroid";
				//variable for belt seperation
				int n = 10;
				int asteroid_spacing = 100;
				//variable for the amount of asteroids to spawn per tick
				int asteroid_amt = 3;
				for(int u=0; u<(mapwidth+1000)/asteroid_spacing; u++){
					for(int i=0; i<asteroid_amt; i++){
						//Downward moving asteroids (on the left)
						Asteroid a = new Asteroid((n*i)+random.nextInt(50),-1000+(u*asteroid_spacing),0,mapheight+1000);
						a.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
						this.getAsteroids().add(a);
						
						//Right moving asteroids (on the top)
						Asteroid b = new Asteroid(-1000+(u*asteroid_spacing),(n*i)+random.nextInt(50),mapwidth+1000,0);
						b.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
						this.getAsteroids().add(b);
						
						//Downward moving asteroids (on the right)
						Asteroid c = new Asteroid((n*i)+mapwidth+random.nextInt(50),-1000+(u*asteroid_spacing),mapwidth,mapheight+1000);
						c.load(img,Direction.DOWNWARD,random.nextFloat()*2f+2f,this);
						this.getAsteroids().add(c);
						
						//Rightward moving asteroids(on the bottom)
						Asteroid d = new Asteroid(-1000+(u*asteroid_spacing),(n*i)+mapheight+random.nextInt(50),mapwidth+1000,mapheight);
						d.load(img,Direction.FORWARD,random.nextFloat()*2f+2f,this);
						this.getAsteroids().add(d);
					}
				}
	}
	
	private void addPlanet(int centerx, int centery, int maxhp, int radius){
		Planet planet = new Planet(centerx, centery,  maxhp, radius);
		this.loadPlanet(planet);
		for(Craft c : getCrafts()) {
			if(c.isColliding(planet)) {
				System.out.println("Collision found");
			}
		}
		objlist.add(planet);
		planetlist.add(planet);
	}
	
	public void loadPlanets(){
		PlanetSprites.load();
		ArrayList<String> imagelist = PlanetSprites.sprites;
		for(Planet p : this.getPlanets()){
			int imagenum = random.nextInt(imagelist.size());
			p.load(imagelist.get(imagenum), p.getRadius(), this);
		}
	}
	
	private void loadPlanet(Planet p){
		PlanetSprites.load();
		ArrayList<String> imagelist = PlanetSprites.sprites;
		int imagenum = random.nextInt(imagelist.size());
		p.load(imagelist.get(imagenum), p.getRadius(), this);
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
