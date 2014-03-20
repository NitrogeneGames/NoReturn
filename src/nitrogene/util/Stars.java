package nitrogene.util;

import java.util.Random;

import nitrogene.core.Zoom;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stars {
	private int n, biggeststarsize;
	private int[] starx, stary;
	private Image twopixelstar;
	
	public Stars(int biggeststarsize, int mapwidth, int mapheight, int startx, int starty, int frequency) throws SlickException{
    	Random rand = new Random();
    	n = rand.nextInt(10) + 1100;
    	starx = new int[n];
    	stary = new int[n];
    	for(int i = 0; i < n; i++) {
    	Random randomx = new Random();
    	starx[i] = randomx.nextInt(mapwidth+startx - biggeststarsize) + 1;
    	Random randomy = new Random();
    	stary[i] = randomy.nextInt(mapheight+starty - biggeststarsize) + 1;
    	}
    	this.biggeststarsize = biggeststarsize;
    	twopixelstar = new Image("res/star2.png");
	}
	
	public void render(float camX, float camY){
		for(int g1 = 0; g1 < n; g1++){
			if(starx[g1]-biggeststarsize>Zoom.getZoomWidth()+camX||starx[g1]+biggeststarsize<camX){
				continue;
			}
			if(stary[g1]-biggeststarsize>Zoom.getZoomHeight()+camY||stary[g1]+biggeststarsize<camY){
				continue;
			}
			twopixelstar.draw(starx[g1],stary[g1]);
		}
	}
}
