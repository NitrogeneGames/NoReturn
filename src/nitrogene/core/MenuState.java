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


public class MenuState extends BasicGameState{

	private int width,height;
	private Button quickPlay, campaign, options, hangar, quitgame;
	
	public MenuState(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//button init
		try {
			campaign = new Button("Campaign", 1100, 340, 150, 60);
			quickPlay = new Button("Quick Play", 1100, 400, 150, 60);
			hangar = new Button("Hangar", 1100, 460, 150, 60);
			options = new Button("Options", 1100, 520, 150, 60);
			quitgame = new Button("Quit Game", 1100, 580, 150, 60);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{

	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		quickPlay.update(container);
		campaign.update(container);
		quitgame.update(container);
		hangar.update(container);
		options.update(container);
		
		if(quickPlay.isClicked()){
			game.enterState(5);
		}
		if(hangar.isClicked()){
			game.enterState(3);
		}
		if(options.isClicked()){
			game.enterState(4);
		}
		if(quitgame.isClicked()){
			container.exit();
		}
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Image temp = (Image) AssetManager.get().get("menubackgroundimg");
		Image tempnormal = (Image) AssetManager.get().get("defaultbuttonnormal");
		Image temppressed = (Image) AssetManager.get().get("defaultbuttonpressed");
		Image temphover = (Image) AssetManager.get().get("defaultbuttonhover");
		temp.setFilter(Image.FILTER_NEAREST);
		temp.draw(0,0);
		campaign.render(g, tempnormal, temppressed, temphover);
		quickPlay.render(g, tempnormal, temppressed, temphover);
		hangar.render(g, tempnormal, temppressed, temphover);
		options.render(g, tempnormal, temppressed, temphover);
		quitgame.render(g, tempnormal, temppressed, temphover);
	}

	public int getID() {
		return 1;
	}

}
