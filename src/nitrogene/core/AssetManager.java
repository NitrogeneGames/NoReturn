package nitrogene.core;

import java.util.HashMap;

import org.newdawn.slick.Sound;

public class AssetManager extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;
	private static AssetManager instance = null;
	
	private AssetManager(){
	}

	public static AssetManager get(){
		if(instance == null){
			instance = new AssetManager();
		}
		return instance;
	}
	public static void playSound(String key, float a, float b) {
		try {
			((Sound) AssetManager.get().get(key)).play(a, b);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
