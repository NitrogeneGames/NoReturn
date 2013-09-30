package nitrogene.npc;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.core.Planet;
import nitrogene.util.Direction;

public class NPCship extends Craft{
	private Relation relation;
	private ArrayList<Craft> crafttarget;
	private ArrayList<Planet> planettarget;
	
	public NPCship(float xpos, float ypos, int upbound, int downbound, int leftbound, int rightbound, Relation relation) throws SlickException{
		super(xpos,ypos, upbound, downbound, leftbound, rightbound);
		this.relation = relation;
		crafttarget = new ArrayList<Craft>();
		planettarget = new ArrayList<Planet>();
	}
	
	@Override
	public void update(int delta, float camX, float camY){
		this.delta = delta;
		cumulative += delta;
		
		//Targetting
		if(crafttarget.get(0)!=null)
			this.laserlist.get(0).setTarget(crafttarget.get(0).getX()+crafttarget.get(0).core.getX(), crafttarget.get(0).getY()+crafttarget.get(0).core.getY());
		
		//Clock
		if(cumulative >= 1000){
			//1 second cumulative
			lifesupport.tick();
			cumulative -= 1000;
		}
		//movement.Accelerate(boundbox.center,delta);
		
	}
	
	public void addCraftTarget(Craft craft){
		crafttarget.add(craft);
	}
	public void addPlanetTarget(Planet planet){
		planettarget.add(planet);
	}
}
