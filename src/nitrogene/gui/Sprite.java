package nitrogene.gui;

import org.newdawn.slick.Image;

import nitrogene.core.AssetManager;
import nitrogene.core.Resources;

public class Sprite {
	private Image image;
	private boolean animated = false;
	private AnimationImage animation = null;
	public Sprite(String s) {
		if(AssetManager.get().containsKey(s)) {
			if(AssetManager.get().get(s).getClass() == Image.class) {
				this.image = ((Image) AssetManager.get().get(s)).copy();
				animated = false;
			} else if(AssetManager.get().get(s).getClass() == AnimationImage.class) {
				this.animation = ((AnimationImage) AssetManager.get().get(s)).copy();
				this.image = animation.getImage(0);
				animated = true;				
			} else {
				Resources.log("no image found for sprite with key: " + s);
				this.image = null;	
			}
		} else {
			Resources.log("no image found for sprite with key: " + s);
			this.image = null;
		}
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
				animation.getImage(i).setRotation(rotation);
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
