using UnityEngine;
using System.Collections;

public class Spawn : MonoBehaviour {
	Transform spawnzone;
	//Transform imageTarget;
	public int damage;
	void SpawnCube() {
		var newCube = GameObject.CreatePrimitive (PrimitiveType.Cube);
		//newCube.transform.parent = imageTarget;
		newCube.transform.localScale = new Vector3 (5F, 5F, 5F);
		var xstart = Random.Range (-20f, 20f);
		Vector3 start = new Vector3(xstart,0,0);
		start = spawnzone.TransformPoint (start);
		//start=imageTarget.InverseTransformPoint(start);
		start[1]=2.56f;
		newCube.transform.localPosition = start;
		newCube.renderer.material = (Material)Resources.Load ("ZombieCharacterPack/Materials/zombie_lowres");
		var rigid = newCube.AddComponent<Rigidbody>();
		rigid.freezeRotation = true;
		newCube.AddComponent ("Move");
		newCube.tag = "zombie";
		Vector3 dest = transform.localPosition;
		dest[1]=.074f;
		//dest = imageTarget.TransformPoint (dest);
		newCube.GetComponent<Move>().destination = dest;
	}

	void Start() {
		damage=0;
		spawnzone = transform.FindChild("Spawn_Zone");
		//imageTarget = transform.parent;
		StartCoroutine(StartCubeSpawning());
	}

	IEnumerator StartCubeSpawning(){
		while(true){
			yield return new WaitForSeconds(Random.Range(2F, 10F));
			SpawnCube();
		}
	}
	void OnCollisionEnter(Collision col){
		string name = col.gameObject.name;
		if(name.Equals("Cube")&&col.gameObject.GetComponent<Move>().isAlive){
			damage++;
			Destroy (col.gameObject);
		}
	}
	
}