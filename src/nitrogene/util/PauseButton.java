package nitrogene.util;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class PauseButton {
	private Rectangle button;
    private boolean buttonDown = false;
    private boolean buttonReleased = false;
    private int i = 0;
    
    private Image normalimage;
    private Image hoverimage;
    private Image renderImage;
    
    Sound mouseover;
    private Vector2f position;

	public PauseButton (float x, float y, Image normalimage, Image hoverimage) throws FontFormatException, IOException, SlickException
    {
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, 84, 28);
        this.normalimage = normalimage;
        this.hoverimage = hoverimage;
        mouseover = new Sound("res/sound/rollover_2.ogg");
        
    }

    public void update (GameContainer gc)
    {
    	
        buttonReleased = false;
        

        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                buttonDown = true;
                renderImage = hoverimage;
                renderImage.setFilter(Image.FILTER_NEAREST);
            }
            else
            {
                if (buttonDown)
                {
                    buttonDown = false;
                    buttonReleased = true;
                }
                renderImage = hoverimage;
                renderImage.setFilter(Image.FILTER_NEAREST);
                if (i==1) i=2;
                if (i==0) i=1;
                if (i==1) {
                	mouseover.play();
                }
            }
        }
        else
        {
            buttonDown = false;
            renderImage = normalimage;
            renderImage.setFilter(Image.FILTER_NEAREST);
            i=0;
        }
        
    }

    public void render (Graphics gr, float camX, float camY)
    {
        renderImage.draw(position.x+camX,position.y+camY);
    }

    public boolean isClicked()
    {
        if (buttonReleased)
        {
            buttonReleased = false;
            return true;
        }
        return false;
    }
    
}
