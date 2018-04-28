public class PriorityQ {
	private Node [] heap;

	public PriorityQ(Node initial) {
		heap = new Node[2];
		heap[1] = initial;
	}

	public void add(Node newNode) {
		int freeLeaf = 0; 
		for (int i = heap.length / 2 + 1; i < heap.length; i++) {
			if (heap[i] != null)  {
				continue;
			}
			else {
				freeLeaf = i; 
			}

		}

		if (freeLeaf == 0) {
			freeLeaf = heap.length;
			expandHeap();
			
		}
		System.out.println("\nfree leaf at" + freeLeaf + "\n"); 
		heap[freeLeaf] = newNode;
		boolean swapped = false;
		int child = freeLeaf;
		do {
			/* System.out.println("\n");
			printHeap();
			System.out.println("Child : " + child ); */
			if ( heap[child].bound > heap[child / 2].bound ) {
				Node temp = heap[child / 2];
				heap[child / 2] = heap[child];
				heap[child] = temp;
				swapped = true;
			}
			else {
				swapped = false;
			}

			child = child / 2;
		}
		while(swapped && child > 1);
	}
	
	private void expandHeap() {
		Node [] newHeap = new Node[heap.length * 2];
		for (int i = 0; i < heap.length; i++) {
			newHeap[i] = heap[i];
		}
		heap = newHeap;
		return;
	}

/*	public Node pop() {
			
	} */

	public void printHeap() {
		for (int i = 1; i < heap.length; i++) {
			if (heap[i] == null) break;
			System.out.println(heap[i].toString());
		} 
		return;
	}
}
