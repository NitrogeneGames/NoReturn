package nitrogene.gui;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import nitrogene.core.Craft;
import nitrogene.util.Button;
import nitrogene.util.EnumStatus;
import nitrogene.weapon.LaserLauncher;

public class Hotbar {
	public int tab = 0;
	public Craft ship;
	UnicodeFont uniFont;
	java.awt.Font mainFont;
	public Hotbar(Craft s) {
		try {
			mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, 17);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(Color.white);
        uniFont.getEffects().add(a);
		try {
			uniFont.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ship = s;
	}
	public void setTab(int i) {
		
	}
	public void loadWeapons(float camX, float camY) throws SlickException {
		for(int i = 0; i < ship.laserlist.size(); i++) {
			LaserLauncher launcher = ship.laserlist.get(i);
			Image rend = launcher.getImage().copy();
			Image statusicon;
			if(launcher.getStatus() == EnumStatus.READY){
				statusicon = new Image("res/gui/status_ready.png");
			} else if(launcher.getStatus() == EnumStatus.ENGAGED){
				statusicon = new Image("res/gui/status_firing.png");
			} else if(launcher.getStatus() == EnumStatus.DAMAGED){
				statusicon = new Image("res/gui/status_ready.png");
			} else if(launcher.getStatus() == EnumStatus.POWER){
				statusicon = new Image("res/gui/status_needpower.png");
			} else if(launcher.getStatus() == EnumStatus.DESTROYED){
				statusicon = new Image("res/gui/status_destroyed.png");
			} else{
				statusicon = new Image("res/gui/status_ready.png");
			}
			renderStatus(statusicon, getSlot(launcher.laserId), camX, camY);
			rend.setFilter(Image.FILTER_NEAREST);
			renderWeapon(rend, getSlot(launcher.laserId), launcher.name, camX, camY);
		}
	}
	public void renderWeapon(Image icon, int[] slot, String n, float camX, float camY) {
		icon.draw(slot[2] - icon.getWidth() + camX, slot[3] - icon.getHeight() + camY, 2F);
	        uniFont.drawString(slot[2] - uniFont.getWidth(n)/2 + camX, slot[3] - 40 + camY, n);

	}
	
	private void renderStatus(Image icon, int[] slot, float camX, float camY){
		icon.draw(slot[2] - icon.getWidth() + camX, slot[3] - icon.getHeight() + camY);
	}
	
	public int[] getSlot(int id) {
		return new int[] {333 + 117*id, 661, 333 + 117*id + 58, 661 + 51};
	}
}
