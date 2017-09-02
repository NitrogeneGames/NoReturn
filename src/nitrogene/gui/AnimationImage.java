package nitrogene.gui;

import nitrogene.core.AssetManager;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AnimationImage extends Animation{
	private float xS, yS;
	private boolean centered = false;
	private int width, height, rows, columns, xOffset, yOffset;
	private int duration;
	private String background;
	private boolean looping;
	private float scale;
	
	
	
	//Used for Sprites
	//DO NOT REGISTER WITH ANIMATION MANAGER, IT WILL CRASH. NEEDS TO BE DRAWN WITH X and Y
	public AnimationImage(int duration, String background, int rows, int columns, int width, int height, boolean looping) throws SlickException{
		this(duration, background, rows, columns, width, height, looping, 0, 0);
	}	
	public AnimationImage(int duration, String background, int rows, int columns, int width, int height, boolean looping, int xOffset, int yOffset) throws SlickException{
		super();
		this.duration = duration;
		this.background = background;
		this.looping = looping;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.setLooping(looping);
		init(duration, background);
	}	
	
	//Use animation gui for temporary animations
	
	private void init(int duration, String background) throws SlickException{
		Image baseimage = ((Image) AssetManager.get().get(background)).copy();
		//Initializes explosion into memory, row = rows, col = columns
		//baseimage.getSubImage(x,y,width,height)
		for (int row=0;row<rows;row++) {
            for(int col=0;col<columns;col++){
               this.addFrame(baseimage.getSubImage((int) (col*(width+xOffset)),(int)(row*(height+yOffset)),width,height).copy(), duration);
            }
         }
	}
	
	@Override
	public void draw(float x, float y){
		draw(x,y,width,height);
	}
	public void draw(float x, float y, float s){
		draw(x,y,width*s,height*s);
	}
	public void drawCentered(float x, float y){
		drawCentered(x,y,1);
	}
	public void drawCentered(float x, float y, float s){
		draw(x-(width/2*s),y-(height/2*s),getWidth()*s,getHeight()*s);
	}

	public AnimationImage copy() {
		try {
			return new AnimationImage(duration, background, rows, columns, width, height, looping, xOffset, yOffset);
		} catch (Exception e) {
			Resources.log("error copying animationimage");
			return null;
		}
	}
	
	
}
