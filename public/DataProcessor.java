/* 
 * Author: Jeremy Cruz
 * Date:   4/3/2015
 */

import java.util.*; // Needed for the arraylist and Collection's method to
                    // sort it

/*
 * This returns all the calculated values through the following series
 * of methods
 */
public class DataProcessor
{
   // Constants
   private final int ADJUST = 1; // Used in checking arraylists for indeces 1 greater than
                           // the current index

   // Declarations
   private ArrayList<Double> entries; // Arraylist to be passed in parameters
                                      // of the following functions to return
                                      // corresponding calculated values

   // Holders of the corresponding calculated results
   private double total = 0,
                  median,
                  mode,
                  average,
                  lowest,
                  highest;

   /*
    * Calls Collection's method to sort arraylist
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    */
   private void sort(ArrayList<Double> entries)
   {
      Collections.sort(entries);
   }

   /*
    * Sums up inputs
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: total is the sum of inputs
    */
   public double getTotal(ArrayList<Double> entries)
   {
      for(int index = 0; index < entries.size(); index++)
         total += entries.get(index);

      return total;
   }

   /*
    * Gets median of inputs
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: median is the median
    */
   public double getMedian(ArrayList<Double> entries)
   {
      // Declarations
      final int TEST = 2;  // Used for odd/ even checking, and divinding to get median
      int choiceIndex,     // Highlight indeces to help find median
          nextChoiceIndex;

      // Sort arraylist for algorithm to work
      sort(entries);

      // Only one entry
      if(entries.size() == ADJUST)
         median = entries.get(0);
      else

      // Odd amount of entries
      if(entries.size() % TEST != 0)
      {
         choiceIndex = (entries.size() - ADJUST) / TEST;
         median = entries.get(choiceIndex);
      }
      else

      // Even amount of entries
      if(entries.size() % TEST == 0)
      {
         choiceIndex = entries.size() / TEST;
         nextChoiceIndex = choiceIndex - ADJUST;
         median = (entries.get(choiceIndex) +
                   entries.get(nextChoiceIndex)) / TEST;
      }

      return median;
   }

   /*
    * Gets mode of inputs
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: mode is the mode
    */
   public double getMode(ArrayList<Double> entries)
   {
      // Declarations
      double mode = entries.get(0), // Initial mode = first entry

             temp = 1,              // Temporary holder variables to
             temp2 = 1;             // find mode

      // Convert arraylist to array
      double[] arrayOfEntries = new double[entries.size()];

      // Index being checked
      int index;

      // Put entries from arraylist into array
      for(index = 0; index < entries.size(); index++)
         arrayOfEntries[index] = entries.get(index);

      // Determine which entry has the highest occurence
      for(index = ADJUST; index < arrayOfEntries.length; index++)
      {
         if(arrayOfEntries[index - ADJUST] == arrayOfEntries[index])
            temp++;
         else
            temp = 1;
         if(temp >= temp2)
         {
            mode = arrayOfEntries[index];
            temp2 = temp;
         }
      }

      return mode;
   }

   /*
    * Gets average of inputs
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: average is the average
    */
   public double getAverage(ArrayList<Double> entries)
   {
      average = total / entries.size();

      return average;
   }

   /*
    * Gets lowest input
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: lowest is the lowest input
    */
   public double getLowest(ArrayList<Double> entries)
   {
      lowest = entries.get(0);
      for(int index = ADJUST; index < entries.size(); index++)
      {
         if(entries.get(index) < lowest)
            lowest = entries.get(index);
      }

      return lowest;
   }

   /*
    * Gets highest input
    * @params: entries is the arraylist to be passed as a parameter in this
    *          function
    * @return: highest is the highest input
    */
   public double getHighest(ArrayList<Double> entries)
   {
      highest = entries.get(0);
      for(int index = 1; index < entries.size(); index++)
      {
         if(entries.get(index) > highest)
            highest = entries.get(index);
      }

      return highest;
   }
}
