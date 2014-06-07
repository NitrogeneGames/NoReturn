package nitrogene.gui;

import nitrogene.core.GlobalInformation;
import nitrogene.util.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

public class ShieldBar {
	
	private Image shieldBackground, shieldFill, shieldicon;
	private UnicodeFont font;
	private float offsetx;
	private float scalefactor;
	
	public ShieldBar(float f) throws SlickException{
		shieldicon = new Image("res/gui/shieldicon2.png");
		shieldFill = new Image("res/gui/shieldbar2_fill.png");
		shieldBackground = new Image("res/gui/shieldbar2.png");
		shieldFill.setFilter(Image.FILTER_NEAREST);
		shieldBackground.setFilter(Image.FILTER_NEAREST);
		shieldicon.setFilter(Image.FILTER_NEAREST);
		this.scalefactor = f;
		
		font = GlobalInformation.getPixelFont(26F);
	}
	
	public void render(Graphics g, float camX, float camY, float progress) throws SlickException{
		shieldicon.draw(camX+5,camY+26,0.7f);
		shieldBackground.draw(camX + 73, camY + 20, scalefactor);
		shieldFill.draw(camX+73, camY+20, 256*progress*scalefactor, 32*scalefactor);
		g.setColor(Color.black);
		g.setFont(font);
		offsetx = Button.getTextWidth((int)(progress*100)+"%", font)/2;
		g.drawString((int)(progress*100) + "%", camX+73+128*scalefactor - offsetx + 10*scalefactor, camY + 23);
	}
	
}
