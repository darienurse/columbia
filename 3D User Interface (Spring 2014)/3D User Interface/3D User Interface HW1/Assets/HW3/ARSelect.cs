using UnityEngine;
using System.Collections;
using System.Linq;

public class ARSelect : MonoBehaviour {
	private GameObject selectedObject;
	private RaycastHit hit;
	private Color selectColor;
	GameObject[] Vobjects;

	void Start () {
		selectColor = Color.green;
		Vobjects = GameObject.FindGameObjectsWithTag("Vobject");
		selectedObject = null;
	}
	
	// Update is called once per frame
	void Update () {
		Debug.DrawRay (transform.position, transform.up*100, Color.cyan);
		if(GUIUtility.hotControl == 0){
			Ray selectionRay = new Ray(transform.position, transform.up);
			if (Physics.Raycast (selectionRay, out hit) && Vobjects.Contains(hit.collider.gameObject)){
				if(selectedObject)
					if(!selectedObject.Equals(hit.collider.gameObject))
						selectedObject.GetComponent<GUI_ARBodyControl>().isSelected(false);
				selectedObject = hit.collider.gameObject;
				selectedObject.transform.GetChild(0).renderer.material.color = selectColor;
				selectedObject.GetComponent<GUI_ARBodyControl>().isSelected(true);
				foreach(GameObject thisObject in Vobjects)
					if(!thisObject.Equals(hit.collider.gameObject))
						selectedObject.transform.GetChild(0).renderer.material.color = Color.gray;
				Debug.Log(" You just hit " + selectedObject.name);
			}

			else{
				deselect();
			}
		}
	}

	public void deselect(){
		if(selectedObject){
			selectedObject.GetComponent<GUI_ARBodyControl>().isSelected(false);
			selectedObject = null;
		}
		foreach(GameObject thisObject in Vobjects)
			thisObject.transform.GetChild(0).renderer.material.color = Color.gray;
	}

	GameObject[] allObjectsInTagList (string[] list) {
		GameObject[] tags = new GameObject[0];
		foreach (string s in list)
			tags = tags.Concat(GameObject.FindGameObjectsWithTag(s)).ToArray();
		return tags;
	}

}