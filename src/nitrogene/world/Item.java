package nitrogene.world;

import java.util.ArrayList;

public class Item {
	private int quantity;
	private boolean isFilled;
	private EnumDrop type;
	private ArrayList<Item> parent;

	public Item(EnumDrop type, ArrayList<Item> parent){
		this.parent = parent;
		this.type = type;
		this.quantity = 0;
	}
	
	//returning 1 means successful, returning 0 means unsuccessful
	public void addStack(int amt){
		if(!isFilled){
			if(quantity+amt <= type.maxstack){
				quantity+=amt;
			} else{
				Item ii = new Item(type,parent);
				int space = type.maxstack - quantity;
				quantity+=space;
				ii.addStack(amt-space);
				parent.add(ii);
				isFilled = true;
			}
		}
	}
	
	//same rules as above function
	public void subtrackStack(int amt){
		if(quantity-amt >= 0){
			quantity-=amt;
			isFilled = false;
		} else{
			
		}
	}
	
	public void changeParent(ArrayList<Item> parent){
		this.parent = parent;
	}
}
