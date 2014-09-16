using UnityEngine;
using System.Collections;

//Orientation(axis rotation x, y, z), Direction(clockwise/counter clockwise), rotate speed and orbit speed(zero to preset value) 

public class GUI_ARBodyControl : MonoBehaviour {

	public Quaternion orientation; //holds initial object orientation 
	Vector3 rotationAxis = Vector3.up; //default rotation about y-axis

	//Hold the index of the selected button from the Selection grid . Defaults to rotating about y-axis
	public string[] axisStrings = new string[]{"Rotate about x-axis", "Rotate about y-axis", "Rotate about z-axis"};
	

	public float  guiLeft= 10.0f,		// Left edge and top edge of GUI
			   	  guiTop = 10.0f;
	public Rect   GUILayoutWindowRect; 	// GUILayout window	
	public GUIStyle labelStyle;
	public int axisInt = 0; 
	public bool  selected; 	// True if object is selected

	
	void OnGUI () {
		if (selected) {
			GUILayoutWindowRect = GUILayout.Window (0, GUILayoutWindowRect, rotWindowGUILayout, transform.gameObject.name + "'s GUI"); //Build GUI
		}
	}

	// Create a 2D GUI window using auto layout, with automatically computed positions and sizes
	public void rotWindowGUILayout (int windowID) {

		GUI.DragWindow (new Rect(0, 0, Screen.width, Screen.height)); // Make it draggable over the entire display
	}

	public void isSelected(bool s){
		selected = s;
	}


	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
	}
}