using UnityEngine;
using System.Collections;
using System.Linq;

public class GUI_ARCamControl : MonoBehaviour {
	public float  guiLeft= 10.0f,		// Left edge and top edge of GUI
				  guiTop = 10.0f;
	public Rect   GUILayoutWindowRect; 	
	public GUIStyle labelStyle;

	public string[] allModelNames = new string[]{"Insect", "Penguin", "Octopus", "Dragon"};
	public GameObject[] allModels;
	public int modelInt = -1;
	public int planetInt = 0;
	public int previousModeInt;
	public int previousModelInt;

	void OnGUI () {
		GUILayoutWindowRect = GUILayout.Window (1, GUILayoutWindowRect, rotWindowGUILayout, "Main GUI");
	}

	public void rotWindowGUILayout (int windowID) {
		labelStyle.fontSize = 15;
		labelStyle.normal.textColor = Color.white;
		GUILayout.Label ("Create an Object", labelStyle);
		modelInt= GUILayout.SelectionGrid(modelInt, allModelNames, 4,GUILayout.Height(Screen.height/10));
		//GUI.DragWindow (new Rect(0, 0, Screen.width, Screen.height)); // Make it draggable over the entire display
	}
	

	void Start () {
		allModels = GameObject.FindGameObjectsWithTag("Vobject");

	}
	
	// Update is called once per frame
	void Update () {
		if (modelInt != -1) {
			Transform current = GameObject.Find (allModelNames [modelInt]).transform;
			Transform clone = Instantiate(current,new Vector3(0, 0, 0), Quaternion.identity) as Transform;
			clone.parent = GameObject.Find("GroundTarget").transform;
			clone.localScale = new Vector3(.005f,.005f,.005f);
			modelInt = -1;
		}
	}

		
}