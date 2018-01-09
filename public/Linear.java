/*~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=
                                                      Author: Jeremy Cruz 
                                                      Date:   3, April 2017 
                                 Linear.java
--------------------------------------------------------------------------------
Purpose: Linear classifier line and equation.
*=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=*/

import objectdraw.*;
import java.util.ArrayList;

/**
 * Name:          Linear
 *
 * Purpose:       Implement linear classifier algorithm
 *
 * Methods:       Linear:           constructor
 *
 *                generateVector:   linear classifier algorithm
 *
 *                toString:         string representation of linear classifier
 *
 * Data fields:   c:                constant object
 *
 *                HALF:             value 2 to cut in half
 *
 *                vector:           classifier
 *
 *                dataPoints:       list of data points
 *
 *                finalSlope:       slope of line
 *
 *                finalIntercept:   y intercept of line
 */
public class Linear
{
   private Constants c;                      // use constants
   private static final int HALF = 2;        // divide by 2
   private Line vector;                      // linear line
   private ArrayList<DataPoint> dataPoints;  // list of data points
   private double finalSlope;                // slope of line
   private double finalIntercept;            // y intercept of line

   /**
    * Name:       DataPoint
    *
    * Purpose:    Constructor for spawning datapoints
    *
    * Parameters: dot:     filled oval representation
    *
    *             point:   data location
    *
    *             label:   data label
    *
    * Return:     none
    */
   public Linear(ArrayList<DataPoint> dataPoints)
   {
      // initialize data fields
      this.c = new Constants();
      this.dataPoints = dataPoints;
   }
   
   /**
    * Name:       generateVector
    *
    * Purpose:    Create the linear classifier
    *
    * Parameters: canvas:  drawing canvas for the linear classifier
    *
    * Return:     line of classifier
    */
   public Line generateVector(DrawingCanvas canvas)
   {
      double xMean = 0.0;     // mean of x values
      double yMean = 0.0;     // mean  of y values
      double slope = 0.0;     // slope
      double squareSum = 0.0; // sum of squares
      double yIntercept;      // intercept
      double xCurrent;        // current x value
      double yCurrent;        // current y value

      // arrays of x and y values
      double [] xNums = new double[this.dataPoints.size()];
      double [] yNums = new double[this.dataPoints.size()];

      // aquire sums of x and y values
      for(int index = 0; index < this.dataPoints.size(); ++index)
      {
         xMean += this.dataPoints.get(index).getXPos();
         yMean += this.dataPoints.get(index).getYPos();
      }

      // obtain x and y averages
      xMean = xMean / this.dataPoints.size();
      yMean = yMean / this.dataPoints.size();
      
      // aquire x and y minus their averages
      for(int index = 0; index < this.dataPoints.size(); ++index)
      {
         xNums[index] = this.dataPoints.get(index).getXPos() - xMean;
         yNums[index] = this.dataPoints.get(index).getYPos() - yMean;
      }

      // aquire square values
      for(int index = 0; index < this.dataPoints.size(); ++index)
      {
         slope += xNums[index] * yNums[index];
         squareSum += xNums[index] * xNums[index];
      }

      // assign the slop and y intercept 
      slope = slope / squareSum;
      yIntercept = yMean - (xMean * slope);
   
      // begin current values
      xCurrent = 0;
      yCurrent = yIntercept;

      // draw line up to applet boundry
      while(xCurrent <= c.REVERSE && yCurrent <= c.REVERSE)
      {
         xCurrent += 1;
         yCurrent += slope;
      }
      
      // assign final values
      this.finalSlope = slope;
      this.finalIntercept = yIntercept;

      // return the linear classifier
      return new Line(0, yIntercept + 5, xCurrent, yCurrent + 5, canvas);
   }
   
   /**
    * Name:       toString
    *
    * Purpose:    format linear equation
    *
    * Parameters: none
    *
    * Return:     string representation of linear equation
    */
   public String toString()
   {
      // string format of linear equation
      return "y = " + -this.finalSlope + "x + " + (this.c.REVERSE - 
             this.finalIntercept * -1);
   }
}
