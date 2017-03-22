import java.util.ArrayList;

/**
 * This class contains some help methods which are used by the threads.
 * 
 * 
 * @author Pietro Bua & Rohmin Mirza
 *
 */
public class Helper
{
	/**
	 * 
	 * @param aList
	 * @param begin
	 * @param end
	 * @return
	 */
	public static ArrayList<Integer> copyArrayList( ArrayList<Integer> aList, int begin, int end )
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for( int z = begin; z < end;z++)
		{
			tmp.add( aList.get( z ) );
		}
		return tmp;
	}

	/**
	 * 
	 * @param leftList
	 * @param rightList
	 * @return
	 */
	public static ArrayList<Integer> mergeLists( ArrayList<Integer> leftList, ArrayList<Integer> rightList )
	{
		ArrayList<Integer> resultList = new ArrayList<Integer>();
	
		int size = leftList.size() + rightList.size();
	
		for( int i=0; i < size; i++ )
		{
			int z = 0;

			if( ( leftList.size() > 0 )&&( rightList.size() > 0) )
			{
				if( leftList.get(z) < rightList.get(z) )
				{
					resultList.add( leftList.get(z) );
					leftList.remove(z);
				}
				else if( leftList.get(z) > rightList.get(z) )
				{
					resultList.add( rightList.get(z) );
					rightList.remove(z);
				}
				else if( leftList.get(z) == rightList.get(z) )
				{
					resultList.add( leftList.get(z) );
					resultList.add( rightList.get(z) );
					leftList.remove(z);
					rightList.remove(z);					
				}
			}
			else if( leftList.size() > 0 )
			{
				for( int j=0; j < leftList.size(); j++ )
				{
					resultList.add( leftList.get(z) );
					leftList.remove(z);
				}
			}
			else if( rightList.size() > 0 )
			{
				for( int k=0; k < rightList.size(); k++ )
				{
					resultList.add( rightList.get(z) );
					rightList.remove(z);
				}
			}
		}
		return resultList;
	}	
}
