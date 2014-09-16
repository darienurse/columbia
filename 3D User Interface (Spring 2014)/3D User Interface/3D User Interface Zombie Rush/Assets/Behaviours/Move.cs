using UnityEngine;
using System.Collections;

public class Move : MonoBehaviour {
	public float z;
	public bool isAlive;
	public Vector3 destination;
	AudioSource scream;

	// Use this for initialization
	void Start () {
		z = 6;
		//this.rigidbody.velocity = new Vector3(0,0,z);
		isAlive = true;
		this.scream = GameObject.Find("ScreamAudioObject").audio;
	}
	
	// Update is called once per frame
	void Update () {

		if (this.isAlive) {
			this.transform.LookAt(destination);
			this.transform.Translate (Vector3.forward * 5 * Time.deltaTime);
		}
		/*if (this.transform.localPosition [2] > 4) {
			Destroy (this.gameObject);
			//TODO: improve for 2 player damage
			GameObject.Find ("Cylinder").GetComponent<Slayer>().incDamage();
		}*/


	}
	/*void OnCollisionEnter(Collision col){
		string name = col.gameObject.name;
		if(name.Equals("Building_1")||name.Equals("Building_2")||name.Equals("Building_3"))
	}*/

	public void destroyCountdown(){
		this.rigidbody.freezeRotation = false;
		this.renderer.material.color = Color.magenta;
		Invoke ("destroyZombie", 3);
		this.scream
			.Play();
	}
	
	void destroyZombie(){
		Destroy (this.gameObject);
	}
}
