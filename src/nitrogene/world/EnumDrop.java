package nitrogene.world;

public enum EnumDrop {

	IRON (1, 100),
	TITANIUM(2, 100);
	
	//Tier used to classify drop rate
	public int tier;
	public int maxstack;
	EnumDrop(int tier, int maxstack){
		this.tier = tier;
		this.maxstack = maxstack;
	}
	
}
