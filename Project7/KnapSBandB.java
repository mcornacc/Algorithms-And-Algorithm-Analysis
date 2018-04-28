import java.io.*;
import java.util.*;

public class KnapSBandB { 
	
	private static double inventory [][]; 
	private static double maxWeight;
	private static int nodeCount; 	

	public static void main (String [] args) throws Exception {
		collectData();
		solve();
	}

	private static void collectData() throws Exception {
		Scanner console = new Scanner(System.in);
		System.out.println("Please enter the name of the data file:\n");
		File inputFile = new File(console.next());
		BufferedReader bReader = null;

		try {
			bReader = new BufferedReader(new FileReader(inputFile));
		}
		catch(Exception exc) {
			System.out.println("Data file not found!");
			System.exit(0);
		}

		maxWeight = Double.parseDouble(bReader.readLine());
		System.out.println("\nCapacity of knapsack is " + maxWeight);

		int numItems = Integer.parseInt(bReader.readLine());
		inventory = new double[numItems][3];
		System.out.println("Items are:");

		for (int i = 0; i < numItems; i++) {
			String[] line = bReader.readLine().split(" ");

			for (int k = 0; k < 2; k++)  
			inventory[i][k] = Double.parseDouble(line[k]);	
			
			System.out.println((i + 1) + ": " + inventory[i][0] +
				" " + inventory[i][1]);

			inventory[i][2] = inventory[i][0] / inventory[i][1];
								
		}
		
	}

	private static Node generateNode(Node parent, boolean includeNextItem) {
		nodeCount++;
		int id = nodeCount;
		double weight = parent.weight + ((includeNextItem) ? inventory[parent.level][1]  : 0.0);
		double profit = parent.profit + ((includeNextItem) ? inventory[parent.level][0] : 0);	
	
		int level = parent.level + 1;
		int [] items = parent.items;

		if (includeNextItem) { 
			items = new int[items.length + 1];
			for (int i = 0; i < items.length - 1; i++)
				items[i] = parent.items[i];

			items[items.length - 1] = parent.level + 1;
			 						
		}

		double bound = determineBound(level, weight, profit);

		return new Node(id, level, items, weight, profit, bound);
	} 

	private static double determineBound(int level, double weight, double profit) {
		double bound = profit;

		for (int i = level; i < inventory.length && weight < maxWeight; i++) {
			double amount = (inventory[i][1] < maxWeight - weight) ? inventory[i][1] : (maxWeight - weight);
			weight += amount; 
			bound += inventory[i][2] * amount; 
		}

		return bound;
 
	} 

	private static void solve() {
		Node baseNode = new Node(1, 0, new int[0], 0.0, 0.0, determineBound(0, 0.0, 0.0));
		nodeCount = 1;
		PriorityQ pQ = new PriorityQ(baseNode);

		System.out.println("Begin exploration of the possibilities tree:");

		Node topNode = baseNode;

		while (!pQ.isEmpty()) {
			// pQ.printHeap();
			
			Node current = pQ.pop();
			System.out.println("\nExploring " + current.toString());

			// check if worthless node
			if (current.bound < topNode.profit) {
				System.out.println("\tpruned, don't explore children" +
					" because bound " + current.bound + " is smaller" +
					" than known achievable profit " + topNode.profit);
				continue;		
			}
			
			//generate children
			for (int i = 0; i < 2; i++) {
				Node currentChild = generateNode(current, (i == 1));
				System.out.println("\t" + ((i==0) ? "Left" : "Right") + 
					 " child is " + currentChild.toString());
				if (currentChild.weight > maxWeight) {
					System.out.println("\t\tpruned because too heavy");
					continue;	
				} 
				else if (currentChild.weight == maxWeight) {
					System.out.println("\t\thit capacity exactly so" +
						" don't explore further");
				}
				else {
					System.out.println("\t\texplore further");
					pQ.add(currentChild);
				}

				if (currentChild.profit > topNode.profit) {
					System.out.println("\t\tnote achievable profit of " + currentChild.profit);
					topNode = currentChild;
				}
			}
				
		}
		System.out.println("\nBest node: " +  topNode.toString()); 
	} 
} 
