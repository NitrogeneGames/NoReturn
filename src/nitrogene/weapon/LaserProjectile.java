package nitrogene.weapon;

import nitrogene.core.AssetManager;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Zoom;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Transform;

import java.util.Random;


public class LaserProjectile extends PhysicalObject{
	
	private float startX = 0, startY = 0, desX = 0, desY = 0, speed = 0, dx, dy;
	double mangle = 0;
	private float mmangle, sspeed;
	private int sdamage;
	private int planetdamage;
	private boolean isRotated;
	public LaserLauncher parent;
	private float xconstant, yconstant;
	Sound basicTestLaser;
	
	public LaserProjectile(float startX, float startY, float destinX, float destinY, int accuracy, float speed, int damage, int planetdamage, float rotation, LaserLauncher l) throws SlickException{
		super(startX, startY);
		//Movement class unused
		setDefaultMovement("normal");
		isRotated = false;
		this.startX =startX;
		this.startY =startY;
		this.desX = destinX;
		this.desY = destinY;
		this.parent = l;
		this.sdamage = damage;
		this.sspeed = speed;
		this.planetdamage = planetdamage;
		this.mmangle = rotation;
		if(accuracy!=0){
		desX += randomize(accuracy);
		desY += randomize(accuracy);
		}
		recalculateVector(desX, desY);
		//recalculateAngle(desX, desY);
	}
	@Override
	public void load(Image img, float scalefactor, ArenaMap map) {
		super.load(img, scalefactor, map);
		this.getImage().setRotation(0);
		this.getImage().setCenterOfRotation(parent.getImage().getWidth()/2,parent.getImage().getHeight()/2);
		this.getImage().rotate(mmangle);
		basicTestLaser = (Sound) AssetManager.get().get("laserSound1");
		basicTestLaser.play(1f, GlobalInformation.sfxlevel);
	}
	/*
	public SLaser(float startX, float startY, float destinX, float destinY, int accuracy, float speed, int damage, float size, Image im) throws SlickException{
		super(startX, startY, img.getWidth(), img.getHeight(), img, size, map);
		theimage = im;
		this.startX =startX;
		this.startY =startY;
		this.desX = destinX;
		this.desY = destinY;
		this.sdamage = damage;
		this.sspeed = speed;
		this.scalesize = size;
		this.mmangle = 0;
		location.setLocation(startX,startY);
		if(accuracy!=0){
		desX += randomize(accuracy);
		desY += randomize(accuracy);
		}
		boundbox = new AABB(theimage.getTextureWidth() * size, theimage.getTextureHeight() * size);
		recalculateVector(desX, desY);
		recalculateAngle(desX, desY);
	}
	*/
	
	private void recalculateVector(float desX2, float desY2) {
		float vec = (float)(Math.atan2(desX2 - startX, desY2 - startY));
		//set speed
		speed = sspeed;

		this.dx = (float) Math.sin(vec) * speed;
        this.dy = (float) Math.cos(vec) * speed;
	}
	
	public void recalculateVector(){
		recalculateVector(desX, desY);
	}
	
	@Override
	public Line move(float thrust, int delta){
		float x1 = getX();
		float y1 = getY();
		float mm = delta/1000f;
		float gj = thrust;
		setX(getX()+(gj*mm*dx));
		setY(getY()+(gj*mm*dy));
		
		if(this.scalefactor == 0.7f){ xconstant = 21f; yconstant = 6f;
		}else if(this.scalefactor == 0.6f){ xconstant = 27f; yconstant = 7.5f;
		}else if(this.scalefactor == 0.3f){ xconstant = 70f; yconstant = 26f;
		}else if(this.scalefactor == 1.5f){ xconstant = 4f; yconstant = 2f;}
		xconstant*=scalefactor;
		yconstant*=scalefactor;
		
		float carryx = this.getX();
		float carryy = this.getY();
		this.getBoundbox().setCenterX(this.getCenterX());
		this.getBoundbox().setCenterY(this.getCenterY());
		this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform(
				(float)Math.toRadians(this.getAngle()),xconstant,yconstant)));//25f, 7.5f
		
		this.setX(carryx);
		this.setY(carryy);
		return new Line(x1, y1, getX(), getY());
	}
	
	public float getAngle(){
		return mmangle;
	}
	
	private int randomize(int distance) {
		Random rand = new Random();
		int  n = rand.nextInt(distance * 2) + 1;
		return (int) (n* Zoom.getZoom().scale - distance);
		}
	
	public int getDamage(){
		return sdamage;
	}

	public int getPlanetDamage() {
		return this.planetdamage;
	}
}
