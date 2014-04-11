package nitrogene.world;

public enum EnumDrop {

	IRON (1);
	
	//Tier used to classify drop rate
	public int tier;
	EnumDrop(int tier){
		this.tier = tier;
	}
	
}
