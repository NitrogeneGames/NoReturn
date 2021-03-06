package nitrogene.gui;

import nitrogene.core.AssetManager;
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
		shieldicon = (Image) AssetManager.get().get("shieldicon");
		shieldFill = (Image) AssetManager.get().get("shieldfill");
		shieldBackground = (Image) AssetManager.get().get("shieldbackground");
		this.scalefactor = f;
		
		font = GlobalInformation.getPixelFont(26F);
	}
	
	public void render(Graphics g, float progress) throws SlickException{
		shieldicon.draw(5,26,0.7f);
		shieldBackground.draw(73, 20, scalefactor);
		shieldFill.draw(73, 20, 256*progress*scalefactor, 32*scalefactor);
		g.setColor(Color.black);
		g.setFont(font);
		offsetx = Button.getTextWidth((int)(progress*100)+"%", font)/2;
		g.drawString((int)(progress*100) + "%", 73+128*scalefactor - offsetx + 10*scalefactor, 23);
	}
	
}
