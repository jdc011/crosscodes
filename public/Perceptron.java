/*~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=
                                                      Author: Jeremy Cruz 
                                                      Date:   3, April 2017 
                                 Perceptron.java
--------------------------------------------------------------------------------
Purpose: Perceptron classifier line and weight.
*=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=*/

import objectdraw.*;
import java.util.ArrayList;

/**
 * Name:          Perceptron
 *
 * Purpose:       Implement perceptron algorithm
 *
 * Methods:       Perceptron:       constructor
 *
 *                dotProduct:       dot product of vectors
 *
 *                scalerProduct:    scaler product of vectors
 *
 *                addVectors:       vector addition
 *
 *                generateVector:   perceptron algorithm
 *
 *                toString:         string representation of perceptron vector
 *
 * Data fields:   c:             constants object
 *
 *                vector:        classifier
 *
 *                dataPoints:    list of data points
 *
 *                finalWeight:   weight of perceptron
 */
public class Perceptron
{
   private Constants c;                      // use constants
   private Line vector;                      // perceptron line
   private ArrayList<DataPoint> dataPoints;  // list of data points
   private DataPoint finalWeight;            // weight of perceptron
   
   /**
    * Name:       Perceptron
    *
    * Purpose:    Constructor 
    *
    * Parameters: dataPoints: list of data points
    *
    * Return:     none
    */
   public Perceptron(ArrayList<DataPoint> dataPoints)
   {
      // initialize data fields
      this.c = new Constants();
      this.dataPoints = dataPoints;
   }
   
   /**
    * Name:       dotProduct
    *
    * Purpose:    perform dot product on 2 vectors
    *
    * Parameters: lhs:  left vector
    *
    *             rhs:  right vector
    *
    * Return:     product: resulting dot product of parameters
    */
   private double dotProduct(DataPoint lhs, DataPoint rhs)
   {
      // current sum
      double product = 0.0;

      // loop through components to sum up dot product
      for(int index = 0; index < this.dataPoints.size(); ++index)
      {
         product += (this.dataPoints.get(index).getXPos() * 
                    this.dataPoints.get(index).getYPos());   
      }

      // return the dot product
      return product;
   }
   
   /**
    * Name:       scalerProduct
    *
    * Purpose:    return scaler product
    *
    * Parameters: scaler:  constant
    *
    *             point:   vector to be scaled
    *
    * Return:     scaled vector
    */
   private DataPoint scalerProduct(double scaler, DataPoint point)
   {
      // return scaled vector
      return new DataPoint(scaler * point.getXPos(), scaler * point.getYPos());
   }
   
   /**
    * Name:       addVectors
    *
    * Purpose:    vector addition
    *
    * Parameters: lhs:  left vector
    *
    *             rhs:  right vector
    *
    * Return:     added vector
    */
   private DataPoint addVectors(DataPoint lhs, DataPoint rhs)
   {
      // return added vector
      return new DataPoint(lhs.getXPos() + rhs.getXPos(), 
                           lhs.getYPos() + rhs.getYPos());
   }
   
   /**
    * Name:       generateVector
    *
    * Purpose:    create the perceptron classifier
    *
    * Parameters: canvas:  drawing canvas for line
    *
    * Return:     final line perceptron classifier
    */
   public Line generateVector(DrawingCanvas canvas)
   {
      DataPoint weightPre = new DataPoint(0, 0);   // weight defaults to 0   
      DataPoint weightPost = weightPre;            // post weight starts at 0
      double xCurrent;                             // current x coordinate
      double yCurrent;                             // current y coordinate

      // loop through data points in forming perceptron
      for(int index = 0; index < this.dataPoints.size(); ++index)
      {
         if(this.dataPoints.get(index).formatLabel() * 
            this.dotProduct(weightPre, this.dataPoints.get(index)) <= 0) 
         {
            // update weight
            weightPost = new DataPoint(this.addVectors(weightPre, 
                     this.scalerProduct(this.dataPoints.get(index).formatLabel()
                     , this.dataPoints.get(index))));
         }

         // keep weight the same
         else
         {
            continue;
         }
      }
      
      // update coordinates
      xCurrent = -weightPost.getXPos();
      yCurrent = -weightPost.getYPos();

      // draw coordinates up to boundry
      while(xCurrent <= c.REVERSE && yCurrent <= c.REVERSE)
      {
         xCurrent += 1;
         yCurrent += 1;
      }
      
      // assign final weight
      this.finalWeight = new DataPoint(xCurrent, yCurrent);

      // returnt the perceptron
      return new Line(0, c.REVERSE, xCurrent + c.ADJUST, yCurrent + c.ADJUST, canvas);
   }
   
   /**
    * Name:       toString
    *
    * Purpose:    String representation of perceptron
    *
    * Parameters: none
    *
    * Return:     weight of perceptron as string
    */
   public String toString()
   {
      // string formal of perceptron weight
      return "Vector w = (" + this.finalWeight.getXPos() + ", " + 
             (this.c.REVERSE - this.finalWeight.getYPos() * -1) + ")";
   }
}
