package nitrogene.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;

public class ImageBase {
	private static HashMap<Image, ArrayList<float[]>> c= new HashMap<Image, ArrayList<float[]>>();
	
	public static ArrayList<float[]> pixelize(Image s) {
		ArrayList<float[]> pixels = new ArrayList<float[]>();
		for(int x = 0; x < s.getWidth(); x++) {
			for(int y = 0; y < s.getHeight(); y++) {
				/*
				Color c = s.getColor(x,y);
				if(!(c.a == 0f)) {
					pixels.add(new int[]{x,y});				
				}
				*/
				if(s.getColor(x, y).getAlpha() != 0.00f){
					pixels.add(new float[]{x,y});	
				}
			}
		}
		return pixels;
	}
}
