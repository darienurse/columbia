using UnityEngine;
using System.Collections;
using System.Linq;

public class Select : MonoBehaviour {
	private GameObject selectedObject;
	private RaycastHit hit;
	public Shader shader1;
	public Shader shader2;
	GameObject[] allObjects;
	string[] tags = {"Sun","Planet","Satillite"};
	// Use this for initialization
	void Start () {
		selectedObject = null;
		shader1 = Shader.Find("Self-Illumin/Diffuse");
		shader2 = Shader.Find("Transparent/Diffuse");
		allObjects = allObjectsInTagList(tags);
			//GameObject.FindGameObjectsWithTag("Planet");
	}
	
	// Update is called once per frame
	void Update () {
		if(Input.GetMouseButtonDown(0) && GUIUtility.hotControl == 0){
			Ray selectionRay = transform.gameObject.camera.ScreenPointToRay(Input.mousePosition);
			if (Physics.Raycast (selectionRay, out hit) && allObjects.Contains(hit.collider.gameObject)){
				if(selectedObject)
					if(!selectedObject.Equals(hit.collider.gameObject))
						selectedObject.GetComponent<GUIBodyControl>().isSelected(false);
				selectedObject = hit.collider.gameObject;
				selectedObject.renderer.material.shader = shader1;
				selectedObject.GetComponent<GUIBodyControl>().isSelected(true);
				foreach(GameObject thisObject in allObjects)
					if(!thisObject.Equals(hit.collider.gameObject))
						thisObject.renderer.material.shader = shader2;
				Debug.Log(" You just hit " + selectedObject.name);
			}

			else{
				deselect();
			}
		}
	}

	public void deselect(){
		if(selectedObject){
			selectedObject.GetComponent<GUIBodyControl>().isSelected(false);
			selectedObject = null;
		}
		foreach(GameObject thisObject in allObjects)
			thisObject.renderer.material.shader = shader1;
	}

	GameObject[] allObjectsInTagList (string[] list) {
		GameObject[] tags = new GameObject[0];
		foreach (string s in list)
			tags = tags.Concat(GameObject.FindGameObjectsWithTag(s)).ToArray();
		return tags;
	}

}