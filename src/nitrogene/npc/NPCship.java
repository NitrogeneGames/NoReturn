package nitrogene.npc;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.world.ArenaMap;
import nitrogene.world.Planet;

public class NPCship extends Craft{
	private Relation relation;
	private ArrayList<Craft> crafttarget;
	private ArrayList<Planet> planettarget;
	public ArrayList<Task> tasks = new ArrayList<Task>();
	
	public NPCship(float xpos, float ypos, Relation relation) throws SlickException{
		super(xpos, ypos);
		this.relation = relation;
		crafttarget = new ArrayList<Craft>();
		planettarget = new ArrayList<Planet>();
	}
	public void addTask(Task t) {
		tasks.add(t);
	}
	public void addTaskOverride(Task t) {
		for (Task t2: tasks){ 
		if(t.getTaskID() == t2.getTaskID()){
			tasks.remove(t2);
		}
		}
		addTask(t);
	}

	public void update(int delta, float camX, float camY){
		super.update(delta, camX, camY);
		this.delta = delta;
		cumulative += delta;
		
		//Targetting
			//this.laserlist.get(0).setTarget(crafttarget.get(0).getCenterX(), crafttarget.get(0).getCenterY());
		
		//movement.Accelerate(new Vector(boundbox.getCenterX(),boundbox.getCenterY()), delta);
		//move(20);
		if(!destroyed) {
			runTasks(delta, camX, camY);
		}
		
	}
	
	public void addCraftTarget(Craft craft){
		crafttarget.add(craft);
	}
	public void addPlanetTarget(Planet planet){
		planettarget.add(planet);
	}
	public void runTasks(int delta, float camX, float camY) {
		for (int i = 0; i < tasks.size(); i++){
			if(!tasks.get(i).isComplete) {
				tasks.get(i).run(delta, camY, camY);
			}
		}
	}
	public void removeTask(Task t2) {
		for (int i = 0; i < tasks.size(); i++){
			if(t2.equals(tasks.get(i))){
				tasks.remove(i);
			}
		}
	}
	public void moveToPoint(float camX, float camY)
	{
		this.tasks.add(new TaskMoveTo(this, camX, camY, 0));
	}
}
