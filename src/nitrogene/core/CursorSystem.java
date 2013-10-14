package nitrogene.core;

import org.lwjgl.input.Controller;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CursorSystem {
	private static String controller;
	private static Image redfire, greenfire, yellowfire;
	
	public static void changeCursor(String st){
		controller = st;
	}
	
	public static void init() throws SlickException{
		redfire = new Image("res/cursor/red_cursor_1.png");
		greenfire = new Image("res/cursor/green_cursor_1.png");
		yellowfire = new Image("res/cursor/yellow_cursor_1.png");
		controller = "default";
	}
	
	public static void update(GameContainer cont) throws SlickException{
		switch(controller){
		case "default": cont.setDefaultMouseCursor();
		case "redfire": cont.setMouseCursor(redfire, redfire.getWidth()/2, redfire.getHeight()/2);
		case "yellowfire": cont.setMouseCursor(yellowfire, yellowfire.getWidth()/2, yellowfire.getHeight()/2);
		case "greenfire": cont.setMouseCursor(greenfire, greenfire.getWidth()/2, greenfire.getHeight()/2);
		}
	}
}
