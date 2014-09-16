using UnityEngine;
using System.Collections;

public class Projectile : MonoBehaviour {
	float speed = 30F;
	float range = 30F;
	float distance = 0;
	// Update is called once per frame
	void Update () {
		transform.Translate(Vector3.forward * Time.deltaTime * speed);
		distance = distance + Time.deltaTime * speed;
		if (distance >= range){
			Destroy(gameObject);
		}
	}

	void OnTriggerEnter(Collider collision){
		if (collision.gameObject.tag == "zombie")
		{

			var collided = collision.gameObject.GetComponent<Move>();
			if (collided != null && collided.isAlive){
				collided.isAlive = false;
				int score = GameObject.Find("Cylinder").GetComponent<Slayer>().score;
				score = score+1;
				collided.destroyCountdown ();
				
			}
			
			//Destroy projectile
			Destroy(gameObject);
		}
	}
}
