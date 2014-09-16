using UnityEngine;
using System.Collections;

//Orientation(axis rotation x, y, z), Direction(clockwise/counter clockwise), rotate speed and orbit speed(zero to preset value) 

public class GUIBodyControl : MonoBehaviour {
	//Orbit t =  (transform.parent.GetComponent("Orbit") as Orbit).curOrbitSpeed;
	public float orbitSpeed;//  = (transform.parent.GetComponent("Orbit") as Orbit).curOrbitSpeed; //default orbit speed
	public readonly float rotationSpeed = 50.0f; //default rotation speed
	public float curRotationSpeed; //current rotation speed
	public float pausedOrbitSpeed; //hold the orbit speed value prior to a pause
	public float pausedRotationSpeed; // hold the rotation speed value prior ro a pause
	public Quaternion orientation; //holds initial object orientation 
	public bool orbitCounterClockwise = false; //default orbit is clockwise
	Vector3 rotationAxis = Vector3.up; //default rotation about y-axis

	//Hold the index of the selected button from the Selection grid . Defaults to rotating about y-axis
	public string[] axisStrings = new string[]{"Rotate about x-axis", "Rotate about y-axis", "Rotate about z-axis"};

	public string orbitSpeedString,			// Text input area strings speeds
				  rotationSpeedString;		
	
	public float  guiLeft= 10.0f,		// Left edge and top edge of GUI
			   	  guiTop = 10.0f;
	public Rect   GUILayoutWindowRect; 	// GUILayout window	
	public GUIStyle labelStyle;
	public int axisInt = 0; 
	public bool  selected; 	// True if object is selected
	public bool  paused; 	// True if object is paused
	public bool  previousPaused; //Holds the value of paused the the previous frame

	
	void OnGUI () {
		if (selected) {
			GUILayoutWindowRect = GUILayout.Window (0, GUILayoutWindowRect, rotWindowGUILayout, transform.gameObject.name + "'s GUI"); //Build GUI
			transform.parent.GetComponent<Orbit> ().ChangeOrbitSpeed (orbitSpeed); //update orbit speed
			if (orbitCounterClockwise)
					transform.parent.GetComponent<Orbit> ().ChangeOrbitDirection (Vector3.down);
			else
					transform.parent.GetComponent<Orbit> ().ChangeOrbitDirection (Vector3.up);
			switch (axisInt) {
				case 0:
						rotationAxis = Vector3.right;
						break;
				case 1:
						rotationAxis = Vector3.up;
						break;
				case 2:
						rotationAxis = Vector3.forward;
						break;
				default:
						break;
			}
		}
	}

	// Create a 2D GUI window using auto layout, with automatically computed positions and sizes
	public void rotWindowGUILayout (int windowID) {
		labelStyle.fontSize = 15;
		labelStyle.normal.textColor = Color.white;
		// Rotation Parameters
		GUILayout.Label ("Rotation Parameters",labelStyle);
		GUILayoutUtils.HorizontalSliderAndText ("Rotation Speed", 0, 400, ref curRotationSpeed, ref rotationSpeedString);
		axisInt= GUILayout.SelectionGrid(axisInt, axisStrings, 3, GUILayout.Height(Screen.height/10));
		if (transform.gameObject.tag.Equals("Planet")|| transform.gameObject.tag.Equals("Satillite")) {
			GUILayout.Label ("Orbit Parameters", labelStyle);
			GUILayoutUtils.HorizontalSliderAndText ("Orbit Speed", 0, 400, ref orbitSpeed, ref orbitSpeedString);
			orbitCounterClockwise = GUILayout.Toggle (orbitCounterClockwise, "Orbit Counter-Clockwise");
			paused = GUILayout.Toggle (paused, "Pause",GUILayout.Height(Screen.height/10));
		} else
			paused = true;
		GUI.DragWindow (new Rect(0, 0, Screen.width, Screen.height)); // Make it draggable over the entire display
	}

	public void isSelected(bool s){
		selected = s;
		if (!selected) {
			restoreDefaults ();
			restoreDescendants();
		}
	}

