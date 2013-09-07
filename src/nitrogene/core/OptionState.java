package nitrogene.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OptionState extends BasicGameState{
	
	private Image skeleton;
	private float x,y = 0;
	
	public OptionState(int width, int height){
		this.x = (float) width / 2;
		this.y = (float) height / 2;
	}
	
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//skeleton = new Image("res/");
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		skeleton.drawCentered(x, y);
	}

	public int getID() {
		return 4;
	}

}
