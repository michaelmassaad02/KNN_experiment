
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

/* 
Implementation by: Michael Massaad

This class represents the implementation of a Priority Queue using the java.util.PriorityQueue class, which is based on a max heap.
See documentation for more information on the java.util.PriorityQueue class: https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html

*/

@SuppressWarnings("unchecked")
public class PriorityQueue3 implements PriorityQueueIF<LabelledPoint>{

	/**
	 * the number of nearest neighbors we want to find, used to initalize the priority queue capacity
	 */
	private int k; 


	/**
	 * represents the set of points that we are to find the distance from a query point
	 */
	private PointSet pointsS; 

	/**
	 * a point of which we want to find the k nearest neighbors
	 */
	private LabelledPoint queryV;


	/**
	 * represents the size of the priority queue
	 */
	private int size;


	/**
	 * represents the priority queue itself, stored as an object of the PriorityQueue class from java.util
	 */
	private PriorityQueue<LabelledPoint> distLabel;
	
	// creating a nested class that will be used to sort the priority queue based on the distance of the points from the query
	private static class ComparingPoints implements Comparator<LabelledPoint>{

		/**
		*Implementing the compare method from the Comparator interface, used to compare if a point is closer to the query than another
		*
		* @param point1 the point we wish to see if it is closer than another
		* @param point2 the point we compare the point1 to
		* 
		* @return if the point1 is closer to the query, 1 if the distance to the query if closer, -1 if it the same distance or is not closer
		* 
		*/
		public int compare(LabelledPoint point1, LabelledPoint point2){
			if(point1.getKey() < point2.getKey()){
				return 1;
			}
			else{
				return -1;
			}
		}
	}
	
	// class constructor that initializes all the instance variables
	public PriorityQueue3(int kInput, PointSet ps, LabelledPoint query){
		Comparator comparing = new ComparingPoints();

		distLabel = new PriorityQueue<LabelledPoint>(kInput, comparing);
		k = kInput;
		size = 0;
		queryV = query;
		pointsS = ps;

	}


	/**
	*Inserts a point into the priority queue according to the capacity and the point's distance from the query point
	*
	* @param e the point we wish to add to the priority queue
	* @return true if we were able to add the point into the priority queue
	* 
	*/
	public boolean offer(LabelledPoint e){
		e.setKey(e.distanceTo(queryV));

		distLabel.offer(e); // execute the offer method implemented by the java.util.PriorityQueue class
		this.size++;

		// since the offer implemented from the java.util.PriorityQueue class doesn't take into account the desired capacity,
		// we remove the point with the maximum distance from the query if the size is bigger than that capacity (k)
		if(this.size() > k){
			this.poll();

			return true;
		}
		return true;
		
		
	}

    /**
	* Retrieves and removes the point with the furthest distance from the query or returns null if this priority queue is empty.
	*
	* @return the head of the priority queue (the point with the furthest distance from the queue), or null (if PQ is empty)
	* 
	*/
    public LabelledPoint poll(){
    	if (this.isEmpty()){
    		return null;
    	}
    	else{
    		this.size--;
    		return distLabel.poll();
    	}
    }

    /**
	* Retrieves but does not remove the point with the furthest distance from the query or returns null if this priority queue is empty.
	*
	* @return the head of the priority queue (the point with the furthest distance from the priority queue), or null (if PQ is empty)
	* 
	*/
    public LabelledPoint peek(){
    	if (this.isEmpty()){
    		return null;
    	}
    	else{
    		return distLabel.peek();
    	}
    }

    /**
	*
	* Returns the size of the PQ
	* @return the size of the priority queue
	* 
	*/
    public int size(){
    	return size;
    }

    /**
	*Checks if the current PQ is empty.
	*
	*@return if the PQ is empty
	* 
	*/
    public boolean isEmpty(){
    	return size == 0;
    }

    /**
     * Offers all the points from the set of points to the priority queue, and returns the final 
     * ArrayList which contains all k points that are closest to the query. We resort the priority queue
     * before the return since we know that at the first point in the priority queue is the point with the maximum distance from the query
     * 
     * @return the final ArrayList that represents the PQ containing the k nearest points to the query.
     */
    public ArrayList<LabelledPoint> findKNN(){
    	ArrayList<LabelledPoint> setPoints = this.pointsS.getPointsList();
    	ArrayList<LabelledPoint> finalKNN;

    	// offering all the points to the PQ, the only ones that stay are the k nearest to the query, so we set the key as the distance from the query
    	// for the comparison with the points that are already contained in the ArrayList
    	for(int i = 0; i < setPoints.size(); i++){
    		this.offer(setPoints.get(i));
    	}

    	// creating and sorting the new ArrayList using the constructor with a collection as a parameter
    	finalKNN = new ArrayList<LabelledPoint>(this.distLabel);
    	finalKNN.sort(new ComparingPoints());

    	ArrayList<LabelledPoint> inOrderFinalKNN = new ArrayList<LabelledPoint>(this.k);
    	
    	// Input the points into a new ArrayList, starting with the point with the closest distance to the query
    	for(int i = this.size-1; i >= 0; --i){
    		inOrderFinalKNN.add(finalKNN.get(i));
    	}

    	return inOrderFinalKNN;
    }

}