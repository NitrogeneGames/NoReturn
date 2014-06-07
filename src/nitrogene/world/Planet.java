package nitrogene.world;

import java.util.ArrayList;

import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Shake;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Planet extends PhysicalObject{
	private int maxhp;
	private int hp;
	private Shake shake;

	public Planet(float centerx, float centery, Image theimage, int maxhp, int scalefactor, ArenaMap map) {
		super(centerx-(theimage.getWidth()/2), centery-(theimage.getWidth()/2), theimage, scalefactor, map);
		this.getImage().setCenterOfRotation(centerx, centery);
		this.maxhp = maxhp;
		hp = maxhp;
		shake = new Shake();
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
		DroppedItem drop = new DroppedItem(map, list, this.getCenterX(), this.getCenterY());
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

}
