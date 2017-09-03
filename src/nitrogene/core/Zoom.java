package nitrogene.core;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	//This is the scale, and the inverse from ZoomEnum is 1/scale (start at 1=scale)
	private static float zoom;
	private static float width;
	private static float height;
	
	
	public static void setZoom(float z){
		zoom = z;
	}
	public static float getZoom(){
		return zoom;
	}
	
	public static void setZoomWindow(int width, int height){
		Zoom.width = (float)(width);
		Zoom.height = (float)(height);
	}
	
	public static float getZoomWidth(){
		return Zoom.width*(1/Zoom.zoom);
	}
	public static float getZoomHeight(){
		return Zoom.height*(1/Zoom.zoom);
	}
	public static float scale(float i) {
		return (float) (i*(1/getZoom()));
	}
	public static int scale(int i) {
		return (int) (i*(1/getZoom()));
	}
}
