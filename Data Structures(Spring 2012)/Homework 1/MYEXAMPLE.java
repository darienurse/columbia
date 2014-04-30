import java.awt.*;
import graph.*;
import java.applet.Applet;


public class MYEXAMPLE extends Applet {

    Graph2D graph;
    DataSet data1;
    DataSet data2;
    Axis    xaxis;
    Axis    yaxis_left;
    Axis    yaxis_right;
    double d1[],d2[];
    int np = 100000;


    public void init() {
      int i,j;
      final int MAXPOINTS=12;
      double d1[] = new double[MAXPOINTS*2];
      double d2[] = new double[MAXPOINTS*2];
      double x,y;

      graph = new Graph2D();
      graph.drawzero = false;
      graph.drawgrid = false;
      graph.borderTop = 50;
      graph.borderRight=100;
      setLayout( new BorderLayout() );
      add("Center", graph);
      
      TimeInterval t= new TimeInterval();
      t.startTiming();
      for(i=0; i<MAXPOINTS; i++){
      	x=i;
      	y=i;
      	d1[i*2]=x; d1[i*2+1]=y;
      }
    	t.endTiming();
      System.out.println("time to generate " + MAXPOINTS + " values of Y=X is " + t.getElapsedTime());
       
      t= new TimeInterval();
      t.startTiming();
      for(i=0; i<MAXPOINTS; i++){
      	x=i;
      	y=i*i;
      	d2[i*2]=x; d2[i*2+1]=y;
      }
      t.endTiming();
      System.out.println("time to generate " + MAXPOINTS + " values of Y=X*X is " + t.getElapsedTime()/1000.0);
      
      data1 = graph.loadDataSet(d1,MAXPOINTS);
      data1.linestyle = 1;
      data1.linecolor   =  new Color(0,255,0);
      data1.marker    = 1;
      data1.markerscale = 1;
      data1.markercolor = new Color(0,0,255);
      data1.legend(200,100,"Y=X, linear");
      data1.legendColor(Color.black);
      
      data2 = graph.loadDataSet(d2,MAXPOINTS);
      data2.linestyle = 1;
      data2.linecolor   =  new Color(255,0,0);
      data2.marker    = 1;
      data2.markerscale = 1;
      data2.markercolor = new Color(0,0,255);
      data2.legend(200,120,"Y=X*X, quadratic");
      data2.legendColor(Color.black);

      xaxis = graph.createAxis(Axis.BOTTOM);
      xaxis.attachDataSet(data1);
      xaxis.attachDataSet(data2);
      xaxis.setTitleText("X values");
      xaxis.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
      xaxis.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
/*
**      Attach the first data set to the Left Axis
*/
      yaxis_left = graph.createAxis(Axis.LEFT);
      yaxis_left.attachDataSet(data1);
      yaxis_left.attachDataSet(data2);
      yaxis_left.setTitleText("Y VALUES");
      yaxis_left.setTitleFont(new Font("TimesRoman",Font.PLAIN,20));
      yaxis_left.setLabelFont(new Font("Helvetica",Font.PLAIN,15));
      yaxis_left.setTitleColor( new Color(0,0,255) );
    
    
 }
}



/**
* A timing utility class useful for timing code segments
*/
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

