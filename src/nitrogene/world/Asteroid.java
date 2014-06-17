package nitrogene.world;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import nitrogene.core.GlobalInformation;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AngledMovement;
import nitrogene.util.Direction;
import nitrogene.util.Movement;

public class Asteroid extends PhysicalObject{
	private float endx, endy;
	private Direction direction;

	public Asteroid(float startx, float starty, float endx, float endy) throws SlickException {
		super(startx, starty);
		setDefaultMovement("normal");
		this.endx = endx;
		this.endy = endy;
	}
	
	public void load(Image img, Direction movement, float scalefactor, ArenaMap map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = img;
		boundbox = GlobalInformation.getImageData().get(img.getResourceReference());
		if(boundbox == null){
			System.out.println(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		init(img.getWidth(), img.getHeight());
		newboundbox = new Polygon();
		newboundbox = boundbox;
		this.setX(tempx);
		this.setY(tempy);
		rotationalconstant=0;
		angledmovement = new AngledMovement(map.getUpbound(), map.getDownbound(), map.getLeftbound(), map.getRightbound());
		this.movement = new Movement();
		this.movement.Toggle(movement);
		this.direction = movement;
		
		Random rand1 = new Random();
		this.rotationalconstant = rand1.nextInt(8)+2;
		this.rotation = rand1.nextInt(360);
	}
	
	public void update(int delta){
		if(this.direction==Direction.FORWARD && this.getX() > endx){
			destroy();
		} else if(this.direction==Direction.UPWARD && this.getY() < endy){
			destroy();
		} else if(this.direction==Direction.BACKWARD && this.getX() < endx){
			destroy();
		} else if(this.direction==Direction.DOWNWARD && this.getY() > endy){
			destroy();
		} else{
		move(7, delta);
		}
	}
	
	private void destroy(){
		this.map.getAsteroids().remove(this);
	}

}
