package nitrogene.world;

import java.util.ArrayList;

import nitrogene.core.GlobalInformation;
import nitrogene.gui.Sprite;
import nitrogene.inventory.DroppedItem;
import nitrogene.inventory.EnumDrop;
import nitrogene.inventory.Item;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AngledMovement;
import nitrogene.util.Movement;
import nitrogene.util.Shake;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

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
	
	public void load(String img, float radius, ArenaMap map){
		this.map = map;
		this.mainimg = new Sprite(img);		
		this.scalefactor = (radius*2)/mainimg.getImage().getWidth();
		boundbox = GlobalInformation.getHitbox(mainimg.getResourceReference());
		if(boundbox == null){
			//Resources.log(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		boundbox = boundbox.transform(Transform.createScaleTransform(scalefactor, scalefactor));
		init(mainimg.getImage().getWidth(), mainimg.getImage().getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		movement = new Movement();
		
		this.getSprite().setCenterOfRotation(realcenterx, realcentery);
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
		this.removeBoundbox();
		this.destroyed = true;
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
		DroppedItem drop = new DroppedItem(list, this.getX(), this.getY());
		drop.load(4f, map);
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
