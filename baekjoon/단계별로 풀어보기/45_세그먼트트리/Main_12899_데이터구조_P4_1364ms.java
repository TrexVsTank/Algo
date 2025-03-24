import java.io.*;
import java.util.*;

public class Main_12899_데이터구조_P4_1364ms {
	
	// [SEGMENT TREE]
	private static class SegmentTree {
		int[] tree;
		int end;
		
		SegmentTree(int num) {
			int height = (int) Math.ceil(Math.log(num) / Math.log(2)) + 1;
			int length = (int) Math.pow(2, height);
			this.tree = new int[length];
			this.end = length / 2;
		}
		
		// INSERT
		void insert(int num, int index, int left, int right) {
			this.tree[index]++;
			if (left == right) {
				return;
			}
			int mid = (left + right) / 2;
			if (num <= mid) {
				insert(num, index * 2, left, mid);
			} else {
				insert(num, index * 2 + 1, mid + 1, right);
			}
		}
		
		// REMOVE
		int remove(int num, int index, int left, int right) {
			this.tree[index]--;
			if (left == right) {
				return left;
			}
			int mid = (left + right) / 2;
			if (num <= this.tree[index * 2]) {
				return(remove(num, index * 2, left, mid));
			} else {
				return(remove(num - this.tree[index * 2], index * 2 + 1, mid + 1, right));
			}
		}
	} // [SEGMENT TREE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		SegmentTree segmentTree = new SegmentTree(2_000_000);
		
		int N = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int order = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			if (order == 1) {
				segmentTree.insert(num, 1, 1, segmentTree.end);
			} else {
				sb.append(segmentTree.remove(num, 1, 1, segmentTree.end)).append("\n");
			}
		}
		
		System.out.println(sb);
	} // [MAIN]
	
}

/*
	3
	2	1
1	1	0	1
 */
