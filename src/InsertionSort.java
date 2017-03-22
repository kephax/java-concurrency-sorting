import java.util.*;

public class InsertionSort
{
	private ArrayList<Integer> myList;
	
	public InsertionSort( ArrayList<Integer> list )
	{
		myList = list;
	}
	
	
	public ArrayList<Integer> sort()
	{
		ArrayList<Integer> arr = myList;

        int j;

        for (int i = 0; i < arr.size(); i++) {
            int temp = arr.get(i);
            j = i;
            while (j > 0 && temp <= arr.get(j - 1)) {
                arr.set( j,  arr.get(j - 1) ) ;
                j--;
            }
            arr.set(j, temp );
        }
        
        return arr;
        
        /*
         	ArrayList<Integer> arr = myList;

        int j;

        for (int i = 1; i < arr.size(); i++) {
            int temp = arr.get(i);
            j = i;
            while (j > 0 && temp <= arr.get(j - 1)) {
                arr.set( j,  arr.get(j - 1) ) ;
                j--;
            }
            arr.set(j, temp );
        }
        
        return arr;
        */
         
	}
}
