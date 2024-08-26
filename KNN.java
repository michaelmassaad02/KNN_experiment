import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

/* 
Implementation by: Michael Massaad

This class represents the implementation of finding the k nearest neighbors for a given set of query points from a given set of points. 

*/


public class KNN{

	/**
	 * the number of nearest neighbors we want to find, used to initalize the priority queue capacity
	 */
	int k;

	public KNN(){

	}

	/**
	 * Sets the value of k
	 * 
	 * @param kVal the value of k desired
	 */
	public void setK(int kVal){
		this.k = kVal;

	}

	/**
	 * Execution of the find KNN algorithm for a certain set of query points, and writes in a seperate file the KNN for each query
	 * 
	 * @param args contains the information of what version of PQ to implement, the value of k, the set of points, and the set of queries
	 */
	public static void main(String[] args){

		// when executing the class, the user inputs the version, k value, the set of points and the set of query points
		// example: java KNN 1 10 sift_base.fvecs siftsmall_query.fvecs 
		// so we can access all the information through the args parameter and initialize the variables needed

		int version = Integer.parseInt(args[0]); // what PQ version we want to execute

		int k = Integer.parseInt(args[1]); // how many nearest neighbors do we want

		PointSet points = new PointSet(PointSet.read_ANN_SIFT(args[2])); // the points from which we find the nearest neighbors

		PointSet queries = new PointSet(PointSet.read_ANN_SIFT(args[3])); // the query points for which we find the nearest neighbors

		int num_queries = 100; // this is the amount of query vectors that we are working with for this assignment

		ArrayList<LabelledPoint> kNearestN; // ArrayList that contains the nearest neighbors for a certain query

		double timeElapsed = 0.0; // computing the total time it takes to execute the KNN algorithm for the queries

		try{
		// creating a new file writer that will write all of the KNN for each query
		FileWriter f = new FileWriter("knn" + "_" + version  + "_" + k + "_" + num_queries + "_" + " 1000000.txt");

		// accessing each of the query points one by one and executing the findKNN method for the appropriate PQ version
		if(version == 1){
			ArrayList<LabelledPoint> queryArray = queries.getPointsList();

			for(int i = 0; i < 100; i++){

			PriorityQueue1 pq1 = new PriorityQueue1(k, points, queryArray.get(i));

			double start = System.currentTimeMillis();
			kNearestN = pq1.findKNN();
			double end = System.currentTimeMillis();

			timeElapsed += end - start; // taking note of how much time it took to implement the findKNN method for the PriorityQueue1 implementation


			StringBuilder str = new StringBuilder(i + ": "); // implementing the stringbuilder to write the KNN of the current query point in the desired format

			for(int j = 0; j < kNearestN.size()-1; j++){

				str.append(kNearestN.get(j).getLabel() + ", ");


			}

			str.append(kNearestN.get(kNearestN.size()-1).getLabel());


			f.write(str.toString());
			f.write("\n");
			System.out.println(str.toString());
			}	

			// printing the time it took to execute the findKNN for all 100 query points using PriorityQueue1
			System.out.println("Total running time for PQ" + version + " to find " + k +" nearest neighbours of 100 queries = " + timeElapsed + " milliseconds");
			f.close();
		}

		else if (version == 2){
			ArrayList<LabelledPoint> queryArray = queries.getPointsList();

			for(int i = 0; i < 100; i++){

			PriorityQueue2 pq2 = new PriorityQueue2(k, points, queryArray.get(i));

			double start = System.currentTimeMillis();
			kNearestN = pq2.findKNN();
			double end = System.currentTimeMillis();

			timeElapsed += end - start; // taking note of how much time it took to implement the findKNN method for the PriorityQueue2 implementation


			StringBuilder str = new StringBuilder(i + ": "); // implementing the stringbuilder to write the KNN of the current query point in the desired format

			for(int j = 0; j < kNearestN.size()-1; j++){
				str.append(kNearestN.get(j).getLabel() + ", ");

			}
			str.append(kNearestN.get(kNearestN.size()-1).getLabel());
			
			f.write(str.toString());
			f.write("\n");
			System.out.println(str.toString());
			}	

			// printing the time it took to execute the findKNN for all 100 query points using PriorityQueue2
			System.out.println("Total running time for PQ" + version + " to find " + k +" nearest neighbours of 100 queries = " + timeElapsed + " milliseconds");
			f.close();
		}

		else if( version == 3){
			ArrayList<LabelledPoint> queryArray = queries.getPointsList();

			for(int i = 0; i < 100; i++){

			PriorityQueue3 pq3 = new PriorityQueue3(k, points, queryArray.get(i));

			double start = System.currentTimeMillis();
			kNearestN = pq3.findKNN();
			double end = System.currentTimeMillis();

			timeElapsed += end - start; // taking note of how much time it took to implement the findKNN method for the PriorityQueue3 implementation

			StringBuilder str = new StringBuilder(i + ": ");// implementing the stringbuilder to write the KNN of the current query point in the desired format

			for(int j = 0; j < kNearestN.size()-1; j++){
				str.append(kNearestN.get(j).getLabel() + ", ");

			}
			str.append(kNearestN.get(kNearestN.size()-1).getLabel());
			
			f.write(str.toString());
			f.write("\n");
			System.out.println(str.toString());
			}	

			// printing the time it took to execute the findKNN for all 100 query points using PriorityQueue3
			System.out.println("Total running time for PQ" + version + " to find " + k +" nearest neighbours of 100 queries = " + timeElapsed + " milliseconds");
			f.close();
		}

		else{
			System.out.println("Invalid version input"); 

		}
	
		
		}
		catch(IOException exception){
			System.out.println("There is an error, here is the stack trace");
			exception.printStackTrace();
		}

}

}