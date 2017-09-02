package nitrogene.gui;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class AnimationManager {
	private static ArrayList<Animation> ListAnimation = new ArrayList<Animation>();
	
	public static void addAnimation(Animation anim){
		ListAnimation.add(anim);
		anim.start();
	}
	public static void deleteAnimation(Animation anim){
		ListAnimation.remove(anim);
	}
	public static void deleteAnimation(int index){
		ListAnimation.remove(index);
	}
	public static void updateAnimation(int delta){
		for(int i = 0; i < ListAnimation.size(); i++){
			Animation anim = ListAnimation.get(i);
			anim.update(delta);
			if(anim.isStopped()){
				ListAnimation.remove(anim);
			}
			anim = null;
		}
	}
	public static void renderAnimation(){
		for(int i =0; i < ListAnimation.size(); i ++){
			((AnimationGui) ListAnimation.get(i)).drawCentered();
		}
	}
	
	public static ArrayList<Animation> getAnimation(){
		return ListAnimation;		
	}
	public static void createExplosion(float x, float y, float scale, int duration) throws SlickException {
		addAnimation(new AnimationGui(x, y, scale, duration, "explosionSound", "explosion", 2, 6, 32, 32, true));
	}
}
