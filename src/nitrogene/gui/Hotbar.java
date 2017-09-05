package nitrogene.gui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import nitrogene.core.AssetManager;
import nitrogene.core.Craft;
import nitrogene.core.GlobalInformation;
import nitrogene.util.EnumStatus;
import nitrogene.weapon.EnumWeapon;
import nitrogene.weapon.LaserLauncher;

public class Hotbar {
	//slot width = 111, height = 104
	public int tab = 0;
	public Craft ship;
	private Image heart, bolt;
	private Image statusicon;
	private Image rend;
	
	
	public Hotbar(Craft s) throws SlickException {
		heart = ((Image) AssetManager.get().get("heart")).copy();
		bolt = ((Image) AssetManager.get().get("bolt")).copy();
		statusicon = ((Image) AssetManager.get().get("statusReady")).copy();
		rend = ((Image) AssetManager.get().get("laser1")).copy();
		ship = s;
	}
	public void setTab(int i) {
		
	}
	public void render(Graphics g, Craft craft, int selected) throws SlickException {
		for(LaserLauncher launcher : craft.laserlist) {
			if(!launcher.isDestroyed() && launcher.getTimer() != null) {
				renderHealthBar(g, launcher.laserId, launcher.getHp()/launcher.getMaxHp());
				renderPowerBar(g, launcher.laserId, launcher.getPowerReceived()/launcher.getPowerUsage());
				rend = launcher.getSprite().getImage().copy();
				/*if(launcher.getStatus() == EnumStatus.READY){
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
				}*/
				if(launcher.getStatus() == EnumStatus.READY){
					statusicon = ((Image) AssetManager.get().get("statusReady"));
				} else if(launcher.getStatus() == EnumStatus.ENGAGED){
					statusicon = ((Image) AssetManager.get().get("statusFiring"));
				} else if(launcher.getStatus() == EnumStatus.DAMAGED){
					statusicon = ((Image) AssetManager.get().get("statusDamaged"));
				} else if(launcher.getStatus() == EnumStatus.POWER){
					statusicon = ((Image) AssetManager.get().get("statusNeedPower"));
				} else if(launcher.getStatus() == EnumStatus.DESTROYED){
					statusicon = ((Image) AssetManager.get().get("statusDestroyed"));
				} else{
					statusicon = ((Image) AssetManager.get().get("statusReady"));
				}
				statusicon.setFilter(Image.FILTER_NEAREST);
				if(launcher.getTimer().getMaxChargeTime() != 0){
					renderChargeBar(g, launcher.laserId, launcher.getTimer().getCurrentChargeTime()/launcher.getTimer().getMaxChargeTime(),
							launcher.getTimer().bursting, ((float) launcher.getTimer().burstShot) * launcher.getTimer().interBurst * 10);
				}
				renderTransparentBackground(g, launcher.laserId, selected, launcher);
				renderStatus(statusicon, launcher.laserId);
				rend.setFilter(Image.FILTER_NEAREST);
				renderWeapon(rend, launcher.laserId, launcher.enumtype);
			}
		}
		
	}
	public void renderWeapon(Image icon, int id, EnumWeapon enumtype) {
		float yStart = 661 + 72;
		float yEnd = 661 + 104;
		float border = 3;
		
		
		float yRange = (104-72)-border*2;
		float yCenter = (yEnd + yStart)/2;
		
		float yHeight = icon.getHeight();
		float scale = yRange/yHeight;
		
		//icon.draw(slot[2] - (icon.getWidth()) + 56+ camX, yCenter, scale);
		
		icon.draw(333 + 117*id - (icon.getWidth()*scale/2) + 56, yCenter-icon.getHeight()*scale/2, scale);
		
		
		//Weapon name and resizing
		enumtype.font.drawString(333 + 117*id - enumtype.font.getWidth(enumtype.shortenedString)/2 + 56, 661 + 30 - enumtype.font.getHeight(enumtype.shortenedString)/2, enumtype.shortenedString);
		/*if(uniFont.getWidth(n) < 104) {
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
			if(n.charAt(n.length()-4) == ' ') {
				n = n.substring(0, n.length()-4) + "...";
			}
			uniFont3.drawString(slot[2] - uniFont3.getWidth(n)/2 + camX + 56, slot[3] + 30 + camY - uniFont3.getHeight(n)/2, n);
		}*/
	}
	
	private void renderStatus(Image icon, int id){
		icon.draw(333 + 117*id + 82, 661 + 2);
	}
	
	private void renderChargeBar(Graphics g, int id, float progress, boolean bursting, float charge){
		g.setColor(Color.white);
		g.drawRect(333 + 117*id + 4, 661 + 6, 74, 18);
		if(bursting && charge >= 500) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.blue);
		}
		g.fillRect(333 + 117*id + 5, 661 + 7, (int)(progress*73), 17);
	}
	
	private void renderHealthBar(Graphics g, int id, float progress){
		heart.draw(333 + 117*id + 3, 661 + 45);
		
		g.setColor(Color.white);
		g.drawRect(333 + 117*id + 19, 661 + 45, 89, 11);
		g.setColor(Color.red);
		g.fillRect(333 + 117*id + 20, 661 + 46, (int)(progress*88), 10);
	}
	
	private void renderPowerBar(Graphics g, int id, float progress){
		bolt.draw(333 + 117*id + 7, 661 + 62);
		
		g.setColor(Color.white);
		g.drawRect(333 + 117*id + 19, 661 + 61, 89, 11);
		g.setColor(Color.yellow);
		g.fillRect(333 + 117*id + 20, 661 + 62, (int)(progress*88), 10);
	}
	
	private void renderTransparentBackground(Graphics g, int id,  int selected, LaserLauncher launcher){
		if(launcher.laserId == selected){
			g.setColor(new Color(0f, 204f, 0f, 0.3f));
		} else{
			g.setColor(new Color(0f, 0f, 0f, 0.3f));
		}
		g.fillRect(333 + 117*id, 661, 111, 104);
	}
	
		//int[] {333 + 117*id, 661, 333 + 117*id, 661};
}
