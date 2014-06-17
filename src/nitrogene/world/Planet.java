package nitrogene.world;

import java.util.ArrayList;

import nitrogene.core.GlobalInformation;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AngledMovement;
import nitrogene.util.Movement;
import nitrogene.util.Shake;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class Planet extends PhysicalObject{
	private int maxhp;
	private float realcenterx, realcentery;
	private int radius;
	private int hp;
	private Shake shake;

	public Planet(float centerx, float centery, int maxhp, int radius) {
		super(centerx-radius, centery-radius);
		this.realcenterx = centerx;
		this.realcentery = centery;
		this.maxhp = maxhp;
		this.radius = radius;
		hp = maxhp;
		shake = new Shake();
	}
	
	public void load(Image img, float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = img;
		boundbox = GlobalInformation.getImageData().get(img.getResourceReference());
		if(boundbox == null){
			System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(img.getWidth(), img.getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
		
		this.getImage().setCenterOfRotation(realcenterx, realcentery);
	}
	
	public void damage(int damage, ArenaMap map, int e) throws SlickException{
		hp -= damage;
		if(hp <= 0){
			hp = 0;
			//explode();
			this.destroy(map, e);
		}
	}
	public void damage(int damage, ArenaMap map) throws SlickException{
		hp -= damage;
		if(hp <= 0){
			hp = 0;
			//explode(); 
			this.destroy(map, this);
		}
	}
	public void destroy(ArenaMap map, int e) throws SlickException {
		this.mainimg = null;
		spawnItem(map);
		map.removePlanet(e);
		map.getObjList().remove(e);
	}
	public void destroy(ArenaMap map, Planet planet) throws SlickException {
		this.mainimg = null;
		spawnItem(map);
		map.getObjList().remove(planet);
		map.removePlanet(planet);
	}
	private void spawnItem(ArenaMap map) throws SlickException{
		ArrayList<Item> list = new ArrayList<Item>();
		Item item = new Item(EnumDrop.IRON, list);
		item.addStack(10);
		list.add(item);
		DroppedItem drop = new DroppedItem(map, list, this.getX(), this.getY());
		map.addDroppedItem(drop);
	}
	public int getHp(){
		return hp;
	}
	
	public int getMaxHp(){
		return maxhp;
	}
	
	public Shake getShake(){
		return shake;
	}

	public int getRadius() {
		return radius;
	}

}
