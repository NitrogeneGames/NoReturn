package nitrogene.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState{
	String lastLoaded = "";
	/** The music that will be played on load completion */
	/** The sound that will be played on load completion */
	/** The image that will be shown on load completion */
	/** The font that will be rendered on load completion */
	/** The next resource to load */
	private DeferredResource nextResource;
	/** True if we've loaded all the resources and started rendereing */
	private boolean started;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		LoadingList.setDeferredLoading(true);
		new Image("res/bigback3.png");
		new Image("res/explosion2.png");
		new Image("res/GUIportrait.png");
		new Image("res/klaarship5.png");
		System.out.println(LoadingList.get().getRemainingResources());
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		System.out.println("UPDATE");
		if (nextResource != null) {
            try {
                    nextResource.load();
            } catch (Exception e) {
                    throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
            }

            nextResource = null;
    }
		if (LoadingList.get().getRemainingResources() > 0) {
            nextResource = LoadingList.get().getNext();
    } else {
            game.enterState(1);
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
