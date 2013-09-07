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
	private Image backgroundimg;
	Button quickPlay, campaign, options, hangar, quitgame;
	
	public MenuState(int width,int height){
		this.width = width;
		this.height = height;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundimg = new Image("res/menubackground4.png");
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
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		if(quickPlay.isClicked()){
			game.enterState(2);
		}
		if(quitgame.isClicked()){
			container.exit();
		}
		
		quickPlay.update(container);
		campaign.update(container);
		quitgame.update(container);
		hangar.update(container);
		options.update(container);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		backgroundimg.draw(0,0);
		campaign.render(g);
		quickPlay.render(g);
		hangar.render(g);
		options.render(g);
		quitgame.render(g);
	}

	public int getID() {
		return 1;
	}

}
