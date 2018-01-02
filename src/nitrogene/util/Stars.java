package nitrogene.util;

import java.util.ArrayList;
import java.util.Random;

import nitrogene.core.AssetManager;
import nitrogene.core.Resources;
import nitrogene.core.Zoom;
import nitrogene.gui.Sprite;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stars {
	private int n, biggeststarsize;
	private int initial_x, initial_y;
	private ArrayList<int[]> stars = new ArrayList<int[]>();
	private Image backgroundimg;
	private Graphics gr;
	
	public Stars(int biggeststarsize, int mapwidth, int mapheight, int startx, int starty, int frequency) throws SlickException{
		//Create an image to place the stars on
		/*backgroundimg = new Image(mapwidth, mapheight);
		gr = backgroundimg.getGraphics();
		Color black = new Color(1f,1f,1f,1f);
		gr.setColor(black);
		//Load star generation system
    	
    	
    	initial_x = startx;
    	initial_y = starty;
    	Image twopixelstar = new Sprite("twopixelstar").getImage();

    	for(int i = 0; i < n; i++) {
	    	Random random = new Random();
	    	gr.drawImage(twopixelstar, random.nextInt(mapwidth+startx - biggeststarsize) + 1, random.nextInt(mapheight+starty - biggeststarsize) + 1);
    	}
    	this.biggeststarsize = biggeststarsize;
    	gr.flush();*/
		n = 1100;
    	for(int i = 0; i < n; i++) {
	    	Random random = new Random();
	    	stars.add(new int[] { random.nextInt(mapwidth+startx - biggeststarsize) + 1,     			
	    			random.nextInt(mapheight+starty - biggeststarsize) + 1});
    	}
	}
	
	//Get the graphics from Gamestate
	/*public void render(Graphics g){
		g.drawImage(backgroundimg, initial_x, initial_y);
	}*/
	
	public void render(Graphics g){
		
		for(int[] loc : stars){
			Image twopixelstar = ((Image) AssetManager.get().get("twopixelstar")).copy();
			twopixelstar.draw(loc[0], loc[1]);
		}
	}
}
