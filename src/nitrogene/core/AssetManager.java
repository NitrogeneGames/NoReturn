package nitrogene.core;

import java.util.HashMap;

import org.newdawn.slick.Sound;

//Static class used to store an instance of all images/sounds loaded

//All media is loaded to the variable "instance" in loadingstate

//After the game is loaded, AssetManager.get() is used to get the loaded media


public class AssetManager extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;
	private static AssetManager instance = null;
	
	public static AssetManager get(){
		//Called by classes to get all loaded media
		if(instance == null){
			instance = new AssetManager();
		}
		return instance;
	}
	public static void playSound(String key, float a, float b) {
		//Takes a media string as argument and plays the sound stored in "instance", if it exists
		try {
			((Sound) AssetManager.get().get(key)).play(a, b);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
