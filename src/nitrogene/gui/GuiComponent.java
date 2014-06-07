package nitrogene.gui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.util.Button;

public class GuiComponent extends Button{

	public GuiComponent(float x, float y, Image onimage, Image offimage)
			throws FontFormatException, IOException, SlickException {
		super("", x, y, onimage.getWidth(), onimage.getHeight(), onimage, null, offimage, null);
	}
	
	
}
