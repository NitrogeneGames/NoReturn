package nitrogene.util;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.sun.net.httpserver.Filter;

public class BuyButton extends Button
{
	private int cost;
	private Image coins;
	private Image finalrender;
	private boolean selected;

	public BuyButton(String name, int cost, float x, float y, float width, float height,
			Image normalimage, Image pressedimage,
			Sound mouseover) throws FontFormatException, IOException,
			SlickException {
		super(name, x, y, width, height, normalimage, null, pressedimage,
				mouseover);
		this.cost = cost;
		finalrender = renderImage;
		selected = false;
		coins = new Image("res/hangar/twocoins.png");
		coins.setFilter(Image.FILTER_NEAREST);
	}

	@Override
	public void update (GameContainer gc, ArrayList<EnumWeapon> weapons, EnumWeapon enumwep)
     {
    	
        buttonReleased = false;
        
        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            	System.out.println("HELLO!");
            	if(finalrender == pressedimage){
            		finalrender = renderImage;
            		weapons.remove(enumwep);
            		buttonDown = false;
            		return;
            	}
            	if(finalrender == renderImage){
            		finalrender = pressedimage;
            		weapons.add(enumwep);
            		buttonDown = true;
            	}
            }
        }
        /*
        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                buttonDown = true;
                System.out.println("CALLED");
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
        */
    }
	
	@Override
	public void render(Graphics g, int scalefactor){
		finalrender.setFilter(Image.FILTER_NEAREST);
        finalrender.draw(position.x,position.y,width,height);

        g.setFont(uniFont);
        
        //draw the name of the item
        g.drawString(text, button.getMinX() + (9*scalefactor), button.getMinY() + (1*scalefactor));
        
        int margin = this.getTextWidth(String.valueOf(cost), uniFont);
        g.drawString(String.valueOf(cost), button.getMinX()+ (100*scalefactor) - margin - (12*scalefactor), button.getMinY() + (1*scalefactor));
        coins.draw(button.getMinX() + (100*scalefactor) - (11.5f*scalefactor), button.getMinY() + (2*scalefactor), scalefactor/4);
	}

}
