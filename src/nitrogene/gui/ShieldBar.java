package nitrogene.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ShieldBar {
	
	private Image shieldBackground, shieldFill;
	private int scalefactor;
	
	public ShieldBar(int scalefactor) throws SlickException{
		shieldFill = new Image("res/gui/ShieldBarFilledNoBorder.png");
		shieldBackground = new Image("res/gui/ShieldBarBG.png");
		shieldFill.setFilter(Image.FILTER_NEAREST);
		shieldBackground.setFilter(Image.FILTER_NEAREST);
		this.scalefactor = scalefactor;
	}
	
	public void render(float camX, float camY, float progress){
		shieldBackground.draw(camX + 25, camY + 20, scalefactor);
		shieldFill.draw(camX+26, camY+21, 62*progress*scalefactor, 14*scalefactor);
	}
	
}
