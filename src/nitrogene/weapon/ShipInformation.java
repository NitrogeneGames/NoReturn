package nitrogene.weapon;

import java.util.ArrayList;

public class ShipInformation {
	
	ArrayList<LaserLauncher> weapons;
	
	public ShipInformation(){
		weapons = new ArrayList<LaserLauncher>();
	}
	
	public void initweapons(LaserLauncher l1, LaserLauncher l2, LaserLauncher l3, LaserLauncher l4, LaserLauncher l5, LaserLauncher l6){
		if(!(l1 == null)){
			weapons.add(0, l1);
		} else if (l2 != null){
			weapons.add(1, l2);
		} else if (l3 != null){
			weapons.add(2, l3);
		}else if (l4 != null){
			weapons.add(3, l4);
		}else if (l5 != null){
			weapons.add(4, l5);
		}else if (l6 != null){
			weapons.add(5, l6);
		}
	}
	
	public void augmentWeapon(int index, LaserLauncher l){
		index =- 1; //Compensate for computers starting at 0 while people count from 1
		if(weapons.get(index) != null){
			weapons.remove(index);
		}
		weapons.add(index, l);
	}
	
	public ArrayList<LaserLauncher> getWeapons(){
		return weapons;
	}
}
