package nitrogene.core;


import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class ShipState extends BasicGameState{

	private int width,height;
	private int scalefactor, obserx, obsery;
	private Image backgroundimg;
	private Color backgroundcolor;
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	
	private Button startButton, hangarButton, menuButton, minusButton, plusButton;
	
	public ShipState(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundimg = new Image("res/hangar/shipselectionfinal.png");
		backgroundimg.setFilter(Image.FILTER_NEAREST);
		backgroundcolor = new Color(91, 91, 91);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(backgroundimg.getWidth()*scalefactor/2);
		obsery = (height/2)-(backgroundimg.getHeight()*scalefactor/2);
		
		//button init
		Image startButtonImage = new Image("res/hangar/startbuttonhangar.png");
		Image hangarButtonImage = new Image("res/hangar/startbuttonhangar.png");
		Image menuButtonImage = new Image("res/hangar/startbuttonhangar.png");
		Image minusButtonImage = new Image("res/hangar/minusbutton.png");
		Image plusButtonImage = new Image("res/hangar/plusbutton.png");
		
		try {
			startButton = new Button("Start", obserx+(125*scalefactor), obsery+(106*scalefactor), 15*scalefactor, 7*scalefactor, startButtonImage, null, null, null);
			hangarButton = new Button("Design", obserx+(27*scalefactor), obsery+(106*scalefactor), 15*scalefactor, 7*scalefactor, hangarButtonImage, null, null, null);
			menuButton = new Button("Menu", obserx+(10*scalefactor), obsery+(106*scalefactor), 15*scalefactor, 7*scalefactor, menuButtonImage, null, null, null);
			minusButton = new Button("", obserx+(80*scalefactor), obsery+(106*scalefactor), 10*scalefactor, 9*scalefactor, minusButtonImage, null, null, null);
			plusButton = new Button("", obserx+(91*scalefactor), obsery+(106*scalefactor), 10*scalefactor, 9*scalefactor, plusButtonImage, null, null, null);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		try {
			for(int i = 0; i < GlobalInformation.getCraftData().size(); i++) {
				buttonList.add(new Button(GlobalInformation.getCraftData().get(i).name, this.width/2-150, 100+i*30, 300, 60));
			}
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			for(int i = 0; i < buttonList.size(); i++) {
				buttonList.get(i).enter(25);
			}
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		startButton.update(container);
		hangarButton.update(container);
		menuButton.update(container);
		minusButton.update(container);
		plusButton.update(container);
		
		for(int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).update(container);
			if(buttonList.get(i).isClicked()){
				GlobalInformation.setStartingWeapons(GlobalInformation.getCraftData().get(i).weapons);
				game.enterState(2);
			}
		}
		

	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(backgroundcolor);
		backgroundimg.draw(obserx, obsery, scalefactor);
		for(int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).render(g);
		}
		
		startButton.render(g);
		hangarButton.render(g);
		menuButton.render(g);
		minusButton.render(g);
		plusButton.render(g);
	}

	public int getID() {
		return 5;
	}

}