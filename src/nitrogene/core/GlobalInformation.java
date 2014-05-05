package nitrogene.core;

import java.util.ArrayList;

import nitrogene.weapon.EnumWeapon;

public class GlobalInformation {
		
	private static ArrayList<CraftData> craftdata = new ArrayList<CraftData>();
	public static int selected;
	public GlobalInformation(){
		
	}
	
	public static void setCraftDataOverride(ArrayList<CraftData> start){
		craftdata = start;
	}
	public static void addCraftData(CraftData craftData){
		craftdata.add(craftData);
	}
	public static ArrayList<CraftData> getCraftData(){
		return craftdata;
	}
	
	private static ArrayList<EnumWeapon> startingweapons;
	
	public static void setStartingWeapons(ArrayList<EnumWeapon> start){
		startingweapons = start;
	}
	
	public static ArrayList<EnumWeapon> getStartingWeapons(){
		return startingweapons;
	}
}
