package nitrogene.objecttree;

public class PhysicalObject {
	protected float x, y, width, height;

	public PhysicalObject(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getwidth(){
		return width;
	}
	public float getheight(){
		return height;
	}
}
