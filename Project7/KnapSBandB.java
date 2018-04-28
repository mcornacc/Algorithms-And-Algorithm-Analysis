import java.io.*;
import java.util.*;

public class KnapSBandB { 
	
	private static double maxValue;
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
		maxValue = 0.0;
		Node baseNode = new Node(1, 0, new int[0], 0.0, 0.0, determineBound(0, 0.0, 0.0));
		nodeCount = 1;
		PriorityQ pQ = new PriorityQ(baseNode);

		Node leftChild = generateNode(baseNode, false);
		Node rightChild = generateNode(baseNode, true);
		pQ.add(leftChild);
		pQ.add(rightChild);
		System.out.println("\n\n");
		pQ.printHeap();
		
		Node fakie = new Node(1337, 30, new int[0], 0.0, 0.0, 500);
		System.out.println("\n\n");		

		pQ.add(fakie);
	
		pQ.printHeap();
                System.out.println("\n\n");
		Node fakie2 = new Node(1338, 30, new int[0], 0.0, 0.0, 700); 
		pQ.add(fakie2);
		pQ.printHeap();

		System.out.println("Begin exploration of the possibilities tree:");
	}

 
} 
