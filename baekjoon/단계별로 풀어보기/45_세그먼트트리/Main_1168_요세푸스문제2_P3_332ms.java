import java.io.*;
import java.util.*;

public class Main_1168_요세푸스문제2_P3_332ms {
	
	static StringBuilder sb = new StringBuilder();
	static int N;
	static int Left;
	static int Right;
	static int Remain;
	
	// [SEGMENT TREE]
	private static class SegmentTree{
		int[] tree;
		int height;
		int length;
		int end;
		
		// Constructor
		SegmentTree(int n) {
			this.height = (int) Math.ceil(Math.log(n) / Math.log(2) + 1);
			this.length = (int) Math.pow(2, height);
			this.tree = new int[this.length];
			this.end = this.length / 2;
		}
		
		// Insert
		void insert(int n) {
			insert(n, 1, 1, this.end);
		}
		void insert(int n, int index, int left, int right) {
			this.tree[index]++;
			if (left == right) {
				return;
			}
			int mid = (left + right) / 2;
			if (n <= mid) {
				insert(n, index * 2, left, mid);
			} else {
				insert(n, index * 2 + 1, mid + 1, right);
			}
		}
		
		// Remove
		void remove(int n) {
			Left = 0;
			Right = 0;
			remove(n, 1, 1, this.end);
		}
		void remove(int n, int index, int left, int right) {
			this.tree[index]--;
			if (left == right) {
				sb.append(left);
				return;
			}
			int mid = (left + right) / 2;
			if (n <= mid) {
				Right += this.tree[index * 2 + 1];
				remove(n, index * 2, left, mid);
			} else {
				Left += this.tree[index * 2];
				remove(n, index * 2 + 1, mid + 1, right);
			}
			return;
		}
		
		// Find
		int find(int n) {
			return find(n, 1, 1, this.end);
		}
		int find(int remain, int index, int left, int right) {
			if (left == right) {
				return left;
			}
			int mid = (left + right) / 2;
			if (remain <= this.tree[index * 2]) {
				return find(remain, index * 2, left, mid);
			} else {
				return find(remain - this.tree[index * 2], index * 2 + 1, mid + 1, right);
			}
		}
	} // [SEGMENT TREE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		SegmentTree Stree = new SegmentTree(N);
		
		for (int i = 1; i <= N; i++) {
			Stree.insert(i);
		}
		
		Remain = N;
		Left = N;
		Right = 0;
		
		sb.append("<");
		for (int i = 0; i < N; i++) {
			int nextTo = K % Remain;
			int target = Right < nextTo ? nextTo - Right : Left + nextTo;
			if (target == 0) {
				target = Remain;
			}
			int killed = Stree.find(target);
			Stree.remove(killed);
			Remain--;
//			System.out.println("사망자 : " + killed + " | Left : " + Left + " | Right " + Right + " | Remain : " + Remain + " | nextTo : " + nextTo + " | target : " + target);
			if (Remain != 0) {
				sb.append(", ");
			}
		}
		sb.append(">");
		System.out.println(sb);
	} // [MAIN]

}