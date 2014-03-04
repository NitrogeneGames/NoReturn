package nitrogene.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class ImageBase {
	private static HashMap<Image, ArrayList<int[]>> c;
	
	private static ArrayList<int[]> pixelize(Image s) {
		ArrayList<int[]> pixels = new ArrayList<int[]>();
		for(int x = 0; x < s.getHeight(); x++) {
			for(int y = 0; y < s.getWidth(); y++) {
				Color c = s.getColor(x, y);
				if(!(c.a == 0f)) {
					pixels.add(new int[]{x,y});				
				}
			}
		}
		return pixels;
	}
	public static void registerImage(Image i) {
		c.put(i, pixelize(i));
	}
	public static ArrayList<int[]> getPixels(Image s) {
		return c.get(s);
	}
	public static boolean contains(Image s) {
		return c.containsKey(s);
	}
}
