package nitrogene.core;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState{
	String lastLoaded = "";

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println(LoadingList.get().getRemainingResources());
		//LoadingList.get().setDeferredLoading(true);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (LoadingList.get().getRemainingResources() > 0) { 
	        DeferredResource nextResource = LoadingList.get().getNext(); 
	        try {
				nextResource.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        lastLoaded = nextResource.getDescription();
	    } else { 
	    	game.enterState(1);
	        // loading is complete, do normal updat ehere      
	    }
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Loaded: "+lastLoaded, 100, 100); 
	}
	
	public int getID() {
		return 0;
	}

}
