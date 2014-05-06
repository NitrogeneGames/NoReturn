package nitrogene.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import nitrogene.util.EnumHull;
import nitrogene.weapon.EnumWeapon;

public class CraftData {
	public ArrayList<EnumWeapon> weapons;
	public String name;
	public EnumHull hull;
	public CraftData(ArrayList<EnumWeapon> a, String n, EnumHull h) {
		weapons = a;
		name = n;
		hull = h;
	}
	public Image renderShip() {
		int[][] slots = new int[][]{new int[]{250,30}, new int[]{174,30},new int[]{105,17},new int[]{250,136},new int[]{174,136},new int[]{105,136}};
		Image a = hull.getImage();
		Graphics g;
		try {
			g = a.getGraphics();
			for(int i = 0; i<weapons.size(); i++) {
				int x = slots[i][0];
				int y = slots[i][1];				
				g.drawImage(new Image(weapons.get(i).image), x, y);	
				g.flush();
			}
		} catch (SlickException e) {
			
		} 
		return a;
	}
	
}
