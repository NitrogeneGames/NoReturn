package nitrogene.world;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import nitrogene.core.GameObject;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;
import nitrogene.gui.Sprite;
import nitrogene.util.AngledMovement;
import nitrogene.util.Direction;
import nitrogene.util.Movement;

public class Asteroid extends GameObject{
	private float endx, endy;
	private Direction direction;
	public Asteroid(World world, float startx, float starty, float endx, float endy) throws SlickException {
		super(world, startx, starty);
		setDefaultMovement("normal");
		this.endx = endx;
		this.endy = endy;
	}
	
	public void load(String img, Direction movement, float scalefactor, World map){
		this.scalefactor = scalefactor;
		this.map = map;
		this.mainimg = new Sprite(img);
		boundbox = GlobalInformation.getHitbox(mainimg.getResourceReference());
		if(boundbox == null){
			//Resources.log(img.getResourceReference() + "   :   " + "WARNING, NEEDS HITBOX REFERENCE");
			float[] m = {0,0,1,1,2,2};
			boundbox = new Polygon(m);
		}
		boundbox = boundbox.transform(Transform.createScaleTransform(scalefactor, scalefactor));
		
		init(mainimg.getImage().getWidth(), mainimg.getImage().getHeight());
		newboundbox = new Polygon(boundbox.getPoints());
		//newboundbox = boundbox;
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
		centerOfRotation = new Point(getSprite().getImage().getCenterOfRotationX(),getSprite().getImage().getCenterOfRotationY());
	}
	
	public void update(int delta){
		float carryx = this.getX();
		float carryy = this.getY();
		Point p = this.getCenterOfRotation();
		this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform((float)Math.toRadians(rotation),p.getX(),p.getY())));
		this.setX(carryx);
		this.setY(carryy);
		this.getSprite().setRotation(this.getRotation());
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
		this.destroyed = true;
		this.removeBoundbox();
		this.map.getAsteroids().remove(this);
	}

}
