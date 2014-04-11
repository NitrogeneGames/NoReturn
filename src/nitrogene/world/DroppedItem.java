package nitrogene.world;

import org.newdawn.slick.Image;

import nitrogene.objecttree.ImageObject;

public class DroppedItem extends ImageObject{
	private EnumDrop[] drop;
	private int[] quantity;

	public DroppedItem(float width, float height, Image img, float scalefactor, ArenaMap map, EnumDrop[] drop, int[] quantity){
		super(width, height, img, scalefactor, map);
		this.drop = drop;
		this.quantity = quantity;
	}
	
	
	
	
}
