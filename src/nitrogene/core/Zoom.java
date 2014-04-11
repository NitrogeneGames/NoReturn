package nitrogene.core;

import org.newdawn.slick.state.StateBasedGame;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	private static ZoomEnum zoom;
	private static float width;
	private static float height;
	
	
	public static void setZoom(ZoomEnum z){
		zoom = z;
	}
	public static ZoomEnum getZoom(){
		return zoom;
	}
	
	public static void setZoomWindow(int width, int height){
		Zoom.width = (float)(width);
		Zoom.height = (float)(height);
	}
	
	public static float getZoomWidth(){
		return Zoom.width*(Zoom.zoom.inverse);
	}
	public static float getZoomHeight(){
		return Zoom.height*(Zoom.zoom.inverse);
	}
	public static float scale(float i) {
		return (float) (i*getZoom().inverse);
	}
	public static int scale(int i) {
		return (int) (i*getZoom().inverse);
	}
}
