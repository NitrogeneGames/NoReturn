package nitrogene.util;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Button
{
    protected Rectangle button;
    protected boolean buttonDown = false;
    protected boolean buttonReleased = false;
    protected int i = 0;
    
    protected Image normalimage;
    protected Image hoverimage;
    protected Image pressedimage;
    protected Image renderImage;
    
    Sound mouseover;
    protected String text;
    java.awt.Font mainFont;
    org.newdawn.slick.UnicodeFont uniFont;
    
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
        this.renderImage = normalimage;
        mouseover = new Sound("res/sound/rollover_2.ogg");
        
        normalimage = new Image("res/button/logomenbutton21.png");
        normalimage.setFilter(Image.FILTER_NEAREST);
        hoverimage = new Image("res/button/logomenubutton2hover.png");
        hoverimage.setFilter(Image.FILTER_NEAREST);
        pressedimage = new Image("res/button/logomenubutton2hover.png");
        pressedimage.setFilter(Image.FILTER_NEAREST);
        renderImage = normalimage;
        
        //font init
        enter(25);
    }
    
    public Button (String text, float x, float y, float width, float height, Image normalimage, Image hoverimage, Image pressedimage, Sound mouseover) throws FontFormatException, IOException, SlickException
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, width, height);
        this.renderImage = normalimage;
        this.mouseover = mouseover;
        
        this.normalimage = normalimage;
        this.hoverimage = hoverimage;
        this.pressedimage = pressedimage;
        renderImage = normalimage;
        
        if(normalimage!=null){
        this.normalimage.setFilter(Image.FILTER_NEAREST);
        }
        if(hoverimage!=null){
        this.hoverimage.setFilter(Image.FILTER_NEAREST);
        }
        if(pressedimage!=null){
        this.pressedimage.setFilter(Image.FILTER_NEAREST);
        }
        this.renderImage.setFilter(Image.FILTER_NEAREST);
        
        //font init
       // GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        enter(25);
    }

    
    public Button (String text, float x, float y, float width, float height, Image normalimage, Image hoverimage, Image pressedimage, Sound mouseover, float fontsize) throws FontFormatException, IOException, SlickException
    {
        this.text = text;
        this.width = width;
        this.height = height;
        this.position = new Vector2f(x, y);
        this.button = new Rectangle(position.x, position.y, width, height);
        this.renderImage = normalimage;
        this.mouseover = mouseover;
        
        this.normalimage = normalimage;
        this.hoverimage = hoverimage;
        this.pressedimage = pressedimage;
        renderImage = normalimage;
        
        if(normalimage!=null){
        this.normalimage.setFilter(Image.FILTER_NEAREST);
        }
        if(hoverimage!=null){
        this.hoverimage.setFilter(Image.FILTER_NEAREST);
        }
        if(pressedimage!=null){
        this.pressedimage.setFilter(Image.FILTER_NEAREST);
        }
        this.renderImage.setFilter(Image.FILTER_NEAREST);
         
        //font init
       // GL11.glEnable(GL11.GL_BLEND);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        enter(fontsize);
    }
    
    public void enter(float fontsize) throws FontFormatException, IOException, SlickException{
    	mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, fontsize);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(Color.white);
        uniFont.getEffects().add(a);
        uniFont.loadGlyphs();
        buttonDown = false;
    }
    
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

    public void render (Graphics gr)
    {
    	renderImage.setFilter(Image.FILTER_NEAREST);
        renderImage.draw(position.x,position.y,width,height);

        int marginx = (int) (width - (getTextWidth(text, uniFont))) / 2;
        int marginy = (int) (height - (getTextHeight(text, uniFont))) / 2;

        //gr.setFont(uniFont);
        uniFont.drawString(button.getMinX() + marginx, button.getMinY() + marginy, text);
    }
    
    public void render(Graphics g, int scalefactor){}

    public boolean isClicked()
    {
        if (buttonReleased)
        {
            buttonReleased = false;
            return true;
        }
        return false;
    }

    protected float getTextWidth (String text, Font font)
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

	public void update(GameContainer container, ArrayList<EnumWeapon> weapons,
			EnumWeapon basic) {		
	}
}