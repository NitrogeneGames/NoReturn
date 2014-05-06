package nitrogene.core;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import nitrogene.weapon.EnumWeapon;

public class GlobalInformation {
	
	private static ArrayList<CraftData> craftdata = new ArrayList<CraftData>();
	public static int selected;
	public static boolean shipsLoaded = true;
	public GlobalInformation(){
		
	}
	
	public org.newdawn.slick.UnicodeFont getFont(Float size) throws SlickException{
		try {
			java.awt.Font mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
			mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, size);
			org.newdawn.slick.UnicodeFont uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
			uniFont.addAsciiGlyphs();
	        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
	        a.setColor(Color.white);
	        uniFont.getEffects().add(a);
	        uniFont.loadGlyphs();
	        return uniFont;
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
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