	public void restoreDefaults(){
		curRotationSpeed = rotationSpeed;
		transform.parent.GetComponent<Orbit> ().restoreDefaults ();
		transform.rotation = orientation;
		rotationAxis = Vector3.up;
		paused = false;
	}

	public void pause(){
		pausedOrbitSpeed = orbitSpeed;
		pausedRotationSpeed = curRotationSpeed; 
		curRotationSpeed = 0f;
		orbitSpeed = 0f;
		transform.parent.GetComponent<Orbit> ().ChangeOrbitSpeed (orbitSpeed); //update orbit speed
		orbitSpeedString = orbitSpeed.ToString ("#0.0");
		rotationSpeedString = curRotationSpeed.ToString ("#0.0");
	}
	public void unpause(){
		orbitSpeed = pausedOrbitSpeed;
		curRotationSpeed = pausedRotationSpeed; 
		transform.parent.GetComponent<Orbit> ().ChangeOrbitSpeed (orbitSpeed); //update orbit speed
		orbitSpeedString = orbitSpeed.ToString ("#0.0");
		rotationSpeedString = curRotationSpeed.ToString ("#0.0");
	}

	public void restoreDescendants(){
		foreach (Transform orbiter in transform.parent) {
			if (orbiter.gameObject.tag.Equals("Orbiter")) {
				foreach (Transform body in orbiter){
					if (body.gameObject.tag.Equals("Planet")|| body.gameObject.tag.Equals("Satillite")){
						//Debug.Log(" Pausing " + body.gameObject.name);
						body.gameObject.GetComponent<GUIBodyControl>().restoreDefaults();
						body.gameObject.GetComponent<GUIBodyControl>().restoreDescendants();
					}
				}
			}
		}
	}
	public void pauseDescendants(){
		foreach (Transform orbiter in transform.parent) {
			if (orbiter.gameObject.tag.Equals("Orbiter")) {
				foreach (Transform body in orbiter){
					if (body.gameObject.tag.Equals("Planet")|| body.gameObject.tag.Equals("Satillite")){
						//Debug.Log(" Pausing " + body.gameObject.name);
						body.gameObject.GetComponent<GUIBodyControl>().pause();
						body.gameObject.GetComponent<GUIBodyControl>().pauseDescendants();
					}
				}
			}
		}
	}

	public void unpauseDescendants(){
		foreach (Transform orbiter in transform.parent) {
			if (orbiter.gameObject.tag.Equals("Orbiter")) {
				foreach (Transform body in orbiter){
					if (body.gameObject.tag.Equals("Planet")|| body.gameObject.tag.Equals("Satillite")){
						//Debug.Log(" Pausing " + body.gameObject.name);
						body.gameObject.GetComponent<GUIBodyControl>().unpause();
						body.gameObject.GetComponent<GUIBodyControl>().unpauseDescendants();
					}
				}
			}
		}
	}

	// Use this for initialization
	void Start () {
		GUILayoutWindowRect = new Rect (guiLeft, guiTop + Screen.width/4, 0, 0);	// Rectangle for auto layout will have sizes computed
		orbitSpeed  = (transform.parent.GetComponent("Orbit") as Orbit).orbitSpeed;
		orbitSpeedString = orbitSpeed.ToString ("#0.0");
		rotationSpeedString = rotationSpeed.ToString ("#0.0");
		orientation = transform.rotation;
		curRotationSpeed = rotationSpeed;
		pausedOrbitSpeed = orbitSpeed; //Variables for holding paused values
		pausedRotationSpeed = curRotationSpeed;
		selected = false;
		paused = false;
		previousPaused = paused;
	}
	
	// Update is called once per frame
	void Update () {
		if (paused != previousPaused && paused){
			if(!transform.name.Equals("Sun"))
				pause();
			pauseDescendants();
			previousPaused = paused;
		}
		else if(paused != previousPaused && !paused){
			unpause();
			unpauseDescendants();
			previousPaused = paused;
		}
		transform.Rotate (rotationAxis*Time.deltaTime*curRotationSpeed);
	}
}