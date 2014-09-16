using UnityEngine;
using System.Collections;

// This script must be attached to the object to be manipulated

public class GUIcontrols : MonoBehaviour {
	public float  xRotValue = 0.0f,		// Euler angles
				  yRotValue = 0.0f,
				  zRotValue = 0.0f;
	public string xRotString,			// Text input area strings for Euler angles 
				  yRotString,
				  zRotString;		
	public float  guiLeft= 10.0f,		// Left edge and top edge of GUI
				  guiTop = 10.0f;
	public Rect   GUIwindowRect,		// GUI and GUILayout windows
				  GUILayoutWindowRect; 					
	public bool   autoLayout = true; 	// True if auto layout performed using GUILayout

	void OnGUI () {
		// Demonstrate the difference between auto (GUILayout) and manual (GUI) layout.
		// We suggest that you use GUILayout instead of GUI, for easier coding!
		if (autoLayout)
			GUILayoutWindowRect = GUILayout.Window (0, GUILayoutWindowRect, rotWindowGUILayout, "Euler Angles");
		else
			GUIwindowRect = GUI.Window (0, GUIwindowRect, rotWindowGUI, "Euler Angles");
	}

	// Create a 2D GUI window using tedious manual layout, with explicit positions and sizes
	public void rotWindowGUI (int windowID) {
		GUI.Label (new Rect (guiLeft, guiTop + 10, 200, 20), "Rotation order: z, x, y");

		// Layout using horizontal sliders
		GUIUtils.HorizontalSliderAndText (new Rect (guiLeft, guiTop+10+25,        200, 70),
		                                  "X rot (pitch)", 0, 360, ref xRotValue, ref xRotString);
		GUIUtils.HorizontalSliderAndText (new Rect (guiLeft, guiTop+10+25+50,     200, 70),
		                                  "Y rot (yaw)",   0, 360, ref yRotValue, ref yRotString);
		GUIUtils.HorizontalSliderAndText (new Rect (guiLeft, guiTop+10+25+2*(50), 200, 70),
		                                  "Z rot (roll)",  0, 360, ref zRotValue, ref zRotString);
		autoLayout = GUI.Toggle (new Rect (guiLeft, guiTop+10+25+3*(50), 200, 20),
		                         autoLayout, " Auto layout");

//		Alternative layout replaces	horizontal sliders with vertical sliders
//		GUIwindowRect.height = 250;	// Make window slightly higher for vertical sliders
//		GUIUtils.VerticalSliderAndText (new Rect (guiLeft, guiTop+10+25,        60, 200),
//		                                  "X rot (pitch)", 0, 360, ref xRotValue, ref xRotString);
//		GUIUtils.VerticalSliderAndText (new Rect (guiLeft+70, guiTop+10+25,     60, 200),
//		                                  "Y rot (yaw)",   0, 360, ref yRotValue, ref yRotString);
//		GUIUtils.VerticalSliderAndText (new Rect (guiLeft+2*(70), guiTop+10+25, 60, 200),
//		                                  "Z rot (roll)",  0, 360, ref zRotValue, ref zRotString);
//		autoLayout = GUI.Toggle (new Rect (guiLeft, guiTop+10+25+175, 200, 20),	// Make button slightly lower
//		                         autoLayout, " Auto layout");					// for vertical sliders

		GUI.DragWindow(new Rect(0, 0, Screen.width, Screen.height)); // Make it draggable over the entire display
	}

	// Create a 2D GUI window using auto layout, with automatically computed positions and sizes
	public void rotWindowGUILayout (int windowID) {
		GUILayout.Label ("Rotation order: z, x, y");

		// Layout using horizontal sliders
		GUILayoutUtils.HorizontalSliderAndText ("X rot (pitch)", 0, 360, ref xRotValue, ref xRotString);
		GUILayoutUtils.HorizontalSliderAndText ("Y rot (yaw)",   0, 360, ref yRotValue, ref yRotString);
		GUILayoutUtils.HorizontalSliderAndText ("Z rot (roll)",  0, 360, ref zRotValue, ref zRotString);

//		Alternative layout replaces horizontal sliders with vertical sliders arranged in a horizontal group
//		GUILayout.BeginHorizontal ();
//		GUILayoutUtils.VerticalSliderAndText ("X rot (pitch)", 0, 360, ref xRotValue, ref xRotString);
//		GUILayoutUtils.VerticalSliderAndText ("Y rot (yaw)",   0, 360, ref yRotValue, ref yRotString);
//		GUILayoutUtils.VerticalSliderAndText ("Z rot (roll)",  0, 360, ref zRotValue, ref zRotString);
//		GUILayout.EndHorizontal ();

		autoLayout = GUILayout.Toggle (autoLayout, " Auto layout");
		GUI.DragWindow (new Rect(0, 0, Screen.width, Screen.height)); // Make it draggable over the entire display
	}

	// Use this for initialization
	void Start () {
		GUIwindowRect = new Rect (guiLeft, guiTop, 210, 240);	// Rectangle for manual layout must have explicit sizes
		GUILayoutWindowRect = new Rect (guiLeft, guiTop, 0, 0);	// Rectangle for auto layout will have sizes computed
		xRotString = xRotValue.ToString ("#0.0");
		yRotString = yRotValue.ToString ("#0.0");
		zRotString = zRotValue.ToString ("#0.0");
	}
	
	// Update is called once per frame
	void Update () {
		transform.localEulerAngles = new Vector3 (xRotValue, yRotValue, zRotValue);	// Set orientation relative to parent
		// transform.eulerAngles = new Vector3 (xRotValue, yRotValue, zRotValue);	// Set orientation relative to world

	}


}
