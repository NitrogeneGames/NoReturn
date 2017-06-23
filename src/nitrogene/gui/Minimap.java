package nitrogene.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.world.Planet;

public class Minimap {
	private ArrayList<Planet> planetlist;
	public Craft focus;
	private ArrayList<Craft> craftlist;
	private final int width, height, SCR_width, SCR_height;
	private int mapwidth, mapheight;
	private float scalefactor;
	private float locationx, locationy;
	private Image l;
	private Graphics g;
	
	public Minimap(int width, int height, int SCR_width, int SCR_height, int mapwidth, int mapheight, ArrayList<Planet> planetlist, ArrayList<Craft> craftlist, Craft f){
		this.focus = f;
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
		try {
			l = new Image(width, height);
			g = l.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(float camX, float camY){
		locationx = camX;
		locationy = camY+SCR_height-height;
	}
	
	public void render(Graphics gr){
		gr.drawImage(l, locationx, locationy);
		//g = new Graphics();
		Color trans = new Color(0f,0f,0f,1f);
        g.setColor(trans);
        g.fillRect(0, 0,width,height);
		for(int i = 0; i < planetlist.size(); i++){
			Planet mesh = planetlist.get(i);
			float x = ((mesh.getX()-focus.getX())*scalefactor)+width/2-focus.width/2*scalefactor;
			float y = ((mesh.getY()-focus.getY())*scalefactor)+height/2-focus.height/2*scalefactor;
			mesh.getImage().copy().draw(x,y,scalefactor);
			mesh=null;
		}
		for(int e = 0; e < craftlist.size(); e++){
			Craft craft = craftlist.get(e);
			float x = ((craft.getX()-focus.getX())*scalefactor)+width/2-focus.width/2*scalefactor;
			float y = ((craft.getY()-focus.getY())*scalefactor)+height/2-focus.height/2*scalefactor;
			Image d = craft.getImage().copy();
			d.setCenterOfRotation(d.getCenterOfRotationX() * scalefactor, d.getCenterOfRotationY() * scalefactor);
			d.setRotation(craft.getRotation());
			d.draw(x, y, scalefactor);
			craft = null;
		}
	}
}
