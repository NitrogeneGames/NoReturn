package nitrogene.gui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

import nitrogene.core.Craft;
import nitrogene.util.Button;
import nitrogene.util.EnumStatus;
import nitrogene.weapon.LaserLauncher;

public class Hotbar {
	public int tab = 0;
	public Craft ship;
	UnicodeFont uniFont;
	java.awt.Font mainFont;
	private Image heart, bolt;
	
	public Hotbar(Craft s) throws SlickException {
		heart = new Image("res/gui/heart.png");
		bolt = new Image("res/gui/lightningbolt.png");
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
	public void loadWeapons(Graphics g, Craft craft, float camX, float camY, int selected) throws SlickException {
		for(int i = 0; i < craft.laserlist.size(); i++) {
			LaserLauncher launcher = craft.laserlist.get(i);
			
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
			statusicon.setFilter(Image.FILTER_NEAREST);
			
			renderTransparentBackground(g, getSlot(launcher.laserId), camX, camY, selected, launcher);
			renderStatus(statusicon, getSlot(launcher.laserId), camX, camY);
			if(launcher.getTimer().getMaxChargeTime() != 0 && launcher.getTimer().getClock().isRunning()){
				renderChargeBar(g, getSlot(launcher.laserId), launcher.getTimer().getCurrentChargeTime()/launcher.getTimer().getMaxChargeTime(),
						camX, camY);
			}
			renderHealthBar(g, getSlot(launcher.laserId), launcher.getHp()/launcher.getMaxHp(), camX, camY);
			renderPowerBar(g, getSlot(launcher.laserId), launcher.getPowerStorage()/launcher.getCapacity(), camX, camY);
			rend.setFilter(Image.FILTER_NEAREST);
			renderWeapon(rend, getSlot(launcher.laserId), launcher.name, camX, camY);
		}
	}
	public void renderWeapon(Image icon, int[] slot, String n, float camX, float camY) {
		icon.draw(slot[2] - (icon.getWidth()) + 56+ camX, slot[3] + 78 + camY, 2F);
	    uniFont.drawString(slot[2] - uniFont.getWidth(n)/2 + camX + 56, slot[3] + 26 + camY, n);
	}
	
	private void renderStatus(Image icon, int[] slot, float camX, float camY){
		icon.draw(slot[2] + camX + 82, slot[3] + camY + 2);
	}
	
	private void renderChargeBar(Graphics g, int[] slot, float progress, float camX, float camY){
		g.setColor(Color.white);
		g.drawRect(slot[2] + camX + 4, slot[3] + camY + 6, 74, 18);
		g.setColor(Color.blue);
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
