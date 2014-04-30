import java.awt.*;

import graph.*;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.util.Arrays;


public class RandomGraph extends Applet{
	Graph2D graph;
    DataSet data1;
    DataSet data2;
    DataSet data3;
    Axis    xaxis;
    Axis    yaxis_left;
    Axis    yaxis_right;
    
	int timingSetSize = 0;
	int maxTime = 30;            //30 Seconds
	int maxOperations= 5000000;
	int inputValue = 100;
	int t1 = inputValue;
	int counter1 = 0;
	int t2 = inputValue;
	int counter2 = 0;
	int t3 = inputValue;
	int counter3 = 0;
	double time1[] = new double[inputValue];
	double time2[] = new double[inputValue];
	double time3[] = new double[inputValue];
	double inputs[] = new double[inputValue];; 
	Random1 r1 = new Random1();
	Random2 r2 = new Random2();
	Random3 r3 = new Random3();
	
	
	
	public void init() {                       //Collection data need to create the graph
	
	      graph = new Graph2D();
	      graph.drawzero = false;
	      graph.drawgrid = false;
	      graph.borderTop = 50;
	      graph.borderRight=100;
	      setLayout( new BorderLayout() );
	      add("Center", graph);
	      TimeInterval t= new TimeInterval();
	      
	      
	      for(int k = 0; k < inputValue; k++)
	      {
	    	  inputs[k] = (inputValue * (Math.pow(2,k)));
	    	  System.out.println(inputs[k]);
	      }
	      t.startTiming();
	      t.endTiming();
	      for (int k = 0; t.getElapsedTime() < maxTime && t1 < maxOperations; k = k+2)
	      {
	    	  if (timingSetSize == time1.length)
	    	  {
	    		  timingSetSize++;
	    		  time1 = Arrays.copyOf(time1, timingSetSize);
	    	  }
	    	  t.startTiming();
	    	  r1.random1(t1);
	    	  t.endTiming();
	    	  time1[k] = inputs[counter1];
	    	  time1[k+1] = t.getElapsedTime();
	    	  System.out.println("1:"+time1[k]);
	    	  t1=t1*2;
	    	  counter1++;
	      }
	      timingSetSize = 0;
	      t.startTiming();
	      t.endTiming();
	      for (int k = 0; t.getElapsedTime() < maxTime && t2 < maxOperations; k = k+2)
	      {
	    	  if (timingSetSize == time2.length)
	    	  {
	    		  timingSetSize++;
	    		  time2 = Arrays.copyOf(time2, timingSetSize);
	    	  }
	    	  t.startTiming();
	    	  r2.random2(t2);
	    	  t.endTiming();
	    	  time2[k] = inputs[counter2];
	    	  time2[k+1] = t.getElapsedTime();
	    	  System.out.println("2:"+time2[k]);
	    	  t2=t2*2;
	    	  counter2++;
	      }
	      timingSetSize = 0;
	      t.startTiming();
	      t.endTiming();
	      for (int k = 0; t.getElapsedTime() < maxTime && t3 < maxOperations; k = k+2)
	      {
	    	  if (timingSetSize == time3.length)
	    	  {
	    		  timingSetSize++;
	    		  time3 = Arrays.copyOf(time3, timingSetSize);
	    	  }
	    	  t.startTiming();
	    	  r3.random3(t3);
	    	  t.endTiming();
	    	  time3[k] = inputs[counter3];
	    	  time3[k+1] = t.getElapsedTime();
	    	  System.out.println("3:"+time3[k]);
	    	  t3=t3*2;
	    	  counter3++;
	      }
	      
	      data1 = graph.loadDataSet(time1,counter1);
	      data1.linestyle = 1;
	      data1.linecolor   =  new Color(0,255,0);
	      data1.marker    = 1;
	      data1.markerscale = 1;
	      data1.markercolor = new Color(0,0,255);
	      data1.legend(200,100,"Algorithm 1");
	      data1.legendColor(Color.black);
	      
	      data2 = graph.loadDataSet(time2,counter2);
	      data2.linestyle = 1;
	      data2.linecolor   =  new Color(255,0,0);
	      data2.marker    = 1;
	      data2.markerscale = 1;
	      data2.markercolor = new Color(0,0,255);
	      data2.legend(200,120,"Algorithm 2");
	      data2.legendColor(Color.black);
	      
	      data3 = graph.loadDataSet(time3,counter3);
	      data3.linestyle = 1;
	      data3.linecolor   =  new Color(0,0,255);
	      data3.marker    = 1;
	      data3.markerscale = 1;
	      data3.markercolor = new Color(0,0,255);
	      data3.legend(200,140,"Algorithm 3");
	      data3.legendColor(Color.black);

	      xaxis = graph.createAxis(Axis.BOTTOM);
	      xaxis.attachDataSet(data1);
	      xaxis.attachDataSet(data2);
	      xaxis.attachDataSet(data3);
	      xaxis.setTitleText("Input Size");
	      xaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
	      xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
	/*
	**      Attach the first data set to the Left Axis
	*/
	      yaxis_left = graph.createAxis(Axis.LEFT);
	      yaxis_left.attachDataSet(data1);
	      yaxis_left.attachDataSet(data2);
	      yaxis_left.attachDataSet(data3);
	      yaxis_left.setTitleText("Time (sec)");
	      yaxis_left.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
	      yaxis_left.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
	      yaxis_left.setTitleColor( new Color(0,0,255) ); 
	 }
  
	class TimeInterval {

		  private long startTime, endTime;
		  private long elapsedTime; // Time interval in milliseconds

		  // Commands
		  public void startTiming() {
		      elapsedTime = 0;
		      startTime = System.currentTimeMillis();
		  }

		  public void endTiming() {
		      endTime = System.currentTimeMillis();
		      elapsedTime = endTime - startTime;
		  }

		  // Queries
		  public double getElapsedTime() {
		      return (double) elapsedTime / 1000.0;
		  }
	}
	
	public static void main(String[] args)
	 {
		RandomGraph rg = new RandomGraph();
		rg.init();
	 }

}
