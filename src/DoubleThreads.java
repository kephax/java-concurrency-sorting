import java.util.*;

/**
 * This class get's a list as parameter and will sort this list.
 * 
 * @author Pietro Bua & Rohmin Mirza
 *
 */
public class DoubleThreads extends Thread
{
	private InsertionSort is;
	private ArrayList<Integer> myList = new ArrayList<Integer>();
	
	/**
	 * 
	 * @param list
	 */
	public DoubleThreads( ArrayList<Integer> list )
	{
		myList = list;
		is = new InsertionSort(myList);
		
	}
	
	/**
	 * 
	 */
	public void run()
	{
		is.sort();
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer> getList()
	{
		return myList;
	}	
}
