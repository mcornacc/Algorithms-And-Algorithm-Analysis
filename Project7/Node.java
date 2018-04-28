public class Node {
	public int id;
	public int level;
	public double weight, profit, bound;
	public int[] items;
	public Node leftChild, rightChild;	

	public Node (int id, int level, int[] items, double weight, double profit,
		double bound) {
		this.id = id;
		this.level = level;
		this.weight = weight;
		this.items = items;
		this.profit = profit;
		this.bound = bound;
		
	}

	public String toString(){
		String out = "<Node " + id + ": items: ["; 

		for (int i = 0; i <items.length; i++)
			out += items[i] + ((i < items.length - 1) ? ", " : "" );
		
		out += "] level: " + level + " profit: " + profit +
			" weight: " + weight + " bound: " + bound + 
			">";

		return out;
		
	}

}
