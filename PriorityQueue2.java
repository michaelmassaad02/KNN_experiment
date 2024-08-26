
import java.util.ArrayList;
import java.util.Comparator;

/* 
Implementation by: Michael Massaad

This class represents the implementation of a Priority Queue using an ArrayList implementing a max heap.

*/
public class PriorityQueue2 implements PriorityQueueIF<LabelledPoint>{

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
	 * represents the priority queue itself, stored as an ArrayList thats representing a max heap
	 */
	private ArrayList<LabelledPoint> distLabel;

	// creating a nested class that will be used to sort the ArrayList based on the distance of the points from the query
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
	public PriorityQueue2(int kInput, PointSet ps, LabelledPoint query){
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
		e.setKey(e.distanceTo(queryV));

		// if the PQ is empty, we just add the point at the first position
		if(this.isEmpty()){
			distLabel.add(0, e);
			this.size++;
			return true;
		}

		// we add it to the priority queue and then we organise the order based on the distance from the query (stored already in the point's key) 
		// using the upheap method. if we have a size that is greater than the capacity wanted, we remove the root/head of the heap, which is
		// the point with the max distance from the query

		else {
				distLabel.add(e);
				this.upheap(this.size());
				this.size++;
				if(this.size > this.k){
					this.poll();
				}
				return true;
		}

	}

    /**
	* Retrieves and removes the point with the furthest distance from the query or returns null if this priority queue is empty.
	*
	* @return the root/head of the priority queue (the point with the furthest distance from the priority queue), or null (if PQ is empty)
	* 
	*/
    public LabelledPoint poll(){
    	if (this.isEmpty()){
    		return null;
    	}
    	else{
    		// we swap the point with the max distance (situated at index 0) with the point with the closest distance, then we
    		// remove the point with the max distance, which is now situation at the end of the ArrayList, and we return it at
    		// the end of the method. then we organise the order using the downheap method from the first index of the ArrayList 
    		// (which is the point with the closest distance). This is similar to the remove method from the heap implementation. 

    		LabelledPoint head = distLabel.get(0);
    		LabelledPoint temp1 = head;
            LabelledPoint temp2 = distLabel.get(this.size()-1);
            distLabel.set(0, temp2);
            distLabel.set(this.size()-1, temp1);
    		distLabel.remove(this.size()-1);
    		this.size--;
    		this.downheap(0);

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
    		LabelledPoint head = distLabel.get(0);
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
    	return size == 0;
    }

    /**
	*
	* This is similar to the upheap implementation from the java implementation of this method provided in the brightspace.
	* It organises the points based off of the distances from the query point, where we want the point with the maximum distance
	* at the head
	* 
	*/
    private void upheap(int i){
    	while(i > 0){
    		int parent;

    		parent = (i-1)/2;
            
            if(distLabel.get(i).getKey() < distLabel.get(parent).getKey()){
                break;
            }
            LabelledPoint temp1 = distLabel.get(i);
            LabelledPoint temp2 = distLabel.get(parent);
            distLabel.set(i, temp2);
            distLabel.set(parent, temp1);
            i = parent;
         }          
    

    }

    /**
	*
	* This is similar to the downheap implementation from the java implementation of this method provided in the brightspace.
	* It organises the points based off of the distances from the query point, where we want the point with the maximum distance
	* at the head
	* 
	*/
    private void downheap(int i){

    	while(2*i +1 < this.size()){
    		int leftIndex = 2*i + 1;
    		int bigChildIndex = leftIndex;

    		if(2*i +2 < this.size()){
    			int rightIndex = 2*i +2;
    			if( distLabel.get(leftIndex).getKey() < distLabel.get(rightIndex).getKey()){
    				bigChildIndex = rightIndex;
    			}

    		}
    		if(distLabel.get(bigChildIndex).getKey() < distLabel.get(i).getKey()){
    			break;
    		}

    		LabelledPoint temp1 = distLabel.get(i);
            LabelledPoint temp2 = distLabel.get(bigChildIndex);
            distLabel.set(i, temp2);
            distLabel.set(bigChildIndex, temp1);
            i = bigChildIndex;

    	}
    }


    /**
     * Offers all the points from the set of points to the PQ (ArrayList), and returns the final 
     * ArrayList which contains all k points that are closest to the query. We resort the priority ArrayList
     * before the return since we know that at the first index is the point with the maximum distance from the query
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

    	// Sorting the ArrayList to make sure that the points are properly placed
    	distLabel.sort(new ComparingPoints());

    	ArrayList<LabelledPoint> finalKNN = new ArrayList<LabelledPoint>(this.k);
    	
    	// Input the points into a new ArrayList, starting with the point with the closest distance to the query
    	for(int i = this.size-1; i >= 0; --i){
    		finalKNN.add(distLabel.get(i));
    	}
    	return finalKNN;
    }

}