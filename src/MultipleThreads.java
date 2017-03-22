import java.util.ArrayList;

/**
 * This class get's a list which has to be sorted. This list is divided in two
 * and each half is given at one thread, which is again MultipleThreads class.
 * This continues until the list to be sorted is of a small amount we define,
 * after this, the list is sorted and then merged.
 * 
 * @author Pietro Bua & Rohmin Mirza
 *
 */
public class MultipleThreads extends Thread
{
	private InsertionSort is;
	private ArrayList<Integer> myList = new ArrayList<Integer>();

	/**
	 * 
	 * @param list
	 */
	public MultipleThreads( ArrayList<Integer> list )
	{
		myList = list;
		is = new InsertionSort( myList );
	}
	
	/**
	 * 
	 */
	public void run()
	{
		if( myList.size() > 1000 )
		{
			int halfList = myList.size() / 2;
			
			ArrayList<Integer> lList = new ArrayList<Integer>();
			ArrayList<Integer> rList = new ArrayList<Integer>();
			lList = Helper.copyArrayList( myList,        0, halfList );
			rList = Helper.copyArrayList( myList, halfList, myList.size() );
			
			MultipleThreads t1 = new MultipleThreads( lList );
			MultipleThreads t2 = new MultipleThreads( rList );
		 	
		 	t1.start();
		 	t2.start();
		 	try
		 	{
		 		t1.join();
		 		t2.join();
		 	}
		 	catch( InterruptedException e )
		 	{
		 		System.out.println("ERROR: Sort>run");
		 	}
		 	
		 	myList = Helper.mergeLists( t1.getList(), t2.getList() );
		 	//assert myList.size() == t1.getList().size() + t2.getList().size():"mergefout";
		}
		else
		{
			is.sort();
		}
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
