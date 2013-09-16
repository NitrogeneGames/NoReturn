package nitrogene.world;

import java.util.ArrayList;
import java.util.Random;

import nitrogene.core.Planet;

public class arenamap {
	private int planetnumber;
	private ArrayList<Planet> planetlist;
	Random random;
	
	public arenamap(int planetnumber){
		this.planetnumber = planetnumber;
		planetlist = new ArrayList<Planet>();
		generate();
	}
	
	private void generate(){
		
	}
	
	private void addPlanet(){
		//random.
	}
	
	public ArrayList<Planet> getPlanets(){
		return planetlist;
	}
}
