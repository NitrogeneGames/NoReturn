package nitrogene.core;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	private static ZoomEnum zoom;
	private static int zoomwidth;
	private static int zoomheight;
	
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
		zoomwidth = (int) (zoom.inverse*width);
		zoomheight = (int) (zoom.inverse*height);
	}
	
	public static int getZoomWidth(){
		return zoomwidth;
	}
	public static int getZoomHeight(){
		return zoomheight;
	}
}
