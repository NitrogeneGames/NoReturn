package nitrogene.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CursorSystem {
	
	//Class that manages the mouse cursor image/color
	
	private static String controller;
	private static Image redfire, greenfire, yellowfire;
	private static GameContainer cont;
	
	public static void changeCursor(String st){
		//Update the cursor to match the string argument
		controller = st;
		try {
			if(controller == "default") cont.setDefaultMouseCursor();
			if (controller == "redfire") cont.setMouseCursor(redfire, redfire.getWidth()/2, redfire.getHeight()/2);
			if (controller == "yellowfire")  cont.setMouseCursor(yellowfire, yellowfire.getWidth()/2, yellowfire.getHeight()/2);
			//if (controller == "greenfire") cont.setMouseCursor(greenfire, 0, 0);
			//else cont.setDefaultMouseCursor();
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public static void init() throws SlickException{
		redfire = new Image("res/cursor/red_cursor_1.png");
		greenfire = new Image("res/cursor/green_cursor_1.png");
		yellowfire = new Image("res/cursor/yellow_cursor_1.png");
		controller = "default";
	}
	
	public static void update(GameContainer container) throws SlickException{
		cont = container;
		}
	}
