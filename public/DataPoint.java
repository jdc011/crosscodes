/*~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=
                                                      Author: Jeremy Cruz 
                                                      Date:   3, April 2017 
                              DataPoint.java
--------------------------------------------------------------------------------
Purpose: Datapoints are placed on the canvas and alalyzed for output.
*=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=*/

import objectdraw.*;

/**
 * Name:          DataPoint
 *
 * Purpose:       Spawn data points on the canvas for analyzation
 *
 * Methods:       DataPoint:     constructor
 *
 *                formatLabel:   format label into positive or negative
 *
 *                getXPos:       return x corrdinate
 *
 *                getYPos:       return y coordinate
 * 
 *                getDot:        return filled oval for the data point
 *
 *                getLocation:   return location point
 *
 *                getLabel:      return the label
 *
 *                toString:      output string format
 *
 * Data fields:   c:                constants object
 *
 *                constructorFlag:  determine which constructor was called
 *
 *                xPos:             x coordinate
 *
 *                yPos:             y coordinate
 *
 *                dot:              filled oval object of data point
 *
 *                point:            location of data point
 *
 *                label:            data point label
 */
public class DataPoint
{
   private Constants c;             // use constants
   private boolean constructorFlag; // constructor determinator
   private double xPos;             // x placement
   private double yPos;             // y placement
   private FilledOval dot;          // (x,y) filled oval object
   private Location point;          // (x.y) placement
   private boolean label;           // data label

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
   public DataPoint(FilledOval dot, Location point, boolean label)
   {
      // initialize datafields
      this.c = new Constants();
      this.constructorFlag = true;
      this.dot = dot;
      this.point = point;
      this.label = label;
   }
   
   /**
    * Name:       DataPoint    
    *
    * Purpose:    Constructor where only x and y are needed for data
    *
    * Parameters: xPos: x place
    *
    *             yPos: y place
    *
    * Return:     none
    */
   public DataPoint(double xPos, double yPos)
   {
      // initialize coordinate values
      this.c = new Constants();
      this.constructorFlag = false;
      this.xPos = xPos;
      this.yPos = yPos;
   }
   
   /**
    * Name:       DataPoint
    *
    * Purpose:    Constructor for vector manipulation
    *
    * Parameters: point:   the data vector
    *
    * Return:     none
    */
   public DataPoint(DataPoint point)
   {
      // initialize datafields
      this.c = new Constants();
      this.constructorFlag = false;
      this.xPos = point.getXPos();
      this.yPos = point.getYPos();
   }
   
   /**
    * Name:       formatLabel
    *
    * Purpose:    convert label to sign
    *
    * Parameters: none
    *
    * Return:     1 for true, -1 for false
    */
   public int formatLabel()
   {
      // return sign
      return this.label ? 1 : -1;
   }
   
   /**
    * Name:       getXPos
    *
    * Purpose:    return x value
    *
    * Parameters: none
    *
    * Return:     x position
    */  
   public double getXPos()
   {
      // return filled oval coordinate
      if(this.constructorFlag)
         return this.getDot().getX();

      // return this coordinate
      return this.xPos;
   }
   
   /**
    * Name:       getYPos
    *
    * Purpose:    return y value
    *
    * Parameters: none
    *
    * Return:     y position
    */
   public double getYPos()
   {
      // return filled oval doordinate
      if(this.constructorFlag)
         return this.getDot().getY();

      // rerturn this coordinate
      return this.yPos;
   }
   
   /**
    * Name:       getDot
    *
    * Purpose:    return filled oval object
    *
    * Parameters: none
    *
    * Return:     dot:  filled oval object
    */
   public FilledOval getDot()
   {
      // return dot representation
      return this.dot;
   }
   
   /**
    * Name:       getLocation
    *
    * Purpose:    return (x,y) point
    *
    * Parameters: none
    *
    * Return:     point:   coordinate placement
    */
   public Location getLocation()
   {
      // return coordinate placement
      return this.point;
   }
   
   /**
    * Name:       getLabel
    *
    * Purpose:    return label
    *
    * Parameters: none
    *
    * Return:     label:   true or false
    */
   public boolean getLabel()
   {
      // return the label
      return this.label;
   }
   
   /**
    * Name:       toString
    *
    * Purpose:    string format of data point
    *
    * Parameters: none
    *
    * Return:     string representation of data point
    */
   public String toString()
   {
      // return string representation
      return "(" + this.point.getX() + ", " + 
             (this.c.REVERSE - this.point.getY()) + "; " +
             this.formatLabel() + ")";
   }
}
