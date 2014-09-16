using UnityEngine;
using System.Collections;
using System.Linq;

public class ARSelect : MonoBehaviour {
	private GameObject selectedObject;
	private RaycastHit hit;
	//private GameObject beam;
	private GameObject detector;
	private GameObject orienter;
	private Color selectColor;
	private Transform clone;
	GameObject[] Vobjects;

	void Start () {
		selectedObject = null;
		selectColor = Color.cyan;
		//beam = GameObject.Find("Beam");
		detector = GameObject.Find ("Cylinder");
		orienter = GameObject.Find ("Sphere");
		Vobjects = GameObject.FindGameObjectsWithTag("Vobject");
	}
	
	// Update is called once per frame
	void Update () {
		detector.transform.localPosition = new Vector3 (0F, 0F, 1.636898F);
		detector.transform.localRotation = new Quaternion(0f,0f,0f,0f);
		orienter.transform.localPosition = new Vector3 (0F, 0F, .65F);
//		if(selectedObject)
//			GameObject.Find("Cylinder").GetComponent<MeshRenderer>().enabled =false;
//		else
//			GameObject.Find("Cylinder").GetComponent<MeshRenderer>().enabled = true;

//		if(selectedObject)
//			if(!selectedObject.Equals(hit.collider.gameObject))
//				selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(false);
//				selectedObject = hit.collider.gameObject;
//				selectedObject.transform.renderer.material.color = selectColor;
//				selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(true);
//				foreach(GameObject thisObject in Vobjects)
//					if(!thisObject.Equals(hit.collider.gameObject))
//						selectedObject.transform.renderer.material.color = selectColor;

//		beam = GameObject.Find("Beam");
//		beam.transform.position = transform.position;
//		beam.transform.rotation = transform.rotation;
//		beam.GetComponent<LineRenderer>().SetPosition (0, transform.position);
//		beam.GetComponent<LineRenderer>().SetPosition (1, new Vector3(transform.position.x+100,transform.position.y,transform.position.z));
//		if(GUIUtility.hotControl == 0){
//			Ray selectionRay = new Ray(transform.position, transform.forward);
//			if (Physics.Raycast (selectionRay, out hit) && Vobjects.Contains(hit.collider.gameObject)){
//				if(selectedObject)
//					if(!selectedObject.Equals(hit.collider.gameObject))
//						selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(false);
//				selectedObject = hit.collider.gameObject;
//				selectedObject.transform.renderer.material.color = selectColor;
//				selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(true);
//				foreach(GameObject thisObject in Vobjects)
//					if(!thisObject.Equals(hit.collider.gameObject))
//						selectedObject.transform.renderer.material.color = Color.gray;
//				//Debug.Log(" You just hit " + selectedObject.name);
//			}
//
//			else{
//				//deselect();
//			}
//		}
	}

	public void deselect(bool destroy){
		selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(false);
		if (destroy)
			Destroy(selectedObject);
		Destroy(clone);
		selectedObject = null;
		GameObject.Find("Cylinder").GetComponent<MeshRenderer>().enabled = true;
		Vobjects = GameObject.FindGameObjectsWithTag("Vobject");
		foreach(GameObject thisObject in Vobjects)
			thisObject.transform.renderer.material.color = Color.gray;
	}

	public void setCollide(GameObject col){
		if(!selectedObject){
			selectedObject = col;
			selectedObject.transform.renderer.material.color = selectColor;
			selectedObject.transform.parent.gameObject.GetComponent<GUI_ARBodyControl>().isSelected(true);
			GameObject.Find("Cylinder").GetComponent<MeshRenderer>().enabled =false;

//			clone = Instantiate(selectedObject,new Vector3(0, 0, 0), Quaternion.identity) as Transform;
//			clone.parent = GameObject.Find("Workspace").transform;
//			clone.localScale = new Vector3(.005f,.005f,.005f);
//			clone.localPosition = Vector3.zero;
		}
	}

}