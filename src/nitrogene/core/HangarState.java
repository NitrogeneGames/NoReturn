package nitrogene.core;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.Button;
import nitrogene.util.BuyButton;
import nitrogene.util.Tab;

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
	
	//tabs
	private ArrayList<Tab> tablist;
	private Tab weapontab;
	
	//buttons for purchasing systems
	private Button basiclaserbutton;
	
	public HangarState(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		tablist = new ArrayList<Tab>();
		
		backgroundimg = new Image("res/hangar/background.png");
		observatory = new Image("res/hangar/observatory1.png");
		observatory.setFilter(Image.FILTER_NEAREST);
		
		this.repx = (int) Math.ceil(width/100.0);
		this.repy = (int) Math.ceil(height/100.0);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(observatory.getWidth()*scalefactor/2);
		obsery = (height/2)-(observatory.getHeight()*scalefactor/2);
		
		Image normalimgtab = new Image("res/hangar/tab2.png");
		Image pressedimgtab = new Image("res/hangar/tabhighlighted2.png");
		Image normalimgbuybutton = new Image("res/hangar/button2.png");
		
		//Image buybuttonnormal = new Image("res/hangar/")
		
		try {
			weapontab = new Tab("", obserx+(11*scalefactor), obsery+(48*scalefactor), 15*scalefactor, 8*scalefactor, normalimgtab, null, pressedimgtab, null);
			tablist.add(weapontab);
			
			basiclaserbutton = new BuyButton("Basic Laser", 20, obserx+(14*scalefactor), obsery+(59*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, normalimgbuybutton, null);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		weapontab.update(container);
		
		if(weapontab.getButtonDown()){
			basiclaserbutton.update(container);
		}
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
		
		if(weapontab.getButtonDown()){
			basiclaserbutton.render(g, scalefactor);
		}
	}
	
	@Override
	public int getID() {
		return 3;
	}

}
