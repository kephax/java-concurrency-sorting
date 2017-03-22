import java.io.*;
import java.util.*;

/**
 * This class will call the methods execute1, execute2, execute3.
 * Execute1 will sort a list in one single thread.
 * Execute2 will sort a list using two threads.
 * Execute3 will sort a list using multiple threads.
 * 
 * @author Pietro Bua & Rohmin Mirza
 *
 */
public class Apl extends Thread
{
	private static int nrOfTests = 10;
	private static int nToGenerate = 50000;
	private static ArrayList<Integer> generated = new ArrayList<Integer>();

	/// Write results to file ///
	private static PrintWriter pr;
	private static ArrayList<String> outputText = new ArrayList<String>();
	private static File file = new File("threads.txt");
	private static PrintWriter pw = null;

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("==== START SCRIPT ====");
		
		outputText.add("Script running with " + nToGenerate + " numbers");
		
		System.out.println("=== Single thread ===");
		outputText.add( "=== Single thread ===" );
		for(int i=0;i<nrOfTests;i++)
		{
			System.out.println("Execution #" + (i+1));
			outputText.add("Execution #" + (i+1)) ;
			execute1();
		}
				
		System.out.println("=== Double thread ===");
		outputText.add("=== Double thread ===");
		for(int i=0;i<nrOfTests;i++)
		{
			System.out.println("Execution #" + (i+1));
			outputText.add("Execution #" + (i+1));
			execute2();
		}
		
		System.out.println("=== Multi thread ===");
		outputText.add("=== Multi thread ===");
		for(int i=0;i<nrOfTests;i++)
		{
			System.out.println("Execution #" + (i+1));
			outputText.add("Execution #" + (i+1));
			execute3();
		}
		
		
		writeToFile(outputText);
		
		System.out.println("==== END SCRIPT ====");
	}
	
	/**
	 * This method will generate a list of numbers and then sort them. Only one
	 * thread is involved.
	 */
	private static void execute1()
	{
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		
		generateNumbers(nToGenerate);
		//printList( generated );

		InsertionSort is = new InsertionSort( generated );
		long t1 = System.nanoTime();
		sorted = is.sort();
		long t2 = System.nanoTime();
		
		//printList( sorted );
		
		if(isSorted(sorted))
		{
			System.out.println( "Time to execute was: " + getTimeDiff(t1,t2 ) + " ms");
			outputText.add("Time to execute was: " + getTimeDiff(t1,t2 ) + " ms");
		}
		else
		{
			System.out.println("ERROR list not sorted");
		}
	}
	
	/**
	 * This method will generate a list of numbers, and split this list in two 
	 * sub-lists. Each sub list will be sorted by a thread (2 threads in total).
	 * When each thread is done with sorting the lists will be merged. 
	 * This method uses the class DoubleThreads and the class Helper.
	 */
	private static void execute2()
	{
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		generateNumbers(nToGenerate);
		//printList( generated );
		
		// Splitting list in two sublists
		ArrayList<Integer> lList = new ArrayList<Integer>();
		ArrayList<Integer> rList = new ArrayList<Integer>();
		
		int halfList = generated.size()/2;

		lList = Helper.copyArrayList(generated,        0, halfList);
		rList = Helper.copyArrayList(generated, halfList, generated.size() );
		
		// Sorting
		long t1 = System.nanoTime();
		
		DoubleThreads lSorter = new DoubleThreads(lList);
		DoubleThreads rSorter = new DoubleThreads(rList);
		
		lSorter.start();
		rSorter.start();
	 	try
	 	{
	 		lSorter.join();
	 		rSorter.join();
	 	}
	 	catch( InterruptedException e )
	 	{
	 		System.out.println("ERROR: Sort>run");
	 	}
	 	
	 	sorted = Helper.mergeLists( lSorter.getList(), rSorter.getList() );
	 	
		long t2 = System.nanoTime();
	 	//printList( resultList );
		if(isSorted(sorted))
		{
			System.out.println( "Time to execute was: " + getTimeDiff(t1,t2 ) + " ms" );
			outputText.add("Time to execute was: " + getTimeDiff(t1,t2 ) + " ms" );
		}
		else
		{
			System.out.println("ERROR list not sorted");
		}
	}
	
	/**
	 * This method will generate a list of numbers, and split this list in two 
	 * sub-lists. Each sub list will be given to a thread which will recursively
	 * split the list in two. This splitting will continue until the list has a
	 * defined smaller length, hen the lists will be sorted and merged with the
	 * one it was divided at. In the end this "tree" of threads will result in a 
	 * sorted list.
	 * This method uses the class MultipleThreads and the class Helper.
	 */
	private static void execute3()
	{
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		generateNumbers(nToGenerate);
		//printList( generated );
		
		// Splitting list in two sublists
		ArrayList<Integer> lList = new ArrayList<Integer>();
		ArrayList<Integer> rList = new ArrayList<Integer>();
		
		int halfList = generated.size()/2;

		lList = Helper.copyArrayList(generated,        0, halfList);
		rList = Helper.copyArrayList(generated, halfList, generated.size() );
		
		// Sorting
		long t1 = System.nanoTime();
		
		MultipleThreads lSorter = new MultipleThreads(lList);
		MultipleThreads rSorter = new MultipleThreads(rList);
		
		lSorter.start();
		rSorter.start();
	 	try
	 	{
	 		lSorter.join();
	 		rSorter.join();
	 	}
	 	catch( InterruptedException e )
	 	{
	 		System.out.println("ERROR: execute3>run");
	 	}
	 	
	 	sorted = Helper.mergeLists( lSorter.getList(), rSorter.getList() );
	 	
		long t2 = System.nanoTime();
	 	//printList( resultList );
		if(isSorted(sorted))
		{
			System.out.println( "Time to execute was: " + getTimeDiff(t1,t2 ) + " ms" );
			outputText.add("Time to execute was: " + getTimeDiff(t1,t2 ) + " ms" );
		}
		else
		{
			System.out.println("ERROR list not sorted");
		}
	}
	
	/**
	 * 
	 * @param aResultList
	 */
	public static void printList( ArrayList<Integer> aResultList )
	{
		for(int i=0; i < aResultList.size(); i++)
		{
			System.out.println( aResultList.get(i) );
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param size
	 */
	public static void generateNumbers( int size )
	{
		generated.clear();
		Random randomGenerator = new Random();
		for(int i=0; i < size ; i++ )
		{
		    int randomValue = randomGenerator. nextInt( Integer.MAX_VALUE );
		    generated.add(randomValue);
		}	
	}
	
	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static long getTimeDiff( long t1, long t2 )
	{
		long elapsedTimeInSeconds  = (t2 - t1) / 1000000;
		return elapsedTimeInSeconds ;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isSorted( ArrayList<Integer> list )
	{
		for( int i=0; i < list.size()-1;i++ )
		{
			int first = list.get(i);
			int second = list.get(i+1);
			
			if(first> second )
			{
				System.out.println( i + "   " + first + " - " + second);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param s
	 */
	public static void writeToFile( ArrayList<String> s )
	{
		try
		{
			pw = new PrintWriter( file );
			
			for(String str: s)
			{
			
			pw.append( str + "\r\n" );
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		pw.close();
	}
}
