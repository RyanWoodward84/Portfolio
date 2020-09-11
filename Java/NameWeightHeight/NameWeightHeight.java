/**
Ryan Woodward


This program will read input from a user in JOptionPane. The user will give both their first and last name, their height
in inches, and their weight in pounds.
*/

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

public class NameWeightHeight
{
    public static void main (String[] args)
    {
        String userInput = JOptionPane.showInputDialog (null, "Input Prompt:",
                       "Input Demo", JOptionPane.QUESTION_MESSAGE);
        if (userInput == null)
        {
            JOptionPane.showMessageDialog(null, "No information entered, program now exiting");
        }
        else
        {
            String name = nameExtract(userInput);
            double height = heightExtract(userInput);
            double weight = weightExtract(userInput);
        
            JOptionPane.showMessageDialog(null, "Personal Details\nName: " + name + "\nHeight: "
                                            + height +" cm\nWeight: " + weight + " kg");
        }
    }
    
    public static String nameExtract(String input)
    {
        String name;
        int index;
        
        index = input.indexOf(",");
        name = input.substring(0, index);
        
        return name;
    }
    
    public static double heightExtract(String input)
    {
        String heightWord;
        int index1, index2;
        double height;
        
        index1 = input.indexOf(",");
        index2 = input.indexOf(",", index1 + 1);
        heightWord = input.substring(index1 + 1, index2);
        height = Double.parseDouble(heightWord);
        
        return height;
    }
    
    public static double weightExtract(String input)
    {
        String weightWord;
        int index1, index2;
        double weight;
        
        index1 = input.indexOf(",");
        index2 = input.indexOf(",", index1 + 1);
        weightWord = input.substring(index2 + 1);
        weight = Double.parseDouble(weightWord);
        
        return weight;        
    }
}