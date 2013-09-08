package nitrogene.util;

public class Movement {
	private boolean[] toggle;
	private double[] dirspeed;
	private double[] diracceleration;
	
	public Movement(){
		toggle = new boolean[5];
		dirspeed = new double[5];
		diracceleration = new double[5];
	}
	
	public void Toggle(Direction direction){
		if(direction == Direction.UP){
			toggle[1] = !toggle[1];
		}
		if(direction == Direction.DOWN){
			toggle[2] = !toggle[2];
		}
		if(direction == Direction.LEFT){
			toggle[3] = !toggle[3];
		}
		if(direction == Direction.RIGHT){
			toggle[4] = !toggle[4];
		}
		
		//rules
		if(toggle[2] == true && toggle[1] == true){ 
			toggle[2] = false;
			toggle[1] = false;
		}
		if(toggle[3] == true && toggle[4] == true){ 
			toggle[3] = false;
			toggle[4] = false;
		}
	}
	
	//linear acceleration: 0 to 5 in increments of 0.1
	public void Accelerate(){
		if(toggle[1] == true && diracceleration[1] < 20) diracceleration[1] += 0.05;
		if(toggle[1] == false && diracceleration[1] > 0) diracceleration[1] -= 0.05;
		if(toggle[2] == true && diracceleration[2] < 20) diracceleration[2] += 0.05;
		if(toggle[2] == false && diracceleration[2] > 0) diracceleration[2] -= 0.05;
		if(toggle[3] == true && diracceleration[3] < 20) diracceleration[3] += 0.05;
		if(toggle[3] == false && diracceleration[3] > 0) diracceleration[3] -= 0.05;
		if(toggle[4] == true && diracceleration[4] < 20) diracceleration[4] += 0.05;
		if(toggle[4] == false && diracceleration[4] > 0) diracceleration[4] -= 0.05;
	}
	
	public void Break(){
	for(int e = 1; e < 5; e++){
		if(toggle[e] == true) {
			toggle[e] = false;
		}
		if(diracceleration[e] > 0){
			diracceleration[e] -= .1;
		}
		if(diracceleration[e] < 0 && toggle[e] == true) diracceleration[e] = 0;
	}
	}
	
	public double getDx(){
		return diracceleration[4] - diracceleration[3];           
	}
	
	
	public double getDy(){
		return diracceleration[2] - diracceleration[1];
	}
}
