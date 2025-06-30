import java.util.Arrays;

public class Main {

	// Main
    public static void main(String[] args) {
        // Stack Test
        MyStack stack = new MyStack();
        stack.push(10);
        stack.push(20);
        System.out.println(stack.pop()); // 20
        System.out.println(stack.pop()); // 10

        // Queue Test
        MyQueue queue = new MyQueue();
        queue.offer(100);
        queue.offer(200);
        System.out.println(queue.poll()); // 100
        System.out.println(queue.poll()); // 200

        // Set Test
        MySet set = new MySet();
        set.add("hello");
        set.add("world");
        set.add("hello");
        System.out.println(set.contains("hello")); // true
        System.out.println(set.contains("java"));  // false

        // Map Test
        MyMap map = new MyMap();
        map.put("a", 1);
        map.put("b", 2);
        System.out.println(map.get("a")); // 1
        System.out.println(map.get("b")); // 2
        System.out.println(map.get("c")); // -1

        // Union-Find Test
        DisjointSet uf = new DisjointSet(10);
        uf.union(1, 2);
        uf.union(2, 3);
        System.out.println(uf.same(1, 3)); // true
        System.out.println(uf.same(1, 4)); // false

        // MinHeap Test
        MinHeap heap = new MinHeap();
        heap.offer(5);
        heap.offer(3);
        heap.offer(9);
        System.out.println(heap.poll()); // 3
        System.out.println(heap.poll()); // 5
        System.out.println(heap.poll()); // 9

        // Trie Test
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        System.out.println(trie.search("apple")); // true
        System.out.println(trie.search("app"));   // true
        System.out.println(trie.search("ap"));    // false
        System.out.println(trie.startsWith("ap")); // true

        // LinkedList Test
        MyLinkedList list = new MyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.print(); // 1 2 3
        list.removeFirst();
        list.print(); // 2 3

        // Deque Test
        MyDeque deque = new MyDeque();
        deque.addLast(10);
        deque.addFirst(5);
        deque.addLast(15);
        System.out.println(deque.removeFirst()); // 5
        System.out.println(deque.removeLast());  // 15

        // PriorityQueue Test
        MyPriorityQueue pq = new MyPriorityQueue();
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        System.out.println(pq.poll()); // 10
        System.out.println(pq.poll()); // 20
        System.out.println(pq.poll()); // 30
        
        int[] arr = {7, 2, 9, 4, 3, 8};
        quickSort(arr, 0, arr.length - 1);
        for (int x : arr) System.out.print(x + " ");  // 2 3 4 7 8 9
    } // Main

    // MyStack
    public static class MyStack {
        int[] data = new int[100];
        int top = -1;
        void push(int val) { data[++top] = val; }
        int pop() { return data[top--]; }
        boolean isEmpty() { return top == -1; }
    } // MyStack

    // MyQueue
    public static class MyQueue {
        int[] data = new int[100];
        int front = 0, rear = 0;
        void offer(int val) { data[rear++] = val; }
        int poll() { return data[front++]; }
        boolean isEmpty() { return front == rear; }
    } // MyQueue

    // MySet
    public static class MySet {
        String[] arr = new String[100];
        int size = 0;
        void add(String s) { if (!contains(s)) arr[size++] = s; }
        boolean contains(String s) {
            for (int i = 0; i < size; i++) if (arr[i].equals(s)) return true;
            return false;
        }
        void remove(String s) {
            for (int i = 0; i < size; i++) {
                if (arr[i].equals(s)) {
                    arr[i] = arr[--size];
                    return;
                }
            }
        }
    } // MySet

    // MyMap
    public static class MyMap {
        String[] keys = new String[100];
        int[] values = new int[100];
        int size = 0;
        void put(String key, int value) {
            for (int i = 0; i < size; i++) {
                if (keys[i].equals(key)) {
                    values[i] = value;
                    return;
                }
            }
            keys[size] = key;
            values[size++] = value;
        }
        int get(String key) {
            for (int i = 0; i < size; i++) if (keys[i].equals(key)) return values[i];
            return -1;
        }
    } // MyMap

