package nitrogene.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HullBar {
	
	private Image hullBackground, hullFill;
	private int scalefactor;
	
	public HullBar(int scalefactor) throws SlickException{
		hullFill = new Image("res/gui/HullBarFullNoBorder.png");
		hullBackground = new Image("res/gui/HullBar1.png");
		hullFill.setFilter(Image.FILTER_NEAREST);
		hullBackground.setFilter(Image.FILTER_NEAREST);
		this.scalefactor = scalefactor;
	}
	
	public void render(float camX, float camY, float progress){
		hullBackground.draw(camX + 25, camY + 20 + (62*scalefactor), scalefactor);
		hullFill.draw(camX+25, camY+20 + (62*scalefactor), 62*progress*scalefactor, 14*scalefactor);
	}
}
