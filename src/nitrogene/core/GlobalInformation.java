package nitrogene.core;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import nitrogene.gui.Sprite;
import nitrogene.weapon.EnumWeapon;

public class GlobalInformation {
	
	private static ArrayList<CraftData> craftdata = new ArrayList<CraftData>();
	public static int selected;
	public static float musiclevel = 50/100;
	public static float sfxlevel = 100/100;
	public static float alarmlevel = 50/100;
	public static int SCRwidth,SCRheight;
	public static double percentloaded;
	public static boolean testMode = false;
	private static HashMap<String, Shape> imagedata = new HashMap<String, Shape>();
	public static boolean shipsLoaded = true;
	
	public GlobalInformation(){
		
	}
	
	public static org.newdawn.slick.UnicodeFont getMenuFont(Float size) throws SlickException{
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
	
	public static org.newdawn.slick.UnicodeFont getPixelFont(Float size) throws SlickException{
		try {
			java.awt.Font mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/visitor1.ttf"));
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


	public static boolean adjacent(float x1, float y1, float x2, float y2) {
		float x = Math.abs(x2 - x1);
		float y = Math.abs(y2 - y1);
		if(x <= 1 && y <=1) {
			return true;
		}
		return false;
	}
	public static ArrayList<float[]> ptsTest = new ArrayList<float[]>();
	public static void initHitboxData(){
		float[] craftData = {6,52,37,52,53,36,99,10,131,0,245,0,245,5,312,5,312,31,
				317,36,328,41,332,46,343,57,343,62,328,67,322,72,94,72,94,93,322,93,
				328,98,343,103,343,109,338,114,328,124,317,124,317,145,307,150,291,150,
				291,161,317,161,317,155,333,145,343,140,348,140,348,155,343,161,317,176,
				276,176,260,161,245,161,245,166,131,166,110,161,98,154,94,150,84,145,63,135,
				53,129,48,119,37,114,6,114,6,109,11,109,11,57,5,57};
		float[] craftData2 = {0,0,33,0,146,15,186,35,200,53,207,78,207,112,200,137,186,155,146,175,
				33,190,0,190,10,150,0,143,0,113,19,100,19,90,0,77,0,47,10,40};
		float[] slaserData = {0,7,1,6,6,4,15,2,21,2,30,0,45,0,47,1,49,3,50,5,50,10,49,12,47,14,
				45,15,30,15,21,14,15,13,6,11,1,9,0,8};
		float[] asteroid = {0, 5, 4, 0, 10, 0, 19, 3, 21, 4, 26, 8, 26, 11, 24, 19, 20,
				22, 13, 22, 5, 19, 0, 11};
		imagedata.put("res/klaarship6.png", new Polygon(craftData));
		//imagedata.put("res/klaarship6.png", Resources.generateBoundboxJared("craftimage"));
		imagedata.put("res/humanship4.png", new Polygon(craftData2));
		//imagedata.put("res/humanship4.png", Resources.generateBoundboxJared("humanship"));
		//ptsTest = getPoints("humanship");
		imagedata.put("res/volcanicplanet2.png", new Circle(64,64,64));
		imagedata.put("res/rockyplanet.png", new Circle(64,64,64));
		imagedata.put("res/sun_1.png", new Circle(50,50,50));
		imagedata.put("res/LaserV2ro.png", new Polygon(slaserData));
		imagedata.put("res/icon/shieldsystem.png", new Circle(20,20,20));
		imagedata.put("res/icon/enginesystem.png", new Circle(20,20,20));
		imagedata.put("res/icon/coresystem.png", new Circle(20,20,20));
		imagedata.put("res/icon/oxygensystem.png", new Circle(20,20,20));
		imagedata.put("res/asteroid1.png", new Polygon(asteroid));
	}
	public static UnicodeFont uniFont;
	public static java.awt.Font mainFont;
	public static UnicodeFont uniFont2;
	public static java.awt.Font mainFont2;
	public static UnicodeFont uniFont3;
	public static java.awt.Font mainFont3;
	public static HashMap<String, int[]> offsets = new HashMap<String, int[]>();
	public static void setLaserOffset(String s, int x, int y) {
		if(!offsets.containsKey(s)) {
			offsets.put(s, new int[] {x,y});
		} else {
			Resources.log("offset duplicate recording in globalinformation");
		}
	}
	public static int getLaserOffsetX(String s) {
		if(offsets.containsKey(s)) {
			return (int)offsets.get(s)[0];
		}
		return 0;
	}
	public static int getLaserOffsetY(String s) {
		if(offsets.containsKey(s)) {
			return (int)offsets.get(s)[1];
		}
		return 0;
	}
	public static void loadFonts() {
		try {
			mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, 17);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(java.awt.Color.white);
        uniFont.getEffects().add(a);
        
        mainFont2 = mainFont.deriveFont(java.awt.Font.PLAIN, 15);
        uniFont2 = new org.newdawn.slick.UnicodeFont(mainFont2);
        uniFont2.addAsciiGlyphs();
        uniFont2.getEffects().add(a);
        
        mainFont3 = mainFont.deriveFont(java.awt.Font.PLAIN, 13);
        uniFont3 = new org.newdawn.slick.UnicodeFont(mainFont3);
        uniFont3.addAsciiGlyphs();
        uniFont3.getEffects().add(a);
		try {
			uniFont.loadGlyphs();
			uniFont2.loadGlyphs();
			uniFont3.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EnumWeapon[] weapVals = EnumWeapon.class.getEnumConstants();
		for(EnumWeapon e : weapVals) {
			e.loadFont();
		}
	}
	
	
	
	public static void init(int SCR_width, int SCR_height){
		SCRwidth=SCR_width;
		SCRheight=SCR_height;
	}
	
	/*public static HashMap<String, Shape> getImageData(){
		return (HashMap<String, Shape>) imagedata.clone();
	}*/
	public static Shape getHitbox(String s) {
		if(imagedata.containsKey(s)) {
			if(imagedata.get(s).getClass() == org.newdawn.slick.geom.Polygon.class) {
				return new Polygon(imagedata.get(s).getPoints());
			} else {
				return imagedata.get(s);
			}
		} else {
			return null;
		}
	}
	public static void addPercentLoaded(double amt){
		percentloaded += amt;
	}
}
