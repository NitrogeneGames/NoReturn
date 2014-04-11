package nitrogene.util;

public enum ZoomEnum {
	NORMAL (1,1),
	LARGE (0.8f, 1.25f),
	MAP (0.5f, 2f);
	
	public float scale;
	public float inverse;
	ZoomEnum(float scale, float inverse){
		this.scale = scale;
		this.inverse = inverse;
	}
}
