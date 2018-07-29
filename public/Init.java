/*~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=
                                                      Author: Jeremy Cruz 
                                                      Date:   3, April 2017 
                                 Init.java
--------------------------------------------------------------------------------
Purpose: Initalizes the applet to run the program.
*=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=*/

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Name:          Init
 *
 * Purpose:       Render the applet for user interaction
 *
 * Methods:       begin:            initalized GUI components in applet
 *                
 *                actionPerformed:  event listener
 *
 *                onMouseClick:     event for mouse clicks
 *
 *                undoData:         remove most recently added data from canvas
 *                                  and datastream
 *
 *                removeLines:      remove lines maintaining only one line at a
 *                                  time on the canvas
 *
 * Data fields:   decribed below
 */
public class Init extends WindowController
                  implements ActionListener
{
   // used for constant values
   private Constants c;

   // struct of data point values
   private ArrayList<DataPoint> dataPoints;

   // current amount of data points
   private int occupancy;

   // label of data point placement
   private boolean currentLabel;

   // panels for GUI components
   private JPanel mainTop,
                  mainBottom;

   // GUI specific panels within main panels
   private JPanel buttonsPanel1,
                  buttonsPanel2,
                  radioPanel,
                  equationPanel,
                  textPanel;

   // buttons
   private JButton undo,         // undo most recent
                   clear,        // clear canvas and data
                   perceptron,   // display perceptron
                   classifier,   // display classifier for all data
                   pClassifier,  // display classifier for positive data
                   nClassifier;  // display classifier for negative data

   // toggle buttons for positive and negative data               
   private JRadioButton positive,
                        negative;

   // maintain unique current label at a time                     
   private ButtonGroup group;

   // text area for data and string format of currently displayed line
   private JTextField data,
                      equation;

   // types of lines to output                   
   private Line pcLine, // perceptron
                cLine,  // classifier
                pLine,  // positives
                nLine;  // negatives

   /**
    * Name:       begin
    *
    * Purpose:    treated as main; generates the applet and its components
    *
    * Parameters: none
    *
    * Return:     void
    */
   public void begin()
   {
      // set lines to null by default
      pcLine = cLine = pLine = nLine = null;

      // use constants
      c = new Constants();

      // initialize GUI components and panels and fields
      dataPoints = new ArrayList<DataPoint>();
      occupancy = 0;
      currentLabel = true;
      mainTop = new JPanel(new GridLayout(c.DIM2, c.DIM1));
      mainBottom = new JPanel(new GridLayout(c.DIM3, c.DIM1));
      buttonsPanel1 = new JPanel(new GridLayout(c.DIM1, c.DIM2));
      buttonsPanel2 = new JPanel(new GridLayout(c.DIM1, c.DIM4));
      radioPanel = new JPanel(new GridLayout(c.DIM1, c.DIM2));
      equationPanel = new JPanel(new GridLayout(c.DIM1, c.DIM1));
      textPanel = new JPanel(new GridLayout(c.DIM1, c.DIM1));
      undo = new JButton("Undo");
      clear = new JButton("Clear");
      perceptron = new JButton("Perceptron");
      classifier = new JButton("Linear");
      pClassifier = new JButton("PLinear");
      nClassifier = new JButton("NLinear");
      positive = new JRadioButton("Positive", true);
      negative = new JRadioButton("Negative", false);
      group = new ButtonGroup();
      data = new JTextField("Data Points: ");
      equation = new JTextField("Equation: ");

      // add components to appropriate panels
      buttonsPanel1.add(undo);
      buttonsPanel1.add(clear);
      buttonsPanel2.add(perceptron);
      buttonsPanel2.add(classifier);
      buttonsPanel2.add(pClassifier);
      buttonsPanel2.add(nClassifier);
      radioPanel.add(positive);
      radioPanel.add(negative);
      equationPanel.add(equation);
      textPanel.add(data);
      add(mainTop, BorderLayout.NORTH);
      add(mainBottom, BorderLayout.SOUTH);
      mainTop.add(buttonsPanel1);
      mainTop.add(radioPanel);
      mainBottom.add(buttonsPanel2);
      mainBottom.add(equationPanel);
      mainBottom.add(textPanel);

      // add listeners to event driven components
      undo.addActionListener(this);
      clear.addActionListener(this);
      perceptron.addActionListener(this);
      classifier.addActionListener(this);
      pClassifier.addActionListener(this);
      nClassifier.addActionListener(this);
      positive.addActionListener(this);
      negative.addActionListener(this);
      
      // keep positive and negative radio buttons uniquely selected
      group.add(positive);
      group.add(negative);
   }
   
   /**
    * Name:       actionPerformed
    *
    * Purpose:    allow event driven interaction
    *
    * Parameters: evt: source of event
    *
    * Return:     void 
    */
   public void actionPerformed(ActionEvent evt)
   {
      // clear the canvas
      if(evt.getSource() == clear)
      {
         // default the text
         equation.setText("Equation: ");
         
         // canvas is already clear
         if(occupancy == 0)
            return;

         // clear and reset
         canvas.clear();
         dataPoints.clear();
         occupancy = 0;
         data.setText("Data Points: ");
         pcLine = cLine = pLine = nLine = null;
      }

      // undo most recent click
      if(evt.getSource() == undo)
      {
         // remove any lines
         removeLines();

         // cannot undo
         if(occupancy == 0)
            return;

         // undo most recent data input
         else
         {
            dataPoints.get(occupancy - 1).getDot().removeFromCanvas();
            dataPoints.remove(occupancy - 1);
            --occupancy;
            undoData();
            equation.setText("Equation: ");
         }
      }

      // set label to true
      if(evt.getSource() == positive)
      {
         currentLabel = true;
      }

      // set label to false
      if(evt.getSource() == negative)
      {
        currentLabel = false;
      }
      
      // display perceptron
      if(evt.getSource() == perceptron)
      {
         // remove any lines
         removeLines();

         // create, display, and show string format
         Perceptron currPerceptron = new Perceptron(dataPoints);
         pcLine = currPerceptron.generateVector(canvas);
         equation.setText(equation.getText() + currPerceptron.toString());
      }

      if(evt.getSource() == classifier)
      {
         // remove any lines
         removeLines();
         
         // create, display, and show string format
         Linear currClassifier = new Linear(dataPoints);
         cLine = currClassifier.generateVector(canvas);
         equation.setText(equation.getText() + currClassifier.toString());
      }

      if(evt.getSource() == pClassifier)
      {
         // remove any lines and create list of positive data
         removeLines();
         ArrayList<DataPoint> pDataPoints = new ArrayList<DataPoint>();

         // put positives in list
         for(int index = 0; index < dataPoints.size(); ++index)
         {
            if(dataPoints.get(index).formatLabel() == -1)
               continue;
            else
               pDataPoints.add(dataPoints.get(index));
         }

         // create, display, and show string format
         Linear currClassifier = new Linear(pDataPoints);
         pLine = currClassifier.generateVector(canvas);
         equation.setText(equation.getText() + currClassifier.toString());
      }

      if(evt.getSource() == nClassifier)
      {
         // remove any lines and create list of negative data
         removeLines();
         ArrayList<DataPoint> nDataPoints = new ArrayList<DataPoint>();
         
         // put negatives in list
         for(int index = 0; index < dataPoints.size(); ++index)
         {
            if(dataPoints.get(index).formatLabel() == 1)
               continue;
            else
               nDataPoints.add(dataPoints.get(index));
         }

         // create, display, and show string format
         Linear currClassifier = new Linear(nDataPoints);
         nLine = currClassifier.generateVector(canvas);
         equation.setText(equation.getText() + currClassifier.toString());
      }
   }
   
   /**
    * Name:       onMouseClick
    *
    * Purpose:    add data point at center of mouse pointer
    *
    * Parameters: point:   data point to add
    *
    * Return:     void
    */
   public void onMouseClick(Location point)
   {
      // remove any lines and initialize current data point
      removeLines();
      DataPoint curr = new DataPoint(new FilledOval(point.getX(), point.getY(), 
                                     c.DOT_SIZE, c.DOT_SIZE, canvas), point, 
                                     currentLabel);
      
      // determine color of point based on label
      if(currentLabel)
         curr.getDot().setColor(Color.BLUE);
      else
         curr.getDot().setColor(Color.RED);

      // move to center of mouse pointer, add to list, increment occupancy
      curr.getDot().moveTo(curr.getXPos() - c.ADJUST, curr.getYPos() - 
                           c.ADJUST);
      dataPoints.add(curr);
      ++occupancy;
      
      // output string format
      data.setText(data.getText() + curr.toString() + " "); 
   }
   
   /**
    * Name:       undoData
    *
    * Purpose:    reformat datastream after undo is executed
    *
    * Parameters: none
    *
    * Return:     void
    */
   public void undoData()
   {
      // default the text
      data.setText("Data Points: ");

      // reset data excluding most recent one
      for(int index = 0; index < occupancy; ++index)
      {
         data.setText(data.getText() + dataPoints.get(index).toString() + " ");
      }
   }
   
   /**
    * Name:       removeLines
    *
    * Purpose:    remove visible lines
    *
    * Parameters: none
    *
    * Return:     void
    */
   public void removeLines()
   {
      // remove perceptron
      if(pcLine != null)
      {
         pcLine.removeFromCanvas();
      }
      
      // remove classifier
      if(cLine != null)
      {
         cLine.removeFromCanvas();
      }
   
      // remove positive classifier
      if(pLine != null)
      {
         pLine.removeFromCanvas();
      }

      // remove negative classifier
      if(nLine != null)
      {
         nLine.removeFromCanvas();
      }
      
      // default equation text and set lines to null
      equation.setText("Equation: ");
      pcLine = cLine = pLine = nLine = null;
   }
}
