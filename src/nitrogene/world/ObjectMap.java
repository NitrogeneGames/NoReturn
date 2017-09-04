package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import nitrogene.core.GameState;
import nitrogene.core.Resources;
import nitrogene.objecttree.PhysicalObject;

public class ObjectMap implements TileBasedMap {
	public Circle marble;
	private int chunkWidth;
	private PhysicalObject traveler;
	private float radius;
	ArrayList<PhysicalObject> ignore;
	public ObjectMap(PhysicalObject o, int chunkWidth, ArrayList<PhysicalObject> ignore) {
		this.ignore = ignore;
		if(!this.ignore.contains(o)) this.ignore.add(o);
		traveler = o;
		float width = (float) (o.getSprite().getImage().getWidth()/2);
		float height = (float) (o.getSprite().getImage().getHeight()/2);
		float radius = width;
		if(width>height) radius = height;
		radius*=1.3;
		marble = new Circle(0, 0, (float) (chunkWidth/2 + radius/Math.sqrt(2)));
		this.chunkWidth = chunkWidth;
	}
	@Override
	public boolean blocked(PathFindingContext arg0, int x, int y) {
		
		marble.setLocation(chunkWidth*x-marble.radius, chunkWidth*y-marble.radius);
		for(PhysicalObject o : GameState.map.getObjList()) {
			
			if(o.getClass() != Asteroid.class && o.isColliding(marble) && !this.ignore.contains(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public int getHeightInTiles() {
		return GameState.map.getMapHeight()/chunkWidth;
	}

	@Override
	public int getWidthInTiles() {
		return GameState.map.getMapWidth()/chunkWidth;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		
	}

}
