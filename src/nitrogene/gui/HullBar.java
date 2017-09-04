package nitrogene.gui;

import nitrogene.core.GlobalInformation;
import nitrogene.util.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

public class HullBar {
	
	private Image hullBackground, hullFill, hullicon;
	private UnicodeFont font;
	private float offsetx;
	private float scalefactor;
	
	public HullBar(float f) throws SlickException{
		hullicon = new Image("res/gui/hullicon.png");
		hullFill = new Image("res/gui/hullbar2_fill.png");
		hullBackground = new Image("res/gui/hullbar2.png");
		this.scalefactor = f;
		
		font = GlobalInformation.getPixelFont(26F);
	}
	
	public void render(Graphics g, float progress) throws SlickException{
		hullicon.draw(73 + 256*2*scalefactor + 10*scalefactor + 3,20);
		hullBackground.draw(73 + 256*scalefactor + 10*scalefactor, 20, 256*scalefactor, 32*scalefactor);
		hullFill.draw(73 + 256*scalefactor + 10*scalefactor, 20, 256*progress*scalefactor, 32*scalefactor);
		g.setColor(Color.black);
		g.setFont(font);
		offsetx = Button.getTextWidth((int)(progress*100)+"%", font);
		g.drawString((int)(progress*100) + "%", 73+128*scalefactor + 256*scalefactor + 20*scalefactor - offsetx, 23);
	}
}
