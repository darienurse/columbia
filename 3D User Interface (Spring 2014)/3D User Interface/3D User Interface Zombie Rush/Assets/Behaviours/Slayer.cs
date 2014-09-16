using UnityEngine;
using System.Collections;

public class Slayer : MonoBehaviour {
	public int score;
	int damage;
	AudioSource audio;


	// Use this for initialization
	void Start () {
		score=0;
		damage=0;
		this.audio = GameObject.Find("SmackAudio").audio;
	}
	
	// Update is called once per frame
	void Update () {
	}

	void OnCollisionEnter(Collision col) {
		var collided = col.gameObject.GetComponent<Move> ();
		if (collided != null) {
			if (collided.isAlive) {
				collided.isAlive = false;
				score=score+1;
				collided.destroyCountdown ();
				audio.Play();
			}
		}
	}

	public int getScore(){
		return score;
	}

	public void incrementScore(){
		score = score + 1;
	}

	public void incDamage(){
		damage++;
	}

	public int getDamage(){
		return damage;
	}
}