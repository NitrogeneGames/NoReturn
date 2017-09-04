package nitrogene.util;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.core.GlobalInformation;
import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Button
{
    protected Rectangle button;
    protected float fontsize;
    protected Font font;
    protected boolean buttonDown = false;
    protected boolean buttonReleased = false;
    protected int i = 0;
    
    protected String renderImage = "";
    
    Sound mouseover;
    protected String text;
    
    protected float width;
	protected float height;
    protected Vector2f position;

	public Button (String text, float x, float y, float width, float height) throws FontFormatException, IOException, SlickException
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, width, height);
        mouseover = new Sound("res/sound/rollover_2.ogg");
        
        fontsize = 25f;
    }
    
    public Button (String text, float x, float y, float width, float height, Sound mouseover) throws FontFormatException, IOException, SlickException
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, width, height);
        this.mouseover = mouseover;
        
        fontsize = 25;
    }

    
    public Button (String text, float x, float y, float width, float height, Sound mouseover, float fontsize) throws FontFormatException, IOException, SlickException
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, width, height);
        this.mouseover = mouseover;
         
        this.fontsize = fontsize;
    }
    
    public void update (GameContainer gc)
    {
    	if(this.font==null){
    		try {
				font = GlobalInformation.getMenuFont(fontsize);
			} catch (SlickException e) {
				e.printStackTrace();
			}
    	}
        buttonReleased = false;
        

        if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()))
        {
        	
            if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
            {
                buttonDown = true;
                renderImage = "pressedimage";
            }
            else
            {
                if (buttonDown)
                {
                    buttonDown = false;
                    buttonReleased = true;
                }
                renderImage = "hoverimage";
               
                if (i==1) i=2;
                if (i==0) i=1;
                if (i==1) {
                	if(mouseover != null){
                	mouseover.play();
                	}
                }
            }
        }else
        {
            renderImage = "normalimage";
            i=0;
        }
        
    }

    public void render (Graphics gr, Image normalimage, Image pressedimage, Image hoverimage)
    {
    	Image temp = null;
    	if(pressedimage!=null && renderImage == "pressedimage"){
    		temp = pressedimage;
    	} else if(hoverimage!=null && renderImage == "pressedimage"){
    		temp = hoverimage;
    	} else if(hoverimage !=null && renderImage == "hoverimage"){
    		temp = hoverimage;
    	} else{
    		temp = normalimage;
    	}
    	temp.setFilter(Image.FILTER_NEAREST);
        temp.draw(position.x,position.y,width,height);

        int marginx = (int) (width - (getTextWidth(text, font))) / 2;
        int marginy = (int) (height - (getTextHeight(text, font))) / 2;

        if(text != null){
            gr.setFont(font);
            gr.drawString(text,  button.getMinX() + marginx, button.getMinY() + marginy);
        }
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

    public static float getTextWidth (String text, Font font)
    {
        float width = 0;

        for (char ch : text.toCharArray())
            if (ch == ' ')
                width += 8f;
            else
                width += font.getWidth(String.valueOf(ch));

        return(width * 1.08f);
    }
    
    protected float getTextHeight (String text, Font font){
    	int height = 0;
    	int charcount =0;

        for (char ch : text.toCharArray()){
        	charcount++;
            height += font.getHeight(String.valueOf(ch));
        }

        if(charcount==0){
        	return 0;
        }
        
        return (height/charcount*1.32f);
    }
 
    public boolean buttonDown(){
    	return buttonDown;
    }

	public void update(GameContainer container, ArrayList<EnumWeapon> weapons) {		
	}

	public void render(Graphics g, int scalefactor, Image renderImage,
			Image pressedImage) {
		
	}

	public void render(Graphics gr, Image normalimage, Image pressedimage) {
	}

}