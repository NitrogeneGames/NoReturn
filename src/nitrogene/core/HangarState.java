package nitrogene.core;

import java.awt.FontFormatException;
import java.io.IOException;

import nitrogene.util.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HangarState extends BasicGameState{

	private int width, height, repx, repy;
	private Image backgroundimg, observatory;
	private int scalefactor;
	private int obserx, obsery;
	
	private Button weapontab;
	
	public HangarState(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundimg = new Image("res/hangar/background.png");
		observatory = new Image("res/hangar/observatory1.png");
		observatory.setFilter(Image.FILTER_NEAREST);
		
		this.repx = (int) Math.ceil(width/100.0);
		this.repy = (int) Math.ceil(height/100.0);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(observatory.getWidth()*scalefactor/2);
		obsery = (height/2)-(observatory.getHeight()*scalefactor/2);
		
		Image normalimg = new Image("res/hangar/unpressedtab.png");
		Image pressedimg = new Image("res/hangar/pressedtab.png");
		try {
			weapontab = new Button("", obserx+(11*scalefactor), obsery+(48*scalefactor), 15*scalefactor, 8*scalefactor, normalimg, null, pressedimg, null);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		weapontab.update(container);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//Render repeating background
		for(int x = 0; x < repx; x++){
			for (int y = 0; y < repy; y++){
				backgroundimg.draw(x*100,y*100);
			}
		}
		
		observatory.draw(obserx, obsery, scalefactor);
		
		weapontab.render(g);
	}
	
	@Override
	public int getID() {
		return 3;
	}

}
