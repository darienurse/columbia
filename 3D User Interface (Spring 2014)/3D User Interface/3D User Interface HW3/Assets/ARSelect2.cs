using UnityEngine;
using System.Collections;
using System.Linq;

public class ARSelect2 : MonoBehaviour {
	private GameObject selectedObject;
	private RaycastHit hit;
	private LineRenderer beam;
	private GameObject detector;
	private Color selectColor;

	void Start () {

	}
	void OnCollisionEnter(Collision col){
		if (col.gameObject.name != "Plane") {
			selectedObject = col.gameObject;
			transform.parent.GetComponent<ARSelect> ().setCollide (col.gameObject);
			Debug.Log ("You just collided with " + selectedObject.name);
		}
	}

	void Update () {
	
	}
}
