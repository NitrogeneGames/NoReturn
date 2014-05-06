package nitrogene.util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum EnumHull {
	NORMAL("res/klaarship6.png");
	
	private String himage;
	public Image getImage() {
		try {
			return new Image(himage);
		} catch (SlickException e) {
			return null;
		}
	}
	EnumHull(String i) {
		himage = i;
	}
}
