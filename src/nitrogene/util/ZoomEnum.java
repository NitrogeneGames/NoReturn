package nitrogene.util;

public enum ZoomEnum {
	NORMAL (1,1),
	LARGE (0.8, 1.25),
	MAP (0.5, 2);
	
	public double scale;
	public double inverse;
	ZoomEnum(double scale, double inverse){
		this.scale = scale;
		this.inverse = inverse;
	}
}
