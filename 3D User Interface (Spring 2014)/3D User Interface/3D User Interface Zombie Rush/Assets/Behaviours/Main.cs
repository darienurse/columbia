using UnityEngine;
using System.Collections;
using System;

public class Main : MonoBehaviour {
	Transform cameraTransform;
	GameObject cam;
	Slayer slayer;
	Transform build1;
	Transform build2;
	Transform build3;
	Spawn spawn1;
	Spawn spawn2;
	Spawn spawn3;
	Transform atRisk;
	GUIStyle style;
	AudioSource audio;
	LineRenderer lineRenderer;
	public float MAX_DAMAGE = 20f;
	bool gameStart = true;
	bool gaming = false;
	bool gameOver = false;
	Rect	GUILayoutWindowRect; 	
	GUIStyle labelStyle;
	float guiWidth, guiheight,guiLeft,guiTop;
	
	
	// Use this for initialization
	void Awake(){
		Time.timeScale = 0;
	}
	void Start () {
		guiWidth = Screen.width / 7;
		guiheight = Screen.height / 5;
		guiLeft = Screen.width - guiWidth*1.5f;
		guiTop = 0.0f;
		GUILayoutWindowRect = new Rect (guiLeft, guiTop, guiWidth, guiheight);	// Rectangle for auto layout will have sizes computed
		
		
		this.lineRenderer = GameObject.Find ("Main").GetComponent<LineRenderer>();
		this.lineRenderer.material.SetColor("_Color", Color.red);
		this.lineRenderer.material.SetColor("_SpecColor", Color.red);
		this.lineRenderer.material.SetColor("_Emission", Color.red);
		this.lineRenderer.material.SetColor("_ReflectColor", Color.red);
		this.cameraTransform = Camera.main.transform;
		cam = GameObject.CreatePrimitive(PrimitiveType.Cube);
		cam.transform.position = Camera.main.transform.position-(new Vector3(0,17,0));
		cam.name = "cam";
		cam.gameObject.transform.parent = GameObject.Find ("ARCamera").transform;
		cam.renderer.material.color = Color.black;
		cam.transform.localScale = cam.transform.localScale / 2;
		cam.collider.enabled = false;
		this.slayer = GameObject.Find ("Cylinder").GetComponent<Slayer>();
		
		var build1Object = GameObject.Find ("Building_1");
		var build2Object = GameObject.Find ("Building_2");
		var build3Object = GameObject.Find ("Building_3");
		build1 = build1Object.transform;
		build2 = build2Object.transform;
		build3 = build3Object.transform;
		this.spawn1 = build1Object.GetComponent<Spawn>();
		this.spawn2 = build2Object.GetComponent<Spawn>();
		this.spawn3 = build3Object.GetComponent<Spawn>();
		
		style= new GUIStyle();
		style.fontSize=50;
		style.normal.textColor = Color.blue;
		atRisk =build1;
		StartCoroutine(reportDanger());
		this.audio = GameObject.Find ("GunAudioObject").audio;
		
		//GameObject.CreatePrimitive (PrimitiveType.Cube).transform.parent = GameObject.Find ("ImageTarget").transform;
	}
	
	// Update is called once per frame
	void Update () {
		
		if (this.spawn1.damage >= MAX_DAMAGE || this.spawn2.damage > MAX_DAMAGE || this.spawn3.damage > MAX_DAMAGE){
			gameOver = true;
			this.lineRenderer.enabled = false;
			Destroy (cam);
		}
		else{
			var startLinePos = this.cam.transform.position;
			startLinePos.z -= 2;
			this.lineRenderer.SetPosition (0, startLinePos);
			this.lineRenderer.SetPosition(1, this.atRisk.position);

			if (Input.GetKeyDown (KeyCode.Mouse0)) {
				audio.Play ();

				Ray ray = new Ray(Camera.main.transform.position, Camera.main.transform.forward);
				RaycastHit hit;
				if (Physics.Raycast (ray, out hit)) {
					if (hit.collider.name.Equals ("cam")) {
						Debug.Log ("hit the cam!");
					}
					//Destroy(hit.collider.gameObject);
					var collided = hit.collider.gameObject.GetComponent<Move> ();
					if (collided != null && collided.isAlive) {
						collided.isAlive = false;
						slayer.incrementScore();
						collided.destroyCountdown ();
					}
					else {
						Debug.Log ("hit something else! " + hit.collider.name);
					}
				}
				else {
					Debug.Log ("miss!");
				}
			}
		}
	}

