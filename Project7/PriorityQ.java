public class PriorityQ {
	private Node [] heap;

	public PriorityQ(Node initial) {
		heap = new Node[2];
		heap[1] = initial;
	}

	public void add(Node newNode) {
		int freeLeaf = 0; 
		for (int i = 1; i < heap.length; i++) {
			if (heap[i] == null) {
				freeLeaf = i;
				break; 
			}

		}

		if (freeLeaf == 0) {
			freeLeaf = heap.length;
			expandHeap();
			
		}
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

	public Node pop() {
		Node popped = heap[1];
		heap[1] = null;
		// find last node
		for (int i = 2; i < heap.length; i++) {
			if (heap[i] == null) {
				heap[1] = heap[i - 1];
				heap[i-1] = null;
				break;
			}
		}

		boolean swapped = false;
		int parent = 1;

		do {
			if ( (2 * parent < heap.length && heap[2*parent] != null && heap[2*parent].bound > heap[parent].bound) || (2 * parent + 1 < heap.length && heap[2*parent + 1] != null && heap[2*parent + 1].bound > heap[parent].bound )) {
				swapped= true;
				int greater = 2 * parent + ((2 * parent + 1 < heap.length && heap[2*parent + 1] != null && heap[2*parent + 1].bound > heap[2*parent].bound) ? 1 : 0);
				Node temp = heap[parent];
				heap[parent] = heap[greater];
				heap[greater] = temp;
				parent = greater; 
			}
			else {
				swapped = false;
			}			

		} while (swapped);


		return popped;

	} 

	public void printHeap() {
		for (int i = 1; i < heap.length; i++) {
			if (heap[i] == null) break;
			System.out.println(heap[i].toString());
		} 
		return;
	}
}
