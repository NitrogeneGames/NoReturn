package nitrogene.core;

import nitrogene.util.ZoomEnum;

public class Zoom {
	
	private static ZoomEnum zoom;
	public Zoom(ZoomEnum z){
		zoom = z;
	}
	
	public static void setZoom(ZoomEnum z){
		zoom = z;
	}
	public static ZoomEnum getZoom(){
		return zoom;
	}
}
