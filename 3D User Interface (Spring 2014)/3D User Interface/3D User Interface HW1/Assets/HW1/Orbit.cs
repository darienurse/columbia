using UnityEngine;
using System.Collections;

public class Orbit : MonoBehaviour {
	public readonly float orbitSpeed  = 20f; //default orbit speed
	readonly Vector3 orbitDirection = Vector3.up; //default orbit is clockwise

	public float curOrbitSpeed;
	Vector3 curOrbitDirection;

	// Use this for initialization
	void Start () {
		curOrbitSpeed = orbitSpeed;
		curOrbitDirection = orbitDirection;
	}
	
	// Update is called once per frame
	void Update () {
		//if(transform.IsChildOf(transform))
		if(transform.parent !=null)
			transform.RotateAround(transform.parent.position, curOrbitDirection, curOrbitSpeed * Time.deltaTime);
	}

	public void ChangeOrbitDirection(Vector3 dir){
		curOrbitDirection = dir;
	}

	public void ChangeOrbitSpeed(float speed){
		curOrbitSpeed = speed;
	}

	public void restoreDefaults(){
		curOrbitSpeed = orbitSpeed;
		curOrbitDirection = orbitDirection;
	}
}
