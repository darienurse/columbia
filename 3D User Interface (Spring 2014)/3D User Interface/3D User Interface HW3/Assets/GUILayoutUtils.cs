using UnityEngine;
using System.Collections;

public class GUILayoutUtils {
	public static int ratio = (int) (Screen.width *0.003); 
	public static int HLabelWidth   = 120*ratio;	// Width of horizontal slider label
	public static int HSliderWidth  = 120*ratio;	// Width of horizontal slider
	public static int HSliderHeight = 20*ratio;	// Height of horizontal slider
	public static int HTextWidth    = 50;	// Width of horizontal slider text
	public static int HTextHeight   = 20;	// Height of horizontal slider text

	public static int VLabelWidth   = 50;	// Width of vertical slider label
	public static int VSliderWidth  = 20;	// Width of vertical slider
	public static int VSliderHeight = 120;	// Height of vertical slider
	public static int VTextWidth    = 50;	// Width of vertical slider text
	public static int VTextHeight   = 20;	// Height of vertical slider text

	/// <summary>
	/// Creates a horizontal slider with text input using GUILayout.
	/// </summary>
	/// <param name="name">Name of slider</param>
	/// <param name="min">Minimum value of slider.</param>
	/// <param name="max">Maximum value of slider.</param>
	/// <param name="inVal">Current slider value.</param>
	/// <param name="inValS">Current text input value.</param>
	public static void HorizontalSliderAndText(string name, float min, float max,
	                                      ref float inVal, ref string inValS)
	{
		float temp;
		string tempString;

		GUILayout.BeginVertical ();
		GUILayout.Label(name, GUILayout.Width(HLabelWidth));

		GUILayout.BeginHorizontal ();
		temp = GUILayout.HorizontalSlider (inVal, min, max,
		                                   GUILayout.Width (HSliderWidth), GUILayout.Height(HSliderHeight));
		
		if(temp != inVal)
		{
			inVal = temp;
			inValS = inVal.ToString("#0.0");
		}

		tempString = GUILayout.TextField(inValS, GUILayout.Width(HTextWidth), GUILayout.Height(HTextHeight));
		GUILayout.EndHorizontal ();

		if(tempString != inValS)
		{
			inValS = tempString;
			
			if(float.TryParse(tempString, out temp))
			{
				inVal = Mathf.Clamp(temp, min, max);
				inValS = inVal.ToString("#0.0");
			}
		}

		GUILayout.EndVertical ();
	}

	/// <summary>
	/// Creates a vertical slider with text input using GUILayout.
	/// </summary>
	/// <param name="name">Name of slider</param>
	/// <param name="min">Minimum value of slider.</param>
	/// <param name="max">Maximum value of slider.</param>
	/// <param name="inVal">Current slider value.</param>
	/// <param name="inValS">Current text input value.</param>
	public static void VerticalSliderAndText(string name, float min, float max,
	                                           ref float inVal, ref string inValS)
	{
		float temp;
		string tempString;

		GUILayout.BeginVertical ();

		GUILayout.Label(name, GUILayout.Width(VLabelWidth));
		
		temp = GUILayout.VerticalSlider(inVal, max, min,
		                                GUILayout.Height (VSliderHeight), GUILayout.Width (VSliderWidth));
		
		if(temp != inVal)
		{
			inVal = temp;
			inValS = inVal.ToString("#0.0");
		}
		
		tempString = GUILayout.TextField(inValS, GUILayout.Width(VTextWidth), GUILayout.Height(VTextHeight));
		
		if(tempString != inValS)
		{
			inValS = tempString;
			
			if(float.TryParse(tempString, out temp))
			{
				inVal = Mathf.Clamp(temp, min, max);
				inValS = inVal.ToString("#0.0");
			}
		}

		GUILayout.EndVertical ();
	}
}
