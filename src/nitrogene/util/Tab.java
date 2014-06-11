package nitrogene.util;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Tab extends Button{

	public Tab(String text, float x, float y, float width, float height)
			throws FontFormatException, IOException, SlickException {
		super(text, x, y, width, height);
	}
	public Tab(String text, float x, float y, float width, float height, Sound mouseover)
			throws FontFormatException, IOException, SlickException {
		super(text, x, y, width, height, mouseover);
	}
	@Override
	public void update(GameContainer gc) {
        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
            {
            	this.buttonDown = !this.buttonDown;           	
            }
        }
        if(this.buttonDown) {
        	this.renderImage = "pressedimage";
        } else {
        	this.renderImage = "normalimage";
        }
	}
	
	public boolean getButtonDown(){
		return buttonDown;
	}
}
