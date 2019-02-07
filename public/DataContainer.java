/*
 * Author: Jeremy Cruz
 * Date:   4/3/2015
 */

import objectdraw.*; // objectdraw components are used to create this
                     // graphical object

// This will create the graphical object to hold the calculated values
public class DataContainer
{
   // Constants
   private final double RECT_H = 15,                         // Container dimensions
                        RECT_W = 200,
                        X_SEPARATOR = 100,                   // Separate Text and container
                                                             // by 100 pixels

                        OBJECT_WIDTH = X_SEPARATOR + RECT_W, // Width of this object

                        ADJUSTMENT = 5;                      // Adjust placement of output
                                                             // 5 pixels away from start of
                                                             // container

   // Declarations
   private String dataType; // Holds String representing value to be calculated

   private double xLoc, // Coordinates of this object
                  yLoc;

   private Text text; // Text object that will show dataType

   private FramedRect framedRect; // FramedRect object that will represent container

   /*
    * ctor
    * @params: dataType is the String representing the value being calculated,
    *          xLoc and yLoc are coordinates of this object, and canvas is the
    *          DrawingCanvas this object will be palced on
    */
   public DataContainer(String dataType, double xLoc, double yLoc,
                        DrawingCanvas canvas)
   {
      // Initialize instance variables to parameters
      this.dataType = dataType;
      this.xLoc = xLoc;
      this.yLoc = yLoc;

      // Create the object with a Text and FramedRect object
      text = new Text(dataType, xLoc, yLoc, canvas);
      framedRect = new FramedRect(xLoc + X_SEPARATOR, yLoc, RECT_W, RECT_H,
                                  canvas);
   }

   /*
    * Move this object to a location (x, y) on the canvas
    * @params: a and y are the coordinates
    */
   public void move(double x, double y)
   {
      text.move(x, y);
      framedRect.move(x, y);
   }

   /*
    * Get the x location
    * @return: xLoc is the x location
    */
   public double getXLoc()
   {
      return xLoc;
   }

   /*
    * Get the y location
    * @return: yLoc is the y location
    */
   public double getYLoc()
   {
      return yLoc;
   }

   /*
    * Get the width of this object
    * @return: OBJECT_WIDTH is this object's width
    */
   public double getObjectWidth()
   {
      return OBJECT_WIDTH;
   }

   /*
    * Get the area in the container to place the calculated result
    * @return: The area of output is ADJUSTMENT pixels away from start of container
    */
   public double getFieldArea()
   {
      return framedRect.getWidth() + ADJUSTMENT;
   }
}
