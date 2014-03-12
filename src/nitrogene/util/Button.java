package nitrogene.util;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;

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
    @SuppressWarnings("unused")
	protected float height;
    protected Vector2f position;

    @SuppressWarnings("unchecked")
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
        hoverimage = new Image("res/button/logomenubutton2hover.png");
        pressedimage = new Image("res/button/logomenubutton2hover.png");
        renderImage = normalimage;
        
        //font init
        mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, 25.f);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(Color.white);
        uniFont.getEffects().add(a);
        uniFont.loadGlyphs();
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
        if(hoverimage!=null){
        this.pressedimage.setFilter(Image.FILTER_NEAREST);
        }
        this.renderImage.setFilter(Image.FILTER_NEAREST);
        
        //font init
        mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, 25.f);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(Color.white);
        uniFont.getEffects().add(a);
        uniFont.loadGlyphs();
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

        int margin = ((int) width - getTextWidth(text, uniFont)) / 2;

        gr.setFont(uniFont);
        gr.drawString(text, button.getMinX() + margin, button.getMinY() + 13);
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

    protected int getTextWidth (String text, Font font)
    {
        int width = 0;

        for (char ch : text.toCharArray())
            if (ch == ' ')
                width += 2;
            else
                width += font.getWidth(String.valueOf(ch));

        return (int) (width * 1.14);
    }
}