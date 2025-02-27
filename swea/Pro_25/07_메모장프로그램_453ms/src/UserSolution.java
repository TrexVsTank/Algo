
class UserSolution {
	
	// [DECLARATION]
	static class Node {
		int alphabet;
		Node prev;
		Node next;
		
		public Node(int alphabet) {
			this.alphabet = alphabet;
			this.prev = null;
			this.next = null;
		}
	}
	
	static class Link {
		Node first;
		Node last;
		int size;
		int[] counter;
		
		public Link() {
			first = new Node(96);
			last = new Node(96);
			first.next = last;
			last.prev = first;
			size = 0;
			counter = new int[27];
		}
		
		public int get(int index) {
			Node node = first.next;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
			return node.alphabet;
		}
		
		public int query(int start, int alphabet) {
			int sum = 0;
			Node node = first;
			for (int i = 0; i <= start; i++) {
				node = node.next;
			}
			while (node.next != null) {
				if (node.alphabet == alphabet) {
					sum++;
				}
				node = node.next;
			}
			return sum;
		}
		
		public int query(int alphabet) {
			return counter[alphabet-96];
		}
		
		public void add(int index, Node node) {
			Node prev = first;
			for (int i = 0; i < index; i++) {
				prev = prev.next;
			}
			Node next = prev.next;
			node.prev = prev;
			node.next = next;
			prev.next = node;
			next.prev = node;
			size++;
			counter[node.alphabet-96]++;
		}
		
		public Node removeLast() {
			Node node = last.prev;
			Node prev = node.prev;
			prev.next = last;
			last.prev = prev;
			size--;
			counter[node.alphabet-96]--;
			return node;
		}
	}
	
	static int width;
	static int height;
	
	static int cursorRow;
	static int cursorCol;
	
	static Link[] notepad;
	static int length;
	// [DECLARATION]
	
	// 100. INIT
	void init(int H, int W, char mStr[]) {
		width = W;
		height = H;
		
		cursorRow = 0;
		cursorCol = 0;
		
		notepad = new Link[height];
		length = 0;
		
		for (int i = 0; i < height; i++) {
			notepad[i] = new Link();
		}
		
		String input = String.valueOf(mStr).trim();
		for (int i = 0; i < input.length(); i++) {
			char alphabet = input.charAt(i);
			insert(alphabet);
		}
		
		cursorRow = 0;
		cursorCol = 0;
	} // 100. INIT
	
	// 200. INSERT
	void insert(char mChar) {
		Node node = new Node(mChar);
		notepad[cursorRow].add(cursorCol, node);
		
		int index = cursorRow;
		while (width < notepad[index].size) {
			Node lastNode = notepad[index].removeLast();
			notepad[++index].add(0, lastNode);
		}
		
		cursorCol++;
		if(cursorCol == width) {
			cursorCol = 0;
			cursorRow++;
		}
		
		length++;
	} // 200. INSERT
	
	// 300. MOVE CURSOR
	char moveCursor(int mRow, int mCol) {
		// Case 1. Return $
		if (length < (mRow - 1) * width + mCol) {
			cursorRow = length / width;
			cursorCol = length - cursorRow * width;
			return '$';
		}
		
		// Case 2. Return Alphabet
		cursorRow = mRow - 1;
		cursorCol = mCol - 1;
		return (char) notepad[cursorRow].get(cursorCol);
		
	} // 300. MOVE CURSOR
	
	// 400. COUNT CHARACTER
	int countCharacter(char mChar) {
		int maxRow = length / width;
		
		int index = cursorRow;
		
		int sum = 0;
		
		sum += notepad[index++].query(cursorCol,  mChar);
		while(index <= maxRow && index < height) {
			sum += notepad[index].query(mChar);
			index++;
		}
		
		return sum;
	} // 400. COUNT CHARACTER
	
}