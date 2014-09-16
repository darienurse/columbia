using UnityEngine;
using System.Collections;




public class TurretCannon : MonoBehaviour {
	public GameObject projectile;
	public Transform target;
	public Transform[] muzzlePositions;
	public Transform turretBall;

	// Use this for initialization
	void Start () {
		StartCoroutine(StartShooting());
	}

	IEnumerator StartShooting(){
		while(true){
			FireProjectile();
			yield return new WaitForSeconds(0.75F);
		}
	}


	void FireProjectile(){
		Instantiate(projectile, muzzlePositions[0].position, muzzlePositions[0].rotation);
		Instantiate(projectile, muzzlePositions[1].position, muzzlePositions[1].rotation);


	}
}
