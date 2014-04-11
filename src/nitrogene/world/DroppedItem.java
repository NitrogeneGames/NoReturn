package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.objecttree.ImageObject;

public class DroppedItem extends ImageObject{
	private ArrayList<Item> droplist;

	public DroppedItem(ArenaMap map, ArrayList<Item> droplist) throws SlickException{
		super(50, 15, new Image("res/LaserV2ro.png"), 1, map);
		this.droplist = droplist;
	}
	
	public ArrayList<Item> getItemsInDrop(){
		return droplist;
	}
}
