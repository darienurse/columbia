using UnityEngine;
using System.Collections;

public class ShipCollide : MonoBehaviour {
	public bool hit = false;
	void OnTriggerEnter(Collider coll) {
		Debug.Log ("Collider: " +coll.gameObject.name);
		//if(shipObj.collider.gameObject != null){
		transform.position = GameObject.Find ("Checkpoint").transform.position;
		//transform.localEulerAngles = new Vector3;
		hit = true;
	}

	void Start(){
		hit = false;
	}
	public bool getHit(){

		return hit;
	}
	public void setHit(bool change){
	 	hit=change;
	}
}
