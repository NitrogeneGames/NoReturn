package nitrogene.core;

import nitrogene.collision.AABB;

import org.newdawn.slick.Image;


public class BoxMesh {
	public AABB boundbox;
	public float x = 0, y = 0;
	public Image meshImage = null;
	public int hp = 0;
	public int resistance = 0;
	
	public BoxMesh(float x, float y, Image theimage, int hp, int resistance){
		this.meshImage = theimage;
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.resistance = resistance;
		boundbox = new AABB(meshImage.getWidth(), meshImage.getHeight());
		Update(x,y);
	}
	
	public void Update(float xpos, float ypos){
		boundbox.update(boundbox.getCenter(xpos,ypos));
	}
	
	public void registerHit(int damage){
		
	}
	
	public Image getImage(){
		return meshImage;
	}
}
