package nitrogene.core;

import org.newdawn.slick.state.StateBasedGame;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	private static ZoomEnum zoom;
	private static float zoomwidth;
	private static float zoomheight;
	private static StateBasedGame instance;
	
	
	public static void setZoom(ZoomEnum z){
		zoom = z;
	}
	public static ZoomEnum getZoom(){
		return zoom;
	}
	
	public static void setZoomWindow(int width, int height){
		zoomwidth = (float) (zoom.inverse*width);
		zoomheight = (float) (zoom.inverse*height);
	}
	
	public static float getZoomWidth(){
		return zoomwidth;
	}
	public static float getZoomHeight(){
		return zoomheight;
	}
	public static float scale(float i) {
		return (float) (i*getZoom().inverse);
	}
	public static int scale(int i) {
		return (int) (i*getZoom().inverse);
	}
}
