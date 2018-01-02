package nitrogene.util;

public class AngledMovement extends Movement{
	private float rotangle = 0;
	private int rotspeed = 3;
	private float maxForwardSpeed = 20f;
	private float maxTurnSpeed = 1f;
	public AngledMovement(int upbound, int downbound, int leftbound, int rightbound, float startangle, int rotspeed){
		toggle = new boolean[5];
		diracceleration = new float[5];
		
		this.rotangle = startangle;
		this.rotspeed = rotspeed;
	}
	
	public AngledMovement(int upbound, int downbound, int leftbound, int rightbound){
		toggle = new boolean[5];
		diracceleration = new float[5];
		
		this.rotangle = 0;
	}

	@Override
	public void Toggle(Direction direction){
		if(direction == Direction.FORWARD){
			toggle[1] = !toggle[1];
		}
		if(direction == Direction.BACKWARD){
			toggle[2] = !toggle[2];
		}
		if(direction == Direction.UPPERANGLE){
			toggle[3] = !toggle[3];
		}
		if(direction == Direction.UNDERANGLE){
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
	
	@Override
	public void changeAccelerator(Direction direction, boolean b){
		if(direction == Direction.FORWARD){
			toggle[1] = b;
		}
		if(direction == Direction.BACKWARD){
			toggle[2] = b;
		}
		if(direction == Direction.UPPERANGLE){
			toggle[3] = b;
		}
		if(direction == Direction.UNDERANGLE){
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

	@Override
	public void Accelerate(Vector location, int delta){
		float decel_const = 1f;
		/*
		float DELTACON = delta/1000f;
		
		float time = this.getDx()/(0.1f*(delta/5f));
		float speed = (this.getDx() * 20 * DELTACON);
		float distance = (speed*time) + ((1/2) * (speed/time) * time * time);
		
		float ltime = this.getDy()/(0.1f*(delta/5f));
		float lspeed = (this.getDy() * 20 * DELTACON);
		float ldistance = (lspeed*ltime) + ((1/2) * (lspeed/ltime) * ltime * ltime);
		*/

		if(toggle[1] && diracceleration[1] < maxForwardSpeed) diracceleration[1] += delta*dirAccel;
		else if(!toggle[1] && diracceleration[1] > 0f) diracceleration[1] -= decel_const*delta*dirAccel;
		else if(!toggle[1]) diracceleration[1] = 0f;
		
		if(toggle[2] && diracceleration[2] < maxForwardSpeed) diracceleration[2] += delta*dirAccel;
		else if(!toggle[2] && diracceleration[2] > 0f) diracceleration[2] -= decel_const*delta*dirAccel;
		else if(!toggle[2]) diracceleration[2] = 0f;
		
		if(toggle[4] && diracceleration[4] < maxTurnSpeed) diracceleration[4] += delta*angleAccel;
		else if(!toggle[4] && diracceleration[4] > 0f) diracceleration[4] -= decel_const*delta*angleAccel;
		else if(!toggle[4]) diracceleration[4] = 0f;
		
		if(toggle[3] && diracceleration[3] < maxTurnSpeed) diracceleration[3] += delta*angleAccel;
		else if(!toggle[3] && diracceleration[3] > 0f) diracceleration[3] -= decel_const*delta*angleAccel;
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
		
		rotangle += ((diracceleration[4] - diracceleration[3])*rotspeed*0.07f)*(delta/5f);
	}
	
	@Override
	public float getDx(){
		//return diracceleration[1] - diracceleration[2]; //DIS WAS THE HYPOTENUSE SILLY
		return (float) (Math.cos(Math.toRadians(rotangle))*getHypotenuse());
	}
	
	public float getHypotenuse() {
		return diracceleration[1] - diracceleration[2];
	}
	
	@Override
	public float getDy(){
		//return (float) (Math.tan(Math.toRadians(rotangle))*this.getDx()); STILL WORKS AFTER UPDATING getDx
		return (float) (Math.sin(Math.toRadians(rotangle))*getHypotenuse()); //WAY CLEANER
	}
	
	@Override
	public boolean getToggle(Direction direction)
	{
		if(direction == Direction.FORWARD){
			return toggle[1];
		}
		if(direction == Direction.BACKWARD){
			return toggle[2];
		}
		if(direction == Direction.UPPERANGLE){
			return toggle[3];
		}
		if(direction == Direction.UNDERANGLE){
			return toggle[4];
		}
		return false;
	}
	public void forceStop() {
		diracceleration[1] = 0;
		diracceleration[2] = 0;
		diracceleration[3] = 0;
		diracceleration[4] = 0;
		if(getToggle(Direction.FORWARD)) Toggle(Direction.FORWARD);
		if(getToggle(Direction.BACKWARD)) Toggle(Direction.BACKWARD);
		if(getToggle(Direction.UPPERANGLE)) Toggle(Direction.UPPERANGLE);
		if(getToggle(Direction.UNDERANGLE)) Toggle(Direction.UNDERANGLE);
	}
	@Override
	public float getRotationAngle(){
		return rotangle;
	}
}
