package nitrogene.world;

public enum EnumDrop {

	IRON (1, 100),
	TITANIUM(2, 100),
	AMMO(1, 15);
	
	//Tier used to classify drop rate
	public int tier;
	public int maxstack;
	//Possible classes are: Crafting resources, weapons, fuel, ammo, provisions
	EnumDrop(int tier, int maxstack){
		this.tier = tier;
		this.maxstack = maxstack;
	}

}
