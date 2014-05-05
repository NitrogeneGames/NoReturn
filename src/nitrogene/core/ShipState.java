package nitrogene.core;


import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class ShipState extends BasicGameState{

	private int width,height;
	private Image backgroundimg;
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	
	public ShipState(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundimg = new Image("res/hangar/background.png");
		//button init

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
		for(int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).render(g);
		}
	}

	public int getID() {
		return 5;
	}

}