package nitrogene.core;

import java.util.ArrayList;

import nitrogene.weapon.EnumWeapon;

public class CraftData {
	public ArrayList<EnumWeapon> weapons;
	public String name;
	public CraftData(ArrayList<EnumWeapon> a, String n) {
		weapons = a;
		name = n;
	}
	
}
