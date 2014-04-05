package nitrogene.core;

import java.util.ArrayList;

import nitrogene.weapon.EnumWeapon;

public class GlobalInformation {
		
	private static ArrayList<EnumWeapon> startingweapons;
	
	public GlobalInformation(){
		
	}
	
	public static void setStartingWeapons(ArrayList<EnumWeapon> start){
		startingweapons = start;
	}
	
	public static ArrayList<EnumWeapon> getStartingWeapons(){
		return startingweapons;
	}
}
