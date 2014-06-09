package nitrogene.gui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.util.Button;

public class GuiComponent extends Button{
	private String id;
	protected float scalefactor;
	protected float originx, originy;

	public GuiComponent(String id, float x, float y, Image onimage, Image offimage, float scalefactor)
			throws FontFormatException, IOException, SlickException {
		super("", x, y, onimage.getWidth()*scalefactor, onimage.getHeight()*scalefactor, onimage, null, offimage, null);
		this.originx = x;
		this.originy = y;
		this.id = id;
		this.scalefactor = scalefactor;
	}
	
	public String getId(){
		return id;
	}
	
	public void move(float camX, float camY){
		this.position.set(originx+camX,originy+camY);
	}
}
