package nitrogene.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import nitrogene.core.Craft;
import nitrogene.world.Planet;

public class Minimap {
	private ArrayList<Planet> planetlist;
	private ArrayList<Craft> craftlist;
	private final int width, height, SCR_width, SCR_height;
	private int mapwidth, mapheight;
	private float scalefactor;
	private float locationx, locationy;
	
	public Minimap(int width, int height, int SCR_width, int SCR_height, int mapwidth, int mapheight, ArrayList<Planet> planetlist, ArrayList<Craft> craftlist){
		this.planetlist = planetlist;
		this.craftlist = craftlist;
		this.width = width; //length of minimap
		this.height = height;
		this.SCR_width = SCR_width;
		this.SCR_height = SCR_height;
		this.mapwidth = mapwidth; //length of full map
		this.mapheight = mapheight;
		scalefactor = ((float)width)/((float) mapwidth);
		locationx = 0f;
		locationy = 0f;
	}
	
	public void update(float camX, float camY){
		locationx = camX;
		locationy = camY+SCR_height-height;
	}
	
	public void render(Graphics g){
		Color trans = new Color(0f,0f,0f,1f);
        g.setColor(trans);
        g.fillRect(locationx, locationy,width,height);
		for(int i = 0; i < planetlist.size(); i++){
			Planet mesh = planetlist.get(i);
			mesh.getImage().copy().draw((mesh.getX()*scalefactor)+locationx,(mesh.getY()*scalefactor)+locationy,scalefactor);
			mesh=null;
		}
		for(int e = 0; e < craftlist.size(); e++){
			Craft craft = craftlist.get(e);
			Image d = craft.getImage().copy();
			d.setCenterOfRotation(d.getCenterOfRotationX() * scalefactor, d.getCenterOfRotationY() * scalefactor);
			d.setRotation(craft.getRotation());
			d.draw(craft.getX()*scalefactor+locationx,craft.getY()*scalefactor+locationy, scalefactor);
			craft = null;
		}
	}
}
