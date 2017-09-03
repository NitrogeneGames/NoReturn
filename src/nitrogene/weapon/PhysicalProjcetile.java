package nitrogene.weapon;

import nitrogene.core.AssetManager;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Zoom;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.Target;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Transform;

import java.util.Random;


public class PhysicalProjcetile extends PhysicalObject{
	
	private float startX = 0, startY = 0, desX = 0, desY = 0, speed = 0, dx, dy;
	private PhysicalObject target;
	private boolean isTargetingObject = false;
	double mangle = 0;
	private int hp;
	private float mmangle, sspeed;
	private int sdamage;
	private int planetdamage;
	private int shipdamage;
	private boolean homing;
	private float homingAngle;
	private boolean isRotated;
	public LaserLauncher parent;
	private float xconstant, yconstant;
	Sound basicTestLaser;
	
	public PhysicalProjcetile(ProjectileData proj, float startX, float startY, float destinX, float destinY, int accuracy, float rotation, LaserLauncher l) throws SlickException{
		super(startX, startY);
		//Movement class unused
		setDefaultMovement("normal");
		isRotated = false;
		this.startX =startX;
		this.startY =startY;
		this.desX = destinX;
		this.desY = destinY;
		this.parent = l;
		this.sdamage = proj.damage;
		this.sspeed = proj.speed;
		this.planetdamage = proj.planetDamage;
		this.shipdamage = proj.shipDamage;
		this.homing = proj.tracking;
		this.homingAngle = proj.turningAngle;
		this.hp = proj.hp;
		this.mmangle = rotation;
		if(accuracy!=0){
		desX += randomize(accuracy);
		desY += randomize(accuracy);
		}
		recalculateVector(desX, desY);
		//recalculateAngle(desX, desY);
	}
	@Override
	public void load(String img, float scalefactor, ArenaMap map) {
		super.load(img, scalefactor, map);
		this.getSprite().setRotation(0);
		this.getSprite().setCenterOfRotation(parent.getSprite().getImage().getWidth()/2,parent.getSprite().getImage().getHeight()/2);
		this.getSprite().rotate(mmangle);
		basicTestLaser = (Sound) AssetManager.get().get("laserSound1");
		basicTestLaser.play(1f, GlobalInformation.sfxlevel);
	}
	public void rotateTo(float degrees) {
		this.getSprite().setRotation(degrees);
		mmangle = degrees;
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
		if(isTargetingObject) {
			if(target.isDestroyed()) { //gotta make sure target isnt null fist :/
				isTargetingObject = false;
			}
		}
		if(homing && isTargetingObject && homingAngle > 0) {
			float newAngle = 0;
			float adjHomingAngle = mm*homingAngle;
			float currentRotation = this.getAngle();
			float targetRotation = (float)(Math.toDegrees(Math.atan2(target.getRealCenterY() - getY(), target.getRealCenterX() - getX())));
			
			//to test homing to 0,0
			//float targetRotation = (float)(Math.toDegrees(Math.atan2(0 - getY(), 0 - getX())));
			float deltaTheta = Target.getRotation(targetRotation, currentRotation);
			if(Math.abs(deltaTheta) > adjHomingAngle) { //find teh angle to turn
				if(deltaTheta < 0) {
					newAngle = -adjHomingAngle;
				} else {
					newAngle = adjHomingAngle;
				}
			} else {
				newAngle = deltaTheta;
			}
			
			float newVectorAngle = (currentRotation + newAngle);
			this.rotateTo(newVectorAngle);
			this.dx = (float) Math.cos(Math.toRadians(newVectorAngle)) * sspeed;
	        this.dy = (float) Math.sin(Math.toRadians(newVectorAngle)) * sspeed;
			setX(getX()+(gj*mm*dx));
			setY(getY()+(gj*mm*dy));
			
			
			float carryx = this.getX();
			float carryy = this.getY();
			this.getBoundbox().setCenterX(this.getCenterX());
			this.getBoundbox().setCenterY(this.getCenterY());
			this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform(
					(float) Math.toRadians(newVectorAngle),this.getCenterX(),this.getCenterY())));//25f, 7.5f
			
			this.setX(carryx);
			this.setY(carryy);
			return new Line(x1, y1, getX(), getY());
		} else {
		
			setX(getX()+(gj*mm*dx));
			setY(getY()+(gj*mm*dy));
			
			
			float carryx = this.getX();
			float carryy = this.getY();
			this.getBoundbox().setCenterX(this.getCenterX());
			this.getBoundbox().setCenterY(this.getCenterY());
			this.setBoundbox(this.getOriginalBoundbox().transform(Transform.createRotateTransform(
					(float)Math.toRadians(this.getAngle()),this.getCenterX(),this.getCenterY())));//25f, 7.5f
			
			this.setX(carryx);
			this.setY(carryy);
			return new Line(x1, y1, getX(), getY());
		}
	}
	
	public float getAngle(){
		return mmangle;
	}
	
	private int randomize(int distance) {
		Random rand = new Random();
		int  n = rand.nextInt(distance * 2) + 1;
		return (int) (n* Zoom.getZoom() - distance);
		}
	
	public int getDamage(){
		return sdamage;
	}

	public int getPlanetDamage() {
		return this.planetdamage;
	}
	public void setTargetObject(PhysicalObject target) {
		this.target = target;
		isTargetingObject = true;
	}
}