    // DisjointSet
    public static class DisjointSet {
        int[] parent;
        DisjointSet(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] == x) return x;
            return parent[x] = find(parent[x]);
        }
        void union(int a, int b) {
            int pa = find(a), pb = find(b);
            if (pa != pb) parent[pb] = pa;
        }
        boolean same(int a, int b) {
            return find(a) == find(b);
        }
    } // DisjointSet

    // MinHeap
    public static class MinHeap {
        int[] heap = new int[100];
        int size = 0;
        void offer(int val) {
            heap[size] = val;
            int i = size++;
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap[p] <= heap[i]) break;
                int tmp = heap[i]; heap[i] = heap[p]; heap[p] = tmp;
                i = p;
            }
        }
        int poll() {
            if (size == 0) return -1;
            int ret = heap[0];
            heap[0] = heap[--size];
            int i = 0;
            while (true) {
                int l = i * 2 + 1, r = i * 2 + 2, s = i;
                if (l < size && heap[l] < heap[s]) s = l;
                if (r < size && heap[r] < heap[s]) s = r;
                if (s == i) break;
                int tmp = heap[i]; heap[i] = heap[s]; heap[s] = tmp;
                i = s;
            }
            return ret;
        }
        boolean isEmpty() { return size == 0; }
    } // MinHeap

    // Trie
    public static class Trie {
        TrieNode root = new TrieNode();
        void insert(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
        boolean search(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (node.children[idx] == null) return false;
                node = node.children[idx];
            }
            return node.isEnd;
        }
        boolean startsWith(String prefix) {
            TrieNode node = root;
            for (int i = 0; i < prefix.length(); i++) {
                int idx = prefix.charAt(i) - 'a';
                if (node.children[idx] == null) return false;
                node = node.children[idx];
            }
            return true;
        }
        static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isEnd;
        }
    } // Trie

    // MyLinkedList
    public static class MyLinkedList {
        Node head = null, tail = null;
        void addLast(int val) {
            Node node = new Node(val);
            if (head == null) head = tail = node;
            else {
                tail.next = node;
                tail = node;
            }
        }
        void removeFirst() {
            if (head != null) head = head.next;
            if (head == null) tail = null;
        }
        void print() {
            for (Node cur = head; cur != null; cur = cur.next) {
                System.out.print(cur.val + " ");
            }
            System.out.println();
        }
        static class Node {
            int val;
            Node next;
            Node(int val) { this.val = val; }
        }
    } // MyLinkedList

    // MyDeque
    public static class MyDeque {
        int[] data = new int[100];
        int front = 50, rear = 50;
        void addFirst(int val) { data[--front] = val; }
        void addLast(int val) { data[rear++] = val; }
        int removeFirst() { return data[front++]; }
        int removeLast() { return data[--rear]; }
        boolean isEmpty() { return front == rear; }
    } // MyDeque

    // MyPriorityQueue
    public static class MyPriorityQueue {
        int[] heap = new int[100];
        int size = 0;
        void offer(int val) {
            heap[size] = val;
            int i = size++;
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap[p] <= heap[i]) break;
                int tmp = heap[i]; heap[i] = heap[p]; heap[p] = tmp;
                i = p;
            }
        }
        int poll() {
            if (size == 0) return -1;
            int ret = heap[0];
            heap[0] = heap[--size];
            int i = 0;
            while (true) {
                int l = i * 2 + 1, r = i * 2 + 2, s = i;
                if (l < size && heap[l] < heap[s]) s = l;
                if (r < size && heap[r] < heap[s]) s = r;
                if (s == i) break;
                int tmp = heap[i]; heap[i] = heap[s]; heap[s] = tmp;
                i = s;
            }
            return ret;
        }
        boolean isEmpty() { return size == 0; }
    } // MyPriorityQueue

    // QuickSort
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivot = arr[(left + right) / 2]; // 중앙 피벗
        int l = left;
        int r = right;

        while (l <= r) {
            while (arr[l] < pivot) l++;     // 좌측에서 피벗보다 큰 값 찾기
            while (arr[r] > pivot) r--;     // 우측에서 피벗보다 작은 값 찾기
            
            if (l <= r) {
                int tmp = arr[l];
                arr[l] = arr[r];
                arr[r] = tmp;
                l++;
                r--;
            }
        }
//        System.out.println(Arrays.toString(arr) + " | " + pivot + " | " + l + " | " + r);

        quickSort(arr, left, r);  // 왼쪽 구간
        quickSort(arr, l, right); // 오른쪽 구간
    } // QuickSort

}
