package nitrogene.core;

import java.util.HashMap;

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
}
