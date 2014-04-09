package nitrogene.core;

import java.util.ArrayList;

import nitrogene.objecttree.CircleObject;
import nitrogene.util.Shake;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Planet extends CircleObject{
	private int maxhp;
	private int hp;
	private Image im;
	private Shake shake;

	public Planet(float centerx, float centery, Image theimage, int maxhp, int scalefactor, ArenaMap map) {
		super(centerx, centery, (theimage.getWidth()/2)*scalefactor, theimage, scalefactor, map);
		this.maxhp = maxhp;
		hp = maxhp;
		im = theimage;
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
		im = null;
		map.removePlanet(e);
	}
	public void destroy(ArenaMap map, Planet planet) throws SlickException {
		im = null;
		map.removePlanet(planet);
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
