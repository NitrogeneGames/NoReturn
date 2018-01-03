package nitrogene.core;

import nitrogene.gui.HybridDisplay;
import nitrogene.slick.NitroAppGameContainer;
import nitrogene.slick.NitroScalableGame;
import nitrogene.util.AppData;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sun.glass.ui.Application;

public class SpaceGame extends StateBasedGame{

	public static String version = "Alpha 0.1.2";
	public static int SCRwidth;
	public static int SCRheight;
	public SpaceGame(String title) {
		super("No Return");
		SCRwidth = 1366;
		SCRheight = 768;
	}

	public static void main(String[] args) throws SlickException, LWJGLException{
		
		NitroScalableGame game = new NitroScalableGame(new SpaceGame("Space Game"), 1366, 768, false);
		Resources.scaleGameInstance = game;
		NitroAppGameContainer app = new NitroAppGameContainer(game);
		
		Resources.appInstance = app;
		
		//SCRheight = app.getScreenHeight();
		//SCRwidth = app.getScreenWidth();
		
		Display.setDisplayMode(new DisplayMode(SCRwidth, SCRheight));
		//app.setDisplayMode(SCRwidth, SCRheight, false);
		

		app.setUpdateOnlyWhenVisible(true);
		Display.setResizable(true);
		
		
		boolean jFrameVersion = true;
		
		
		if(jFrameVersion) {
			
			JFrame frame = new JFrame("No Return: " + version);
			try {
				frame.setIconImage(ImageIO.read(new File("res/icon.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			frame.setSize(SCRwidth, SCRheight);
			HybridDisplay.frameInstance = frame;
			Canvas canvas = new Canvas();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(canvas);
			frame.setVisible(true);
			frame.setLocation(20, 20);
			frame.setExtendedState(6);
			frame.addWindowListener(new WindowListener(){
	            public void windowClosing(WindowEvent e){
	            	AppData.saveRez();
	            	app.exit();
	            	app.destroy();
	            }
	
				@Override
				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
	
				public void windowClosed(WindowEvent arg0) {
					System.exit(0);
				}
	
				@Override
				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	        });
			try {
			    Display.setParent(canvas);
			    Display.create();
			    
			} catch (LWJGLException e) {
			    e.printStackTrace();
			}
		}
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		GlobalInformation.init(SCRwidth, SCRheight);
		AppData.runInit();
		
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