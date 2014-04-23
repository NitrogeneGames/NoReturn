package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.objecttree.ImageObject;
import nitrogene.objecttree.RectangleObject;

public class DroppedItem extends RectangleObject{
	private ArrayList<Item> droplist;

	public DroppedItem(ArenaMap map, ArrayList<Item> droplist, float x, float y) throws SlickException{
		super(50, 15, new Image("res/LaserV2ro.png"), 1, map);
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
