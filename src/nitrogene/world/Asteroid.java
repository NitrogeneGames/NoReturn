package nitrogene.world;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Direction;

public class Asteroid extends PhysicalObject{
	private float startx, starty, endx, endy;
	private Direction direction;

	public Asteroid(float startx, float starty, float endx, float endy, Direction movement, float scalefactor, ArenaMap map) throws SlickException {
		super(startx, starty, new Image("res/Asteroid1.png"), 1, map);
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
		this.movement.Toggle(movement);
		this.direction = movement;
		
		Random rand1 = new Random();
		this.rotationalconstant = rand1.nextInt(8)+2;
	}
	
	public void update(int delta){
		if(this.direction==Direction.FORWARD && this.getX() > endx){
			destroy();
		} else if(this.direction==Direction.UPWARD && this.getY() > endy){
			destroy();
		} else if(this.direction==Direction.BACKWARD && this.getX() < endx){
			destroy();
		} else if(this.direction==Direction.DOWNWARD && this.getY() < endx){
			destroy();
		} else{
		move(3, delta);
		System.out.println("ASTEROID  " + this.getX() + "   :   " + this.getY());
		}
	}
	
	private void destroy(){
		this.map.getAsteroids().remove(this);
	}

}
