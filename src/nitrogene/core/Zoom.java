package nitrogene.core;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	private static ZoomEnum zoom;
	private static float zoomwidth;
	private static float zoomheight;
	
	public Zoom(ZoomEnum z){
		zoom = z;
		zoomwidth = 0;
		zoomheight = 0;
	}
	
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
}
