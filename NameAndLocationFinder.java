package com.amante;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
 
/**
 * This class demonstrates how to use NameFinderME class to do Named Entity Recognition/Extraction tasks.
 * @author tutorialkart.com
 */
public class NameAndLocationFinder {
	

    public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
    	//input string
    	
    	String sentence = new String
    			("John Smith is standing next to bus stop and waiting for "
    			+ "Mike who is coming from Atlanta John Smith is standing next "
    			+ "to bus stop and waiting for Mike who is coming from Atlanta with Abera Clinton in Ethiopia for Addis Ababa "
    	        );
    	
    	//converting input to array of string
    	String[] sentence_to_array = sentence.split(" ");
    	
    	System.out.println("\nYour sentence: \n\t" + sentence);
       
    	//System.out.println(Arrays.toString(sentence_to_array));
        
        // initialize the class
        //NameAndLocationFinder finder = new NameAndLocationFinder();
        
        // find person and location
        System.out.println("\nrecognized entity name");
        //finder.findName(sentence_to_array);
        //finder.findLocation(sentence_to_array);
        	
        //Create two threads:
        Thread thread1 = new Thread() {
        	public void run() {
        		try {
        	
					NameAndLocationFinder.findLocation(sentence_to_array);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        };
        //Creating second thread
        Thread thread2 = new Thread() {
        	public void run() {
        	    try {
					NameAndLocationFinder.findName(sentence_to_array);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	 }
        };

         //Start the downloads.
         thread1.start();
         thread2.start();

         //Wait for them both to finish
         thread1.join();
         thread2.join();

         System.out.println();
    }// end of main method
  
    /**
     * method to find locations in the sentence
     * @throws IOException
     */
    public static void findName(String[] sentence) throws IOException {
        InputStream is = new FileInputStream("en-ner-person.bin");
 
        // load the model from file
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();
 
        // feed the model to name finder class
        NameFinderME nameFinder = new NameFinderME(model);
 

 
        Span nameSpans[] = nameFinder.find(sentence);
 
        // nameSpans contain all the possible entities detected
        for(Span s: nameSpans){
            System.out.print(s.toString());
            System.out.print("  :  ");
            // s.getStart() : contains the start index of possible name in the input string array
            // s.getEnd() : contains the end index of the possible name in the input string array
            for(int index=s.getStart();index<s.getEnd();index++){
                System.out.print(sentence[index]+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * method to find locations in the sentence
     * @throws IOException
     */
    public static void findLocation(String[] sentence) throws IOException {
        InputStream is = new FileInputStream("en-ner-location.bin");
 
        // load the model from file
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();
 
        // feed the model to name finder class
        NameFinderME nameFinder = new NameFinderME(model);
 
        Span nameSpans[] = nameFinder.find(sentence);
 
        // nameSpans contain all the possible entities detected
        for(Span s: nameSpans){
            System.out.print(s.toString());
            System.out.print("  :  ");
            // s.getStart() : contains the start index of possible name in the input string array
            // s.getEnd() : contains the end index of the possible name in the input string array
            for(int index=s.getStart();index<s.getEnd();index++){
                System.out.print(sentence[index]+" ");
            }
            System.out.println();
        }
    }
    
}
 