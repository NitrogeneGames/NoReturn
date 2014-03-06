package nitrogene.util;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class BuyButton extends Button
{

	public BuyButton(String text, float x, float y, float width, float height,
			Image normalimage, Image hoverimage, Image pressedimage,
			Sound mouseover) throws FontFormatException, IOException,
			SlickException {
		super(text, x, y, width, height, normalimage, hoverimage, pressedimage,
				mouseover);
	}

	@Override
	 public void update (GameContainer gc)
    {
    	
        buttonReleased = false;
        

        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                buttonDown = true;
                if(pressedimage != null){
                renderImage = pressedimage;
                renderImage.setFilter(Image.FILTER_NEAREST);
                }
            }
            else
            {
                if (buttonDown)
                {
                    buttonDown = false;
                    buttonReleased = true;
                }
                if(hoverimage != null){
                renderImage = hoverimage;
                renderImage.setFilter(Image.FILTER_NEAREST);
                }
                if (i==1) i=2;
                if (i==0) i=1;
                if (i==1) {
                	if(mouseover != null){
                	mouseover.play();
                	}
                }
            }
        }
        else
        {
            buttonDown = false;
            if(normalimage != null){
            renderImage = normalimage;
            }
            i=0;
        }
        
    }
	
}
