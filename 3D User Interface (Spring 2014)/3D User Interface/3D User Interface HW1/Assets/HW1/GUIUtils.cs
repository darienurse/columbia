using UnityEngine;
using System.Collections;

public class GUIUtils {

	//Fully customizable Horizontal Slider parameters
	public static int HLabelWidth   = 120;	// Width of horizontal slider label
	public static int HLabelHeight  = 20;	// Height of horizontal slider label
	public static int HSliderWidth  = 120;	// Width of horizontal slider
	public static int HSliderHeight = 20;	// Height of horizontal slider
	public static int HTextWidth    = 50;	// Width of horizontal slider text
	public static int HTextHeight   = 20;	// Height of horizontal slider text
	public static int HSpace        = 10;	// Extra horizontal space

	//Fully customizable Vertical Slider Parameters
	public static int VLabelWidth   = 50;	// Width of vertical slider label
	public static int VLabelHeight  = 20;	// Height of vertical slider label
	public static int VSliderWidth  = 30;	// Width of vertical slider
	public static int VSliderHeight = 120;	// Height of vertical slider
	public static int VTextWidth    = 50;	// Width of vertical slider text
	public static int VTextHeight   = 20;	// Height of vertical slider text
	public static int VSpace        = 5;	// Extra vertical space

	/// <summary>
	/// Creates a horizontal slider with text input and label - using standard GUI components.
	/// </summary>
	/// <param name="pos">Position of control group.</param>
	/// <param name="name">Name of slider</param>
	/// <param name="min">Minimum value of slider.</param>
	/// <param name="max">Maximum value of slider.</param>
	/// <param name="inVal">Current slider value.</param>
	/// <param name="inValS">Current text input value.</param>
	public static void HorizontalSliderAndText(Rect pos, string name, float min, float max,
	                                               ref float inVal, ref string inValS)
	{
		float temp;
		string tempString;
		
		GUI.BeginGroup(pos);

		GUI.Label(new Rect(0, 0, HLabelWidth, HLabelHeight), name);

		GUI.BeginGroup (new Rect (0, HLabelHeight+VSpace, HSliderWidth+HSpace+HTextWidth, HSliderHeight+VSpace));
		temp = GUI.HorizontalSlider(new Rect(0, 0, HSliderWidth, HSliderHeight), inVal, min, max);
		
		if(temp != inVal)
		{
			inVal = temp;
			inValS = inVal.ToString("#0.0");
		}

		tempString = GUI.TextField(new Rect(HSliderWidth+HSpace, 0, HTextWidth, HTextHeight), inValS);
		
		if(tempString != inValS)
		{
			inValS = tempString;
			
			if(float.TryParse(tempString, out temp))
			{
				inVal = Mathf.Clamp(temp, min, max);
				inValS = inVal.ToString("#0.0");
			}
		}
		GUI.EndGroup();
		GUI.EndGroup();
	}

	/// <summary>
	/// Creates a vertical slider with text input and label - using standard GUI components.
	/// </summary>
	/// <param name="pos">Position of control group.</param>
	/// <param name="name">Name of slider</param>
	/// <param name="min">Minimum value of slider.</param>
	/// <param name="max">Maximum value of slider.</param>
	/// <param name="inVal">Current slider value.</param>
	/// <param name="inValS">Current text input value.</param>
	public static void VerticalSliderAndText(Rect pos, string name, float min, float max,
	                                           ref float inVal, ref string inValS)
	{
		float temp;
		string tempString;
		
		GUI.BeginGroup(pos);

		GUI.Label(new Rect(0,0,VLabelWidth,VLabelHeight), name);

		temp = GUI.VerticalSlider(new Rect(0,VLabelHeight+VSpace,VSliderWidth,VSliderHeight), inVal, max, min);
		
		if(temp != inVal)
		{
			inVal = temp;
			inValS = inVal.ToString("#0.0");
		}

		tempString = GUI.TextField(new Rect(0,VLabelHeight+VSliderHeight+2* VSpace, VTextWidth ,VTextHeight), inValS);
		
		if(tempString != inValS)
		{
			inValS = tempString;
			
			if(float.TryParse(tempString, out temp))
			{
				inVal = Mathf.Clamp(temp, min, max);
				inValS = inVal.ToString("#0.0");
			}
		}
		
		GUI.EndGroup();
	}
}
