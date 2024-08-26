
import java.util.ArrayList;

/* 
Implementation by: Michael Massaad

This class represents the implementation of a Priority Queue using an ArrayList.

*/
public class PriorityQueue1 implements PriorityQueueIF<LabelledPoint>{

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
	 * represents the current size of the priority queue
	 */
	private int size;

	/**
	 * represents the priority queue itself, stored as an ArrayList
	 */
	private ArrayList<LabelledPoint> distLabel;

	// class constructor that initializes all the instance variables
	public PriorityQueue1(int kInput, PointSet ps, LabelledPoint query){
		this.distLabel = new ArrayList<LabelledPoint>(kInput);
		this.k = kInput;
		this.size = 0;
		this.queryV = query;
		this.pointsS = ps;

	}

	/**
	*Inserts a point into the priority queue according to the capacity and the point's distance from the query point
	*
	* @param e the point we wish to add to the priority queue
	* @return true if we were able to add the point into the priority queue
	* 
	*/
	public boolean offer(LabelledPoint e){

		// if the PQ is empty, we just add the point at the first position
		if(this.isEmpty()){
			distLabel.add(e);
			this.size++;
			return true;
		}

		// if the PQ has reached its total capacity, we compare the point we want to insert with the ones in the PQ already
		// if the point has a distance thats closer than any of the points (which we set as the key defined in the LabelledPoint class, 
		// we remove the one with the max distance and we place the point

		else if(this.size() == this.k){
			for(int i = 0; i < this.size; i++){
			LabelledPoint inQ = distLabel.get(i);
			if(e.getKey() < inQ.getKey()){
				distLabel.remove(this.size()-1);
				distLabel.add(i, e);
				return true;
				}
			}
			return false;
		}

		// based off the previous lines, we know there's room in queue, so we compare the point with the points already in queue
		// if the distance is closer (comparing the keys) to the query than any of the points, we place it before the corresponding point
		else { 
			for(int i = 0; i < this.size; i++){
				LabelledPoint inQ = distLabel.get(i);
				if(e.getKey() < inQ.getKey()){
					distLabel.add(i, e);
					this.size++;
					return true;
				}
			}

		// we have reached the last index of the PQ and we are not able to place it in a previous index, so we add it at the end of
		// the queue
			distLabel.add(e);
			this.size++; 
			return true;
		}
	}

    /**
	* Retrieves and removes the point with the furthest distance from the query or returns null if this priority queue is empty.
	*
	* @return the head of the priority queue (the point with the furthest distance from the priority queue), or null (if PQ is empty)
	* 
	*/
    public LabelledPoint poll(){
    	if (this.isEmpty()){
    		return null;
    	}
    	else{
    		LabelledPoint head = distLabel.get(this.size -1);
    		distLabel.remove(this.size -1);
    		this.size--;
    		return head;
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
    		LabelledPoint head = distLabel.get(this.size -1);
    		return head;
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
    	return (this.size == 0);
    }

    /**
     * Offers all the points from the set of points to the PQ (ArrayList), and returns the final 
     * ArrayList which contains all k points that are closest to the query.
     * 
     * @return the final ArrayList that represents the PQ containing the k nearest points to the query.
     */
    public ArrayList<LabelledPoint> findKNN(){
    	ArrayList<LabelledPoint> setPoints = this.pointsS.getPointsList();

    	// offering all the points to the PQ, the only ones that stay are the k nearest to the query, so we set the key as the distance from the query
    	// for the comparison with the points that are already contained in the ArrayList
    	for(int i = 0; i < setPoints.size(); i++){
    		setPoints.get(i).setKey(setPoints.get(i).distanceTo(queryV));
    		this.offer(setPoints.get(i));
    	}

    	return distLabel;
    }

}