package nitrogene.util;

import nitrogene.collision.Vector;

public class Movement {
	protected boolean[] toggle;
	protected float[] diracceleration;
	
	public Movement(){
		toggle = new boolean[5];
		diracceleration = new float[5];
	}
	
	/*
	public Movement(int upbound, int downbound, int leftbound, int rightbound){
		toggle = new boolean[5];
		diracceleration = new float[5];
		
		this.upbound = upbound;
		this.downbound = downbound;
		this.leftbound = leftbound;
		this.rightbound = rightbound;
		this.rotangle = 0;
	}
	*/
	
	public void Toggle(Direction direction){
		if(direction == Direction.FORWARD){
			toggle[1] = !toggle[1];
		}
		if(direction == Direction.BACKWARD){
			toggle[2] = !toggle[2];
		}
		if(direction == Direction.UPWARD){
			toggle[3] = !toggle[3];
		}
		if(direction == Direction.DOWNWARD){
			toggle[4] = !toggle[4];
		}
		
		//rules
		if(toggle[2] && toggle[1]){ 
			toggle[2] = false;
			toggle[1] = false;
		}
		if(toggle[3]  && toggle[4]){ 
			toggle[3] = false;
			toggle[4] = false;
		}
	}
	
	public void changeAccelerator(Direction direction, boolean b){
		if(direction == Direction.FORWARD){
			toggle[1] = b;
		}
		if(direction == Direction.BACKWARD){
			toggle[2] = b;
		}
		if(direction == Direction.UPWARD){
			toggle[3] = b;
		}
		if(direction == Direction.DOWNWARD){
			toggle[4] = b;
		}
		
		if(toggle[2] && toggle[1]){
			toggle[2] = false;
			toggle[1] = false;
		}
		if(toggle[3]  && toggle[4]){ 
			toggle[3] = false;
			toggle[4] = false;
		}
	}
	
	//linear acceleration: 0 to 5 in increments of 0.1

	public void Accelerate(Vector location, int delta){
		/*
		float DELTACON = delta/1000f;
		
		float time = this.getDx()/(0.1f*(delta/5f));
		float speed = (this.getDx() * 20 * DELTACON);
		float distance = (speed*time) + ((1/2) * (speed/time) * time * time);
		
		float ltime = this.getDy()/(0.1f*(delta/5f));
		float lspeed = (this.getDy() * 20 * DELTACON);
		float ldistance = (lspeed*ltime) + ((1/2) * (lspeed/ltime) * ltime * ltime);
		*/
		
		if(toggle[1] && diracceleration[1] < 20f) diracceleration[1] += 0.05f*delta/5f;
		else if(!toggle[1] && diracceleration[1] > 0f) diracceleration[1] -= 0.05f*delta/5f;
		else if(!toggle[1]) diracceleration[1] = 0f;
		
		if(toggle[2] && diracceleration[2] < 20f) diracceleration[2] += 0.05f*delta/5f;
		else if(!toggle[2] && diracceleration[2] > 0f) diracceleration[2] -= 0.05f*delta/5f;
		else if(!toggle[2]) diracceleration[2] = 0f;
		
		if(toggle[4] && diracceleration[4] < 20f) diracceleration[4] += 0.05f*delta/5f;
		else if(!toggle[4] && diracceleration[4] > 0f) diracceleration[4] -= 0.005f*delta/5f;
		else if(!toggle[4]) diracceleration[4] = 0f;
		
		if(toggle[3] && diracceleration[3] < 20f) diracceleration[3] += 0.05f*delta/5f;
		else if(!toggle[3] && diracceleration[3] > 0f) diracceleration[3] -= 0.05f*delta/5f;
		else if(!toggle[3]) diracceleration[3] = 0f;
		
		/*
		if (location.x > rightbound - distance){
			//BringBack(Direction.BACKWARD, delta);
		} else {
			if(toggle[1] && diracceleration[1] < 20f) diracceleration[1] += 0.05f*delta/5f;
			else if(!toggle[1] && diracceleration[1] > 0f) diracceleration[1] -= 0.05f*delta/5f;
			else if(!toggle[1]) diracceleration[1] = 0f;
		}
		
		if (location.x < leftbound + distance){
			//BringBack(Direction.FORWARD, delta);
		} else{
			if(toggle[2] && diracceleration[2] < 20f) diracceleration[2] += 0.05f*delta/5f;
			else if(!toggle[2] && diracceleration[2] > 0f) diracceleration[2] -= 0.05f*delta/5f;
			else if(!toggle[2]) diracceleration[2] = 0f;
		}
		*/
		
	}
	
	public void BringBack(Direction direction, int delta){
		switch(direction){
		case FORWARD: toggle[1] = false;
			if(diracceleration[1] > 0f) diracceleration[1] -= 0.05f*delta/5f;
			else if(diracceleration[1] < 0f) diracceleration[1] = 0f;
			break;
		case BACKWARD: toggle[2] = false;
			if(diracceleration[2] > 0f) diracceleration[2] -= 0.05f*delta/5f;
			else if(diracceleration[2] < 0f) diracceleration[2] = 0f;
			break;
		default:
			break;
		}
	}
	
	public void Break(int delta){
	for(int e = 1; e < 5; e++){
		if(toggle[e]) {
			//toggle[e] = false;
		}
		if(diracceleration[e] > 0f){
			diracceleration[e] -= .1f*delta/5f;
		}
		if(diracceleration[e] <= 0f) diracceleration[e] = 0f;
	}
	}
	
	public float getDx(){
		return diracceleration[1] - diracceleration[2];
	}
	
	public float getDy(){
		return diracceleration[4] - diracceleration[3];
	}
	
	public boolean getToggle(Direction direction)
	{
		if(direction == Direction.FORWARD){
			return toggle[1];
		}
		if(direction == Direction.BACKWARD){
			return toggle[2];
		}
		if(direction == Direction.UPWARD){
			return toggle[3];
		}
		if(direction == Direction.DOWNWARD){
			return toggle[4];
		}
		return false;
	}
	
	public float getRotationAngle() {return 0;}
}