	IEnumerator reportDanger(){ 
		while (true){
			int d1 = this.spawn1.damage;
			int d2 = this.spawn2.damage;
			int d3 = this.spawn3.damage;
			int [] damage = {d1, d2, d3};
			Array.Sort (damage);
			int max = damage[2];
			if(d1==max)
				atRisk = build1;
			else if (d2==max)
				atRisk=build2;
			else
				atRisk = build3;
			//Debug.Log (atRisk);
			yield return new WaitForSeconds(3);
		}
		
		
	}
	
	void OnGUI(){
		GUILayoutWindowRect = GUILayout.Window (1, GUILayoutWindowRect, rotWindowGUILayout, "Game Status");
	}
	
	void rotWindowGUILayout (int windowID) {
		style.normal.textColor = Color.white;
		if(gameStart ==  true){
			GUILayoutWindowRect = new Rect (0.0f, guiTop, guiWidth, guiheight);
			String msg = "ZOMBIE RUSH";
			style.fontSize = 70;
			GUILayout.Label (msg,style);
			msg = "How to play\n1. Align the crosshair with zombies and tap to shoot.";
			msg += "\n2. Hit the zombies with the beating stick.";
			msg += "\n3. Place the turret to help you defend your buildings.";
			msg += "\n4. Keep an eye out for the indicator pointing to the building with the most damage.";
			msg += "\n\nSee how many points you can get before a building takes 20 damage!";
			style.fontSize = 30;
			GUILayout.Label (msg,style); 
			if (GUILayout.Button("Start", GUILayout.Height(100))){
				Time.timeScale = 1;
				gameStart = false;
			}
		}
		else if (gameOver == true){
			GUILayoutWindowRect = new Rect (0.0f, guiTop, guiWidth, guiheight);	// Rectangle for auto layout will have sizes computed
			String msg = "Game Over! \nYour score: " + this.slayer.getScore().ToString();
			style.fontSize = 70;
			GUILayout.Label (msg,style);
			if (GUILayout.Button("Play Again?",GUILayout.Height(100)))
				Application.LoadLevel (0); 
		}
		
		else{
			if(!gaming){
				GUILayoutWindowRect = new Rect (guiLeft, guiTop, guiWidth, guiheight);
				gaming = true;
			}
			GUILayoutWindowRect.x = Screen.width - guiWidth*1.5f;
			string score1 = "Score: "+this.slayer.getScore().ToString();
			GUILayout.Label (score1,style);
			
			string damage1 = "Damage 1: "+this.spawn1.damage;
			style.normal.textColor = whatColorIsGuiText(this.spawn1.damage);
			GUILayout.Label (damage1,style);
			
			string damage2 = "Damage 2: "+this.spawn2.damage;
			style.normal.textColor = whatColorIsGuiText(this.spawn2.damage);
			GUILayout.Label (damage2,style);
			
			string damage3 = "Damage 3: "+this.spawn3.damage;
			style.normal.textColor = whatColorIsGuiText(this.spawn3.damage);
			GUILayout.Label (damage3,style);
			
			style.normal.textColor = Color.blue;
			//GUI.Label (new Rect(0,120,100,100),damage1,style);
		}
	}
	
	Color whatColorIsGuiText(int damage){
		if (damage/MAX_DAMAGE > 0.75){
			return Color.red;
		}
		
		if (damage/MAX_DAMAGE >0.25){
			return Color.yellow;
		}
		
		return Color.green;
	}
}
