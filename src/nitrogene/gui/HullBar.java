package nitrogene.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HullBar {
	
	private Image hullBackground, hullFill, hullicon;
	private float scalefactor;
	
	public HullBar(float f) throws SlickException{
		hullFill = new Image("res/gui/hullbar2_fill.png");
		hullBackground = new Image("res/gui/hullbar2.png");
		hullicon = new Image("res/gui/hullicon.png");
		hullFill.setFilter(Image.FILTER_NEAREST);
		hullBackground.setFilter(Image.FILTER_NEAREST);
		hullicon.setFilter(Image.FILTER_NEAREST);
		this.scalefactor = f;
	}
	
	public void render(float camX, float camY, float progress){
		hullicon.draw(camX + 73 + 256*2*scalefactor + 10*scalefactor + 3,camY+20);
		hullBackground.draw(camX + 73 + 256*scalefactor + 10*scalefactor, camY + 20, 256*scalefactor, 32*scalefactor);
		hullFill.draw(camX+ 73 + 256*scalefactor + 10*scalefactor, camY + 20, 256*progress*scalefactor, 32*scalefactor);
	}
}
