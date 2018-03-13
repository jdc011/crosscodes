/*
 * Author: Jeremy Cruz
 * Date:   4/3/2015
 */

import objectdraw.*;        // GUI based on this library

import java.awt.*;          // Used for GUI components

import java.awt.event.*;    // Used to handle events fired by pressing
                            // buttons

import javax.swing.*;       // Used for framed to add the GUI components

import java.util.ArrayList; // Used for arraylist to update data input at
                            // real time

/*
 * This is the main driver(entends WindowController) that creates the interface
 * and calls functions for data input by handling events
 * (implements ActionListener)
 */
public class DataWindow extends WindowController
                        implements ActionListener
{
   // Constants
   private final int NORTH_ROWS = 1, // North panel dimensions
                     NORTH_COLS = 3,

                     SOUTH_ROWS = 2, // South panel dimensions
                     SOUTH_COLS = 1,

                     HALF = 2,       // Used for defining midpoints for
                                     // dynamically moving objectdraw
                                     // components to the center

                     ADJUST = 5;     // Adjust the output 5 pixels away from
                                     // the start of the FramedRect objects

   private final double Y_POS = 75,          // Defined y location of
                                             // highest objectdraw components

                        ORIGINAL_SPACE = 50; // Space addition between graphically
                                             // descending objectdraw components

   // Declarations
   private double space = 50, // Space buffer between objectdraw components

                  total,      // Value holders for corresponding calculated results
                  median,
                  mode,
                  average,
                  lowest,
                  highest;

   // GUI components
   private JTextField typeEntry,  // Type the input

                      theEntries; // Output the input

   private JLabel showEntries;    // Title header for south panel

   private JButton addEntry,      // Input to arraylist

                   clear;         // Clear arraylist

   // Panels
   private JPanel controllerPanel,   // North panel that takes and clear input

                  inputLabelPanel,   // Panel for south panel title showing input

                  inputOutPanel,     // Panel for showing input

                  inputPrinterPanel; // South panel that outputs what user inputs

   private ArrayList<Double> entries; // Arraylist to be updated with input and cleared
                                      // when needed

   // DataContainer objects that graphically hold the resulting calculations
   private DataContainer totalContainer,
                         medianContainer,
                         modeContainer,
                         averageContainer,
                         lowestContainer,
                         highestContainer;

   // The actual values of the resulting calculations
   private Text theTotal,
                theMedian,
                theMode,
                theAverage,
                theLowest,
                theHighest;

   // Flags
   private boolean ranCalcs = false,  // Determines if calculations were ran to
                                      // prevent exception when clicking clear
                                      // button and when clearing and updating
                                      // resulting values

                   firstInput = true; // Determines if input is the first to output
                                      // first input without a following ","

   // DataProcessor object
   private DataProcessor dataProcessor;

   // Layout the interface from objectdraw and awt components
   public void begin()
   {
      // Initialize GUI objects
      typeEntry = new JTextField(null);
      theEntries = new JTextField(null);
      showEntries = new JLabel("---Your Data---");
      addEntry = new JButton("Enter");
      clear = new JButton("Clear");
      controllerPanel = new JPanel(new GridLayout(NORTH_ROWS, NORTH_COLS));
      inputPrinterPanel = new JPanel(new BorderLayout());
      inputLabelPanel = new JPanel();
      inputOutPanel = new JPanel(new GridLayout(SOUTH_ROWS, SOUTH_COLS));

      // Add awt components to panels
      controllerPanel.add(typeEntry);
      controllerPanel.add(addEntry);
      controllerPanel.add(clear);
      inputLabelPanel.add(showEntries);
      inputOutPanel.add(theEntries);
      inputPrinterPanel.add(inputLabelPanel, BorderLayout.NORTH);
      inputPrinterPanel.add(inputOutPanel, BorderLayout.SOUTH);
      add(controllerPanel, BorderLayout.NORTH);
      add(inputPrinterPanel, BorderLayout.SOUTH);

      // Initialize the arraylist
      entries = new ArrayList<>();

      // Create DataContainer objects to hold calculated data values
      totalContainer = new DataContainer("Total", canvas.getWidth() / HALF,
                                        Y_POS, canvas);
      medianContainer = new DataContainer("Median", canvas.getWidth() / HALF,
                                          Y_POS + space, canvas);
      modeContainer = new DataContainer("Mode", canvas.getWidth() / HALF,
                                        Y_POS + (space += ORIGINAL_SPACE), canvas);
      averageContainer = new DataContainer("Average", canvas.getWidth() / HALF,
                                           Y_POS + (space += ORIGINAL_SPACE), canvas);
      lowestContainer = new DataContainer("Lowest", canvas.getWidth() / HALF,
                                          Y_POS + (space += ORIGINAL_SPACE), canvas);
      highestContainer = new DataContainer("Highest", canvas.getWidth() / HALF,
                                           Y_POS + (space += ORIGINAL_SPACE), canvas);

      // Dynamically reposition DataContainer objects on canvas to center
      totalContainer.move(- totalContainer.getObjectWidth() / HALF, 0);
      medianContainer.move(- medianContainer.getObjectWidth() / HALF, 0);
      modeContainer.move(- modeContainer.getObjectWidth() / HALF, 0);
      averageContainer.move(- averageContainer.getObjectWidth() / HALF, 0);
      lowestContainer.move(- lowestContainer.getObjectWidth() / HALF, 0);
      highestContainer.move(- highestContainer.getObjectWidth() / HALF, 0);

      // Register buttons to listener
      addEntry.addActionListener(this);
      clear.addActionListener(this);
   }

   /*
    * actionPerformed method override from ActionListener
    * @params: evt is the ActionEvent being fired
    */
   public void actionPerformed(ActionEvent evt)
   {
      // Add input
      if(evt.getSource() == addEntry)
      {
         // Incase of non-numeric input
         try
         {
            // Convert input to double, add to arraylist, clear typed content
            double input = Double.parseDouble(typeEntry.getText());
            entries.add(input);
            typeEntry.setText(null);

            // Manage how output looks like; differs after first input by adding ","
            if(firstInput)
               theEntries.setText(theEntries.getText() + input);
            else
               theEntries.setText(theEntries.getText() + ",  " + input);

            // Set flag to false after first input has been added
            firstInput = false;

            // Manage outputs by calling functions to return corresponding calculated values
            checkAndClear();
            callDataProcessors();
            returnVals();

            // Set flag to true once calculations are made atleast once
            ranCalcs = true;
         }

        // Catch non-numeric input
        catch(NumberFormatException exp)
        {
           // Display this message using dialog box and clear typed content
           JOptionPane.showMessageDialog(null, "Non-numeric input detected." +
                                         "\nData NOT updated!");
           typeEntry.setText(null);
        }
      }

      // Clear the arraylist and start over
      if(evt.getSource() == clear)
      {
         // Reset flags to original state
         ranCalcs = false;
         firstInput = true;

         // Arraylist must be null for this to execute(remove outputs)
         if(!entries.isEmpty())
         {
            theEntries.setText(null);
            entries.clear();
            theTotal.removeFromCanvas();
            theMedian.removeFromCanvas();
            theMode.removeFromCanvas();
            theAverage.removeFromCanvas();
            theLowest.removeFromCanvas();
            theHighest.removeFromCanvas();
         }
      }

   }

   // Clear outdated output to add new outputs
   private void checkAndClear()
   {
      // Happens when calculations have been ran
      if(ranCalcs)
      {
         theTotal.removeFromCanvas();
         theMedian.removeFromCanvas();
         theMode.removeFromCanvas();
         theAverage.removeFromCanvas();
         theLowest.removeFromCanvas();
         theHighest.removeFromCanvas();
      }
   }

   // Do corresponding calculations
   private void callDataProcessors()
   {
      dataProcessor = new DataProcessor();
      total = dataProcessor.getTotal(entries);
      median = dataProcessor.getMedian(entries);
      mode = dataProcessor.getMode(entries);
      average = dataProcessor.getAverage(entries);
      lowest = dataProcessor.getLowest(entries);
      highest = dataProcessor.getHighest(entries);
   }

   /*
    * Output resulting values from calculations by assigning corresponing
    * holder variables to the resulting calculated values
    */
   private void returnVals()
   {
      theTotal = new Text(total, totalContainer.getFieldArea() + ADJUST,
                         totalContainer.getYLoc(), canvas);
      theMedian = new Text(median, medianContainer.getFieldArea() + ADJUST,
                           medianContainer.getYLoc(), canvas);
      theMode = new Text(mode, modeContainer.getFieldArea() + ADJUST,
                         modeContainer.getYLoc(), canvas);
      theAverage = new Text(average, averageContainer.getFieldArea() + ADJUST,
                            averageContainer.getYLoc(), canvas);
      theLowest = new Text(lowest, lowestContainer.getFieldArea() + ADJUST,
                           lowestContainer.getYLoc(), canvas);
      theHighest = new Text(highest, highestContainer.getFieldArea() + ADJUST,
                            highestContainer.getYLoc(), canvas);
   }
}
