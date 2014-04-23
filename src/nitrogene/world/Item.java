package nitrogene.world;

public class Item {
	private int quantity;
	private EnumDrop type;

	public Item(EnumDrop type){
		this.type = type;
		this.quantity = 0;
	}
	
	//returning 1 means successful, returning 0 means unsuccessful
	public int addStack(int amt){
		if(quantity+amt <= type.maxstack){
			quantity+=amt;
			return 1;
		} else{
			return 0;
		}
	}
	
	//same rules as above function
	public int subtrackStack(int amt){
		if(quantity-amt >= 0){
			quantity-=amt;
			return 1;
		} else{
			return 0;
		}
	}
	
}
