package nitrogene.core;

import nitrogene.slick.BetterAppGameContainer;
import nitrogene.slick.BetterScalableGame;
import nitrogene.util.AppData;

import java.awt.Canvas;
import java.awt.Frame;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceGame extends StateBasedGame{

	public static int SCRwidth;
	public static int SCRheight;
	public SpaceGame(String title) {
		super("No Return");
		SCRwidth = 1366;
		SCRheight = 768;
	}

	public static void main(String[] args) throws SlickException, LWJGLException{
		BetterScalableGame game = new BetterScalableGame(new SpaceGame("Space Game"), 1366, 768, false);
		Resources.scaleGameInstance = game;
		BetterAppGameContainer app = new BetterAppGameContainer(game);
		Resources.appInstance = app;
		//SCRheight = app.getScreenHeight();
		//SCRwidth = app.getScreenWidth();
		Display.setDisplayMode(new DisplayMode(SCRwidth, SCRheight));
		//app.setDisplayMode(SCRwidth, SCRheight, false);
		GlobalInformation.initHitboxData();
		GlobalInformation.init(SCRwidth, SCRheight);
		AppData.runInit();
		app.setUpdateOnlyWhenVisible(true);
		Display.setResizable(true);
		app.start();
	}
 
 
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new LoadingState()); //0
		//SCRwidth = container.getScreenWidth();
		//SCRheight = container.getScreenHeight();
		this.addState(new MenuState(SCRwidth,SCRheight)); //1
		this.addState(new GameState(SCRwidth,SCRheight)); //2
		this.addState(new HangarState(SCRwidth,SCRheight)); //3
		this.addState(new OptionState(SCRwidth,SCRheight)); //4
		this.addState(new ShipState(SCRwidth,SCRheight)); //5
		this.enterState(0);
	}

}