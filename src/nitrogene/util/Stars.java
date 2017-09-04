package nitrogene.util;

import java.util.Random;

import nitrogene.core.AssetManager;
import nitrogene.core.Zoom;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stars {
	private int n, biggeststarsize;
	private int initial_x, initial_y;
	private int[] starx, stary;
	private Image backgroundimg;
	private Graphics gr;
	
	public Stars(int biggeststarsize, int mapwidth, int mapheight, int startx, int starty, int frequency) throws SlickException{
		//Create an image to place the stars on
		backgroundimg = new Image(mapwidth, mapheight);
		gr = backgroundimg.getGraphics();
		Color black = new Color(1f,1f,1f,1f);
		gr.setColor(black);
		//Load star generation system
    	Random rand = new Random();
    	n = rand.nextInt(10) + 1100;
    	starx = new int[n];
    	stary = new int[n];
    	initial_x = startx;
    	initial_y = starty;
    	for(int i = 0; i < n; i++) {
	    	Random randomx = new Random();
	    	starx[i] = randomx.nextInt(mapwidth+startx - biggeststarsize) + 1;
	    	Random randomy = new Random();
	    	stary[i] = randomy.nextInt(mapheight+starty - biggeststarsize) + 1;
    	}
    	this.biggeststarsize = biggeststarsize;
    	render_ontoimg();
	}
	
	public void render_ontoimg() throws SlickException{
		Image twopixelstar = (Image) AssetManager.get().get("twopixelstar");
		for(int g1 = 0; g1 < n; g1++){
			gr.drawImage(twopixelstar, starx[g1], stary[g1]);
		}
		gr.flush();
	}
	
	//Get the graphics from Gamestate
	public void render(Graphics g){
		g.drawImage(backgroundimg, initial_x, initial_y);
	}
	
	@Deprecated
	public void render(Graphics g, float camX, float camY){
		for(int g1 = 0; g1 < n; g1++){
			if(starx[g1]-biggeststarsize>Zoom.getZoomWidth()+camX||starx[g1]+biggeststarsize<camX){
				continue;
			}
			if(stary[g1]-biggeststarsize>Zoom.getZoomHeight()+camY||stary[g1]+biggeststarsize<camY){
				continue;
			}
			Image twopixelstar = (Image) AssetManager.get().get("twopixelstar");
			twopixelstar.draw(starx[g1],stary[g1]);
		}
	}
}
