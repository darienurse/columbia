using UnityEngine;
using System.Collections;

using UnityEngine;
using System.Collections;

public class ZombieSpawn : MonoBehaviour {
	Transform spawnzone;
	//Transform imageTarget;
	public int damage;
	void SpawnCube() {
		var newCube = (GameObject)Instantiate(Resources.Load ("ZombieCharacterPack/zombie_lowres"));
		//newCube.AddComponent<Rigidbody>();
		//newCube.AddComponent<MeshCollider>();
		newCube.transform.localScale += new Vector3(6F, 6F, 6F);

		var xstart = Random.Range (-20f, 20f);
		Vector3 start = new Vector3(xstart,200,0);
		start = spawnzone.TransformPoint (start);
		//start=imageTarget.InverseTransformPoint(start);
		start[1]=2.56f;
		newCube.transform.localPosition = start;
		
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
			SpawnCube();
			yield return new WaitForSeconds(Random.Range(1F, 4F));
		}
	}
	void OnCollisionEnter(Collision col){
		string name = col.gameObject.name;
		if(col.gameObject.tag.Equals("zombie")&&GameObject.Find (name).GetComponent<Move>().isAlive){
			damage++;
			Destroy (col.gameObject);
		}
	}
	
}