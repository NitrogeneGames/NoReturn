package nitrogene.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ShieldBar {
	
	private Image shieldBackground, shieldFill, shieldicon;
	private float scalefactor;
	
	public ShieldBar(float f) throws SlickException{
		shieldicon = new Image("res/gui/shieldicon2.png");
		shieldFill = new Image("res/gui/shieldbar2_fill.png");
		shieldBackground = new Image("res/gui/shieldbar2.png");
		shieldFill.setFilter(Image.FILTER_NEAREST);
		shieldBackground.setFilter(Image.FILTER_NEAREST);
		shieldicon.setFilter(Image.FILTER_NEAREST);
		this.scalefactor = f;
	}
	
	public void render(float camX, float camY, float progress){
		shieldicon.draw(camX+5,camY+26,0.7f);
		shieldBackground.draw(camX + 73, camY + 20, scalefactor);
		shieldFill.draw(camX+73, camY+20, 256*progress*scalefactor, 32*scalefactor);
	}
	
}
