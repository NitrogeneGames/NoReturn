package nitrogene.gui;

import org.newdawn.slick.Image;

public class Sprite {
	private Image image;
	private boolean animated = false;
	private AnimationImage animation = null;
	public Sprite(Image i) {
		this.image = i;
	}
	public Sprite(Image i, AnimationImage a) {
		this.image = i;
		animated = true;
		this.animation = a;
	}
	public Sprite(AnimationImage a) {
		this.image = a.getImage(0);
		animated = true;
		this.animation = a;
	}
	public AnimationImage getAnimation() {
		return animation;
	}
	public boolean isAnimated() {
		return animated;
	}
	public void setAnimated(boolean b) {
		animated = b;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image i) {
		this.image = i;
	}
	public void setRotation(float rotation) {
		if(animated) {
			for(int i = 0; i < animation.getFrameCount(); i++) {
				System.out.println("Setting rotation of image " + i + " to " + rotation);
				animation.getImage(i).setRotation(rotation);
				//animation.get
			}
		} else {
			this.getImage().setRotation(rotation);
		}
	}
	public void rotate(float rotation) {
		if(animated) {
			for(int i = 0; i < animation.getFrameCount(); i++) {
				animation.getImage(i).rotate(rotation);
			}
		} else {
			this.getImage().rotate(rotation);
		}
	}
	public void setCenterOfRotation(float x, float y) {
		if(animated) {
			for(int i = 0; i < animation.getFrameCount(); i++) {
				animation.getImage(i).setCenterOfRotation(x, y);
			}
		} else {
			this.getImage().setCenterOfRotation(x, y);
		}
	}
	public void draw(float x, float y) {
		if(animated) {
			animation.draw(x, y);
		} else {
			image.draw(x,y);
		}
	}
	public void drawCentered(float x, float y) {
		if(animated) {
			animation.drawCentered(x, y);
		} else {
			image.drawCentered(x,y);
		}
	}
	public void draw(float x, float y, float scale) {
		if(animated) {
			animation.draw(x, y, scale);
		} else {
			image.draw(x,y,scale);
		}
	}
	public String getResourceReference() {
		return image.getResourceReference();
	}
}
