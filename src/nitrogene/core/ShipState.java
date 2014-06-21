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
	private Color backgroundcolor;
	private ArrayList<ArrayList<Button>> buttonList;
	private int currentTab;
	
	private Button startButton, hangarButton, menuButton, minusButton, plusButton;
	
	public ShipState(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		buttonList = new ArrayList<ArrayList<Button>>();
		currentTab = 0;
		backgroundcolor = new Color(91, 91, 91);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(150*scalefactor/2);
		obsery = (height/2)-(120*scalefactor/2);
		
		//button init
		
		try {
			startButton = new Button("Start", obserx+(120*scalefactor), obsery+(104*scalefactor), 20*scalefactor, 9*scalefactor, null);
			hangarButton = new Button("Design", obserx+(22*scalefactor), obsery+(104*scalefactor), 20*scalefactor, 9*scalefactor, null);
			menuButton = new Button("Main Menu", obserx+(10*scalefactor), obsery+(104*scalefactor), 10*scalefactor, 9*scalefactor, null);
			minusButton = new Button("", obserx+(97*scalefactor), obsery+(104*scalefactor), 10*scalefactor, 9*scalefactor, null);
			plusButton = new Button("", obserx+(108*scalefactor), obsery+(104*scalefactor), 10*scalefactor, 9*scalefactor, null);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		try {
			for(int e = 0; e < Math.ceil(GlobalInformation.getCraftData().size()/6d); e++){
				ArrayList<Button> templist = new ArrayList<Button>();
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+0).name, this.width/2-150, 150+e*80*1, 300, 60, null));
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+1).name, this.width/2-150, 150+e*80*2, 300, 60, null));
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+2).name, this.width/2-150, 150+e*80*3, 300, 60, null));
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+3).name, this.width/2-150, 150+e*80*4, 300, 60, null));
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+4).name, this.width/2-150, 150+e*80*5, 300, 60, null));
				templist.add(new Button(GlobalInformation.getCraftData().get(e*6+5).name, this.width/2-150, 150+e*80*6, 300, 60, null));
				buttonList.add(templist);
			}
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		
		for(int i = 0; i < buttonList.size(); i++){
			ArrayList<Button> blist = buttonList.get(i);
			for(int e = 0; e < blist.size(); e++){
				Button b = blist.get(e);
				if(b.isClicked()){
					GlobalInformation.setStartingWeapons(GlobalInformation.getCraftData().get(e*6+i).weapons);
					game.enterState(2);
				}
			}
		}
		
		if(startButton.isClicked()){
			
		} else if(hangarButton.isClicked()){
			game.enterState(3);
		} else if(menuButton.isClicked()){
			game.enterState(1);
		}

	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(backgroundcolor);
		Image backgroundimg = (Image) AssetManager.get().get("shipselection");
		backgroundimg.draw(obserx, obsery, scalefactor);
		ArrayList<Button> finalblist = buttonList.get(currentTab);
 		for(int i = 0; i < finalblist.size(); i++) {
			finalblist.get(i).render(g, (Image)AssetManager.get().get("shipbutton"), (Image)AssetManager.get().get("shipbuttonhighlighted"), null);
		}
		
		startButton.render(g, (Image)AssetManager.get().get("startbutton"), null, null);
		hangarButton.render(g, (Image)AssetManager.get().get("startbutton"), null, null);
		menuButton.render(g, (Image)AssetManager.get().get("menubutton"), null, null);
		minusButton.render(g, (Image)AssetManager.get().get("minusbutton"), null, null);
		plusButton.render(g, (Image)AssetManager.get().get("plusbutton"), null, null);
	}

	public int getID() {
		return 5;
	}

}