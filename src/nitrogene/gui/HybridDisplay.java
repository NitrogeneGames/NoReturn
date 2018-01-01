package nitrogene.gui;

import javax.swing.JFrame;

public class HybridDisplay {
	public static JFrame frameInstance;
	public static void set(int width, int height) {
		set(width, height, frameInstance.getExtendedState());
	}
	
	public static void set(int width, int height, int state) {
		frameInstance.setSize(width, height);
		frameInstance.setExtendedState(state);
	}
	
}
