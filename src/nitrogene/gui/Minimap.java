package nitrogene.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.core.Craft;
import nitrogene.world.Asteroid;
import nitrogene.world.Planet;

public class Minimap {
	public Craft focus;
	private final int width, height, SCR_width, SCR_height;
	private int mapwidth, mapheight;
	private float scalefactor;
	private float locationx, locationy;
	private Image l;
	private Graphics g;
	
	public Minimap(int width, int height, int SCR_width, int SCR_height, int mapwidth, int mapheight, Craft f){
		this.focus = f;
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
	
	public void update(){

	}
	private boolean imageBasedMinimap = true;
	public void render(Graphics gr, float camX, float camY, ArrayList<Planet> planetlist, ArrayList<Craft> craftlist, ArrayList<Asteroid> asteroidlist){
		locationx = camX;
		locationy = camY+SCR_height-height;
		//g = new Graphics();
		float statx = 0;
		float staty = 0;
		if(!imageBasedMinimap) {
			g = gr;
			statx = locationx;
			staty = locationy;
		}
		Color trans = new Color(0f,0f,0f,1f);
        g.setColor(trans);
        g.fillRect(statx, staty,width,height);
		for(int i = 0; i < planetlist.size(); i++){
			Planet mesh = planetlist.get(i);
			float x = ((mesh.getX()-focus.getX())*scalefactor)+statx+width/2-focus.width/2*scalefactor;
			float y = ((mesh.getY()-focus.getY())*scalefactor)+staty+height/2-focus.height/2*scalefactor;
			Image d = mesh.getImage().getScaledCopy(mesh.getScale());
			d.setCenterOfRotation(d.getCenterOfRotationX() * scalefactor, d.getCenterOfRotationY() * scalefactor);
			d.setRotation(mesh.getRotation());
			d.draw(x, y, scalefactor);
			mesh=null;
		}
		for(int e = 0; e < craftlist.size(); e++){
			Craft mesh = craftlist.get(e);
			float x = ((mesh.getX()-focus.getX())*scalefactor)+statx+width/2-focus.width/2*scalefactor;
			float y = ((mesh.getY()-focus.getY())*scalefactor)+staty+height/2-focus.height/2*scalefactor;
			Image d = mesh.getImage().getScaledCopy(mesh.getScale());
			d.setCenterOfRotation(d.getCenterOfRotationX() * scalefactor, d.getCenterOfRotationY() * scalefactor);
			d.setRotation(mesh.getRotation());
			d.draw(x, y, scalefactor);
			mesh=null;
		}
		for(int e = 0; e < asteroidlist.size(); e++){
			Asteroid mesh = asteroidlist.get(e);
			float x = ((mesh.getX()-focus.getX())*scalefactor)+statx+width/2-focus.width/2*scalefactor;
			float y = ((mesh.getY()-focus.getY())*scalefactor)+staty+height/2-focus.height/2*scalefactor;
			Image d = mesh.getImage().getScaledCopy(mesh.getScale());
			d.setCenterOfRotation(d.getCenterOfRotationX() * scalefactor, d.getCenterOfRotationY() * scalefactor);
			d.setRotation(mesh.getRotation());
			d.draw(x, y, scalefactor);
			mesh=null;
		}
		g.flush();
		gr.drawImage(l, locationx, locationy);
	}
}
