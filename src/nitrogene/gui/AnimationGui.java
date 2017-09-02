package nitrogene.gui;

import nitrogene.core.AssetManager;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AnimationGui extends Animation{
	private float xS, yS;
	private boolean centered = false;
	private int width, height, rows, columns, xOffset, yOffset;
	private int duration;
	private String background;
	private boolean looping;
	private float scale;
	private Sound hit;
	private String soundSource;

	
	
	
	//Use AnimationImage for Sprites
	
	//Has sound for animation
	//Used for animations that are temporary, have x and y in constructor for this
	//REGISTER THESE WITH ANIMATIONMANAGER
	public AnimationGui(float x, float y, float scale, int duration, String sound, String background, int rows, int columns, int width, int height, boolean centered) throws SlickException{
		this(x, y, scale, duration, sound, background, rows, columns, width, height, centered, 0, 0);
	}	
	public AnimationGui(float x, float y, float scale, int duration, String sound, String background, int rows, int columns, int width, int height, boolean centered, int xOffset, int yOffset) throws SlickException{
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
	public AnimationGui copy() {
		try {
			return new AnimationGui(xS, yS, scale, duration, soundSource, background, rows, columns, width, height, centered, xOffset, yOffset);
		} catch (Exception e) {
			Resources.log("error copying animationgui");
			return null;
		}
	}
	
	
}
