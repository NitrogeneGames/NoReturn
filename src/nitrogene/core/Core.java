package nitrogene.core;

public class Core extends ShipSystem{
	private int maxpower;
	private int maxhp;

	public Core(int maxhp, int durability, int maxpower, int capacity) {
		super(maxhp, durability, maxpower, capacity);
		this.maxpower = maxpower;
	}
	
	//Core under 30% health stars to 
	public int getPower(){
		double zz = getHp()/maxhp;
		if(zz<0.3) return (int) (zz*3.33*maxpower);
		else return maxpower;
	}
	
	public void damage(int damage){
		setHp(getHp()-damage);
		if(getHp() > maxhp) setHp(maxhp);
	}
	
	public void repair(int repair){
		setHp(getHp()+repair);
		if(getHp() < 0) setHp(0);
	}
	
}
