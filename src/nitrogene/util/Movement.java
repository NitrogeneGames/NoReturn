package nitrogene.util;

import nitrogene.collision.Vector;

public class Movement {
	private boolean[] toggle;
	private double[] diracceleration;
	private int [] pixbounds;
	private int downbound, leftbound, upbound, rightbound;
	
	public Movement(int upbound, int downbound, int leftbound, int rightbound){
		toggle = new boolean[5];
		diracceleration = new double[5];
		pixbounds = new int[5];
		
		this.upbound = upbound;
		this.downbound = downbound;
		this.leftbound = leftbound;
		this.rightbound = rightbound;
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

	public void Accelerate(Vector location, int delta, int cc){
		double distance = (rightbound - location.x);
		float DELTACON = delta/5f;
		double accpoints = diracceleration[4]/(0.1*delta/5f);

		double average = ((diracceleration[4])/2);
		double secs = distance/(20*average);
		double slowdowntime = diracceleration[4]/20;

		float speed = (float) (diracceleration[4]*20*(delta/1000f));
		System.out.println(slowdowntime);
		System.out.println(secs);
		
		if(secs <= slowdowntime && diracceleration[4] != 0){
			
			System.out.println("HERE");
			BringBack(Direction.RIGHT, delta);
		} else {
			if(toggle[4] == true && diracceleration[4] < 20) diracceleration[4] += 0.05*delta/5f;
			if(toggle[4] == false && diracceleration[4] > 0) diracceleration[4] -= 0.05*delta/5f;
		}
		
		if (false){
			BringBack(Direction.LEFT, delta);
		} else{
			if(toggle[3] == true && diracceleration[3] < 20) diracceleration[3] += 0.05*delta/5f;
			if(toggle[3] == false && diracceleration[3] > 0) diracceleration[3] -= 0.05*delta/5f;
		}
		
		if (false){
			BringBack(Direction.DOWN, delta);
		} else {
			if(toggle[2] == true && diracceleration[2] < 20) diracceleration[2] += 0.05*delta/5f;
			if(toggle[2] == false && diracceleration[2] > 0) diracceleration[2] -= 0.05*delta/5f;
		}
		
		if (false){
			BringBack(Direction.UP, delta);
		} else{
			if(toggle[1] == true && diracceleration[1] < 20) diracceleration[1] += 0.05*delta/5f;
			if(toggle[1] == false && diracceleration[1] > 0) diracceleration[1] -= 0.05*delta/5f;
		}
		
	}
	
	public void BringBack(Direction direction, int delta){
		switch(direction){
		case UP: toggle[1] = false;
			if(diracceleration[1] > 0) diracceleration[1] -= 0.1*delta/5f;
			else if(diracceleration[1] < 0) diracceleration[1] = 0;
			break;
		case DOWN: toggle[2] = false;
			if(diracceleration[2] > 0) diracceleration[2] -= 0.1*delta/5f;
			else if(diracceleration[2] < 0) diracceleration[2] = 0;
			break;
		case LEFT: toggle[3] = false;
			if(diracceleration[3] > 0) diracceleration[3] -= 0.1*delta/5f;
			else if(diracceleration[3] < 0) diracceleration[3] = 0;
			break;
		case RIGHT: toggle[4] = false;
			if(diracceleration[4] > 0) diracceleration[4] -= 0.1*delta/5f;
			else if(diracceleration[4] < 0) diracceleration[4] = 0;
			break;
		}
	}
	
	public void Break(int delta){
	for(int e = 1; e < 5; e++){
		if(toggle[e] == true) {
			toggle[e] = false;
		}
		if(diracceleration[e] > 0.00){
			diracceleration[e] -= .1*delta/5f;
		}
		if(diracceleration[e] <= 0.00) diracceleration[e] = 0;
	}
	}
	
	public double getDx(){
		return diracceleration[4] - diracceleration[3];           
	}
	
	
	public double getDy(){
		return diracceleration[2] - diracceleration[1];
	}
}
