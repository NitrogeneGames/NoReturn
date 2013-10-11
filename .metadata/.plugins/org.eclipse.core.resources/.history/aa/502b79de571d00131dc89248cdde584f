package nitrogene.util;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Stars {
	private int n, biggeststarsize;
	private int[] starx, stary;
	private int screenwidth, screenheight;
	private Image twopixelstar;
	
	public Stars(int biggeststarsize, int mapwidth, int mapheight, int windowwidth, int windowheight) throws SlickException{
    	Random rand = new Random();
    	n = rand.nextInt(10) + 510;
    	starx = new int[n];
    	stary = new int[n];
    	for(int i = 0; i < n; i++) {
    	Random randomx = new Random();
    	starx[i] = randomx.nextInt(mapwidth - biggeststarsize) + 1;
    	Random randomy = new Random();
    	stary[i] = randomy.nextInt(mapheight - biggeststarsize) + 1;
    	}
    	this.biggeststarsize = biggeststarsize;
    	twopixelstar = new Image("res/star2.png");
    	screenwidth = windowwidth;
    	screenheight = windowheight;
	}
	
	public void render(float camX, float camY){
		for(int g1 = 0; g1 < n; g1++){
			if(starx[g1]-biggeststarsize>screenwidth+camX||starx[g1]+biggeststarsize<camX){
				continue;
			}
			if(stary[g1]-biggeststarsize>screenheight+camY||stary[g1]+biggeststarsize<camY){
				continue;
			}
			twopixelstar.draw(starx[g1],stary[g1]);
		}
	}
}
