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
	private Sound hit;
	private String soundSource;

	
	
	
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
	
	//Has sound for animation
	//Used for animations that are temporary, have x and y in constructor for this
	//REGISTER THESE WITH ANIMATIONMANAGER
	public AnimationImage(float x, float y, float scale, int duration, String sound, String background, int rows, int columns, int width, int height, boolean centered) throws SlickException{
		this(x, y, scale, duration, sound, background, rows, columns, width, height, centered, 0, 0);
	}	
	public AnimationImage(float x, float y, float scale, int duration, String sound, String background, int rows, int columns, int width, int height, boolean centered, int xOffset, int yOffset) throws SlickException{
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
		this.xS = x;
		this.yS = y;
		this.soundSource = sound;
		hit = (Sound) AssetManager.get().get(sound);
		this.setLooping(false);
		this.scale = scale;
		init(duration, background);
		try {
			if(hit.playing()){
				hit.stop();
			}
			hit.play(1f, GlobalInformation.sfxlevel);
		} catch (Exception e) {
			Resources.log("Error with explosion sound effects");
		}
	}
	
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

	
	@Override
	public void draw(){
		if(centered) {
			drawCentered();
		} else {
			draw(xS,yS,width*scale,height*scale);
		}
	}
	public void drawCentered(){
		draw(xS-(width/2*scale),yS-(height/2*scale),getWidth()*scale,getHeight()*scale);
	}
	public AnimationImage copy() {
		try {
			if(this.hit == null) {
				return new AnimationImage(duration, background, rows, columns, width, height, looping, xOffset, yOffset);
			} else {
				return new AnimationImage(xS, yS, scale, duration, soundSource, background, rows, columns, width, height, centered, xOffset, yOffset);
			}
		} catch (Exception e) {
			Resources.log("error copying animationimage");
			return null;
		}
	}
	
	
}
