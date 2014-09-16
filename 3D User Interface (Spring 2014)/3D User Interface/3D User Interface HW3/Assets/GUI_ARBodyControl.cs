using UnityEngine;
using System.Collections;


public class GUI_ARBodyControl : MonoBehaviour {


	public string[] modeStrings = new string[]{"Translation", "Rotation", "Scaling"};
	public string[] optStrings = new string[]{"Deselect", "Switch Coordinate System", "Delete"};

	public float  guiLeft= 10.0f,		// Left edge and top edge of GUI
			   	  guiTop = 10.0f;
	public Rect   GUILayoutWindowRect; 	// GUILayout window	
	public GUIStyle labelStyle;
	public int modeInt = 1;
	public int optInt = -1;
	public Vector3 posOffset = new Vector3 (0f, 0f, 0f);
	public Vector3 rotOffset = new Vector3(0f,0f,0f);
	public bool selected; 	// True if object is selected

	
	void OnGUI () {
		if (selected) {
			GUILayoutWindowRect = GUILayout.Window (0, GUILayoutWindowRect, rotWindowGUILayout, transform.gameObject.name + "'s GUI"); //Build GUI
		}
	}

	// Create a 2D GUI window using auto layout, with automatically computed positions and sizes
	public void rotWindowGUILayout (int windowID) {
		GUILayout.Label ("Manipulations",labelStyle);
		modeInt = GUILayout.SelectionGrid(modeInt, modeStrings, 3,GUILayout.Height(Screen.height/10));
		GUILayout.Label ("Other options",labelStyle);
		optInt = GUILayout.SelectionGrid(optInt, optStrings, 3,GUILayout.Height(Screen.height/10));
		GUI.DragWindow(new Rect(0, 100, Screen.width, Screen.height)); // Make it draggable over the entire display
	}

	public void isSelected(bool s){
		selected = s;
		if (true) {
			posOffset = GameObject.Find ("Sphere").transform.position - transform.position;
			GameObject.Find ("Sphere").transform.rotation = transform.rotation;
		}
	}


	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update (){
		if (selected){
			if (optInt == 0)
				GameObject.Find("Toolbar").GetComponent<ARSelect>().deselect(false);
			else if (optInt == 1);
			else if (optInt == 2){
				GameObject.Find("Toolbar").GetComponent<ARSelect>().deselect(true);
			}

			if(modeInt == 0)
				transform.position = GameObject.Find("Sphere").transform.position - posOffset;
			else if (modeInt == 1)
				transform.rotation = GameObject.Find("Sphere").transform.rotation;
			else if(modeInt == 2);
		}
	}
}