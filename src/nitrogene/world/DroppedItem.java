package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.objecttree.PhysicalObject;

public class DroppedItem extends PhysicalObject{
	private ArrayList<Item> droplist;

	public DroppedItem(ArenaMap map, ArrayList<Item> droplist, float x, float y) throws SlickException{
		super(50, 15);
		this.setCenterX(x);
		this.setCenterY(y);
		this.droplist = droplist;
	}
	
	public ArrayList<Item> getItemsInDrop(){
		return droplist;
	}
	
	public void destroy(ArenaMap map){
		this.mainimg = null;
		map.getDroppedItem().remove(this);
	}
}
