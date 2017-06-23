package nitrogene.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import nitrogene.core.Craft;

public class Inventory {
	private HashMap<Integer, Item> itemlist = new HashMap<Integer, Item>();
	//15 slots, first 5 are for weapons only, other 10 can be anything
	private int slotCount = 15;
	public int getSlotCount() {
		return slotCount;
	}
	public void setSlotCount(int s) {
		slotCount = s;
	}
	public boolean hasItemInSlot(int slot) {
		if(!itemlist.containsKey(slot)) {
			return false;
		}
		return true;
	}
 	public Item getItemInSlot(int slot) {
		return itemlist.get(slot);
	}
 	public void clearSlot(int slot) {
 		itemlist.remove(slot);
 	}
}
