package nitrogene.gui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.util.EnumStatus;
import nitrogene.weapon.LaserLauncher;

public class Hotbar {
	//slot width = 111, height = 104
	public int tab = 0;
	public Craft ship;
	UnicodeFont uniFont;
	java.awt.Font mainFont;
	UnicodeFont uniFont2;
	java.awt.Font mainFont2;
	UnicodeFont uniFont3;
	java.awt.Font mainFont3;
	private Image heart, bolt;
	
	public Hotbar(Craft s) throws SlickException {
		heart = (Image) AssetManager.get().get("heart");
		bolt = (Image) AssetManager.get().get("bolt");
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
		ship = s;
	}
	public void setTab(int i) {
		
	}
	public void loadWeapons(Graphics g, Craft craft, float camX, float camY, int selected) throws SlickException {
		for(int i = 0; i < craft.laserlist.size(); i++) {
			LaserLauncher launcher = craft.laserlist.get(i);
			
			Image rend = launcher.getImage().copy();
			Image statusicon;
			if(launcher.getStatus() == EnumStatus.READY){
				statusicon = ((Image) AssetManager.get().get("statusReady")).copy();
			} else if(launcher.getStatus() == EnumStatus.ENGAGED){
				statusicon = ((Image) AssetManager.get().get("statusFiring")).copy();
			} else if(launcher.getStatus() == EnumStatus.DAMAGED){
				statusicon = ((Image) AssetManager.get().get("statusDamaged")).copy();
			} else if(launcher.getStatus() == EnumStatus.POWER){
				statusicon = ((Image) AssetManager.get().get("statusNeedPower")).copy();
			} else if(launcher.getStatus() == EnumStatus.DESTROYED){
				statusicon = ((Image) AssetManager.get().get("statusDestroyed")).copy();
			} else{
				statusicon = ((Image) AssetManager.get().get("statusReady")).copy();
			}
			statusicon.setFilter(Image.FILTER_NEAREST);
			
			renderTransparentBackground(g, getSlot(launcher.laserId), camX, camY, selected, launcher);
			renderStatus(statusicon, getSlot(launcher.laserId), camX, camY);
			if(launcher.getTimer().getMaxChargeTime() != 0 && launcher.getTimer().active){
				renderChargeBar(g, getSlot(launcher.laserId), launcher.getTimer().getCurrentChargeTime()/launcher.getTimer().getMaxChargeTime(),
						camX, camY, launcher.getTimer().bursting, ((float) launcher.getTimer().burstShot) * launcher.getTimer().interBurst * 10);
			}
			renderHealthBar(g, getSlot(launcher.laserId), launcher.getHp()/launcher.getMaxHp(), camX, camY);
			renderPowerBar(g, getSlot(launcher.laserId), launcher.getPowerReceived()/launcher.getPowerUsage(), camX, camY);
			rend.setFilter(Image.FILTER_NEAREST);
			renderWeapon(rend, getSlot(launcher.laserId), launcher.name, camX, camY);
		}
	}
	public void renderWeapon(Image icon, int[] slot, String n, float camX, float camY) {
		icon.draw(slot[2] - (icon.getWidth()) + 56+ camX, slot[3] + 91- (icon.getHeight()) + camY, 2F);
		if(uniFont.getWidth(n) < 104) {
			uniFont.drawString(slot[2] - uniFont.getWidth(n)/2 + camX + 56, slot[3] + 30 + camY - uniFont.getHeight(n)/2, n);
		} else if(uniFont2.getWidth(n) < 104) {
			uniFont2.drawString(slot[2] - uniFont2.getWidth(n)/2 + camX + 56, slot[3] + 30 + camY - uniFont2.getHeight(n)/2, n);
		} else if(uniFont3.getWidth(n) < 104) {
			uniFont3.drawString(slot[2] - uniFont3.getWidth(n)/2 + camX + 56, slot[3] + 30 + camY - uniFont3.getHeight(n)/2, n);
		} else {
			while(uniFont3.getWidth(n) > 104) {
				n = n.substring(0, n.length() - 4);			
				n = n + "...";
			}
			System.out.println(n.substring(n.length()-4,n.length()-3) + "=");
			if(n.charAt(n.length()-4) == ' ') {
				n = n.substring(0, n.length()-4) + "...";
			}
			uniFont3.drawString(slot[2] - uniFont3.getWidth(n)/2 + camX + 56, slot[3] + 30 + camY - uniFont3.getHeight(n)/2, n);
		}
	}
	
	private void renderStatus(Image icon, int[] slot, float camX, float camY){
		icon.draw(slot[2] + camX + 82, slot[3] + camY + 2);
	}
	
	private void renderChargeBar(Graphics g, int[] slot, float progress, float camX, float camY, boolean bursting, float charge){
		g.setColor(Color.white);
		g.drawRect(slot[2] + camX + 4, slot[3] + camY + 6, 74, 18);
		if(bursting && charge >= 500) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.blue);
		}
		g.fillRect(slot[2] + camX + 5, slot[3] + camY + 7, (int)(progress*73), 17);
	}
	
	private void renderHealthBar(Graphics g, int[] slot, float progress, float camX, float camY){
		heart.draw(slot[2] + camX + 3, slot[3] + camY + 45);
		
		g.setColor(Color.white);
		g.drawRect(slot[2] + camX + 19, slot[3] + camY + 45, 89, 11);
		g.setColor(Color.red);
		g.fillRect(slot[2] + camX + 20, slot[3] + camY + 46, (int)(progress*88), 10);
	}
	
	private void renderPowerBar(Graphics g, int[] slot, float progress, float camX, float camY){
		bolt.draw(slot[2] + camX + 7, slot[3] + camY + 62);
		
		g.setColor(Color.white);
		g.drawRect(slot[2] + camX + 19, slot[3] + camY + 61, 89, 11);
		g.setColor(Color.yellow);
		g.fillRect(slot[2] + camX + 20, slot[3] + camY + 62, (int)(progress*88), 10);
	}
	
	private void renderTransparentBackground(Graphics g, int[] slot, float camX, float camY, int selected, LaserLauncher launcher){
		if(launcher.laserId == selected){
			g.setColor(new Color(0f, 204f, 0f, 0.3f));
		} else{
			g.setColor(new Color(0f, 0f, 0f, 0.3f));
		}
		g.fillRect(slot[2]+camX, slot[3]+camY, 111, 104);
	}
	
	public int[] getSlot(int id) {
		return new int[] {333 + 117*id, 661, 333 + 117*id, 661};
	}
}
