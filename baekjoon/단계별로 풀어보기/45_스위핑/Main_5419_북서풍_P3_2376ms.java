import java.io.*;
import java.util.*;

public class Main_5419_북서풍_P3_2376ms {
	
	// [POINT]
	private static class Point implements Comparable<Point> {
		int x;
		int y;
		
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int compareTo(Point o) {
			if (this.y == o.y) {
				return o.x - this.x;
			}
			return this.y - o.y;
		}
	}
	// [POINT]
	
	// [SEGMENT TREE]
	private static class SegmentTree {
		int[] tree;
		int last;
		int length;
		
		SegmentTree(int n) {
			int height = (int) Math.ceil(Math.log(n) / Math.log(2));
			this.length = (int) Math.pow(2, height + 1);
			this.tree = new int[length];
			this.last = (int) Math.pow(2,  height);
		}
		
		void insert(int num) {
			this.insert(num, 1, 1, this.last);
		}
		void insert(int num, int index, int left, int right) {
			tree[index]++;
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
		
		int rightSum(int num) {
			return this.rightSum(num, 1, 1, this.last);
		}
		int rightSum(int num, int index, int left, int right) {
			int result = 0;
			if (left == right) {
				return this.tree[index];
			}
			int mid = (left + right) / 2;
			if (num <= mid) {
				result += rightSum(num, index * 2, left, mid) + this.tree[index * 2 + 1];
			} else {
				result += rightSum(num, index * 2 + 1, mid + 1, right);
			}
			return result;
		}
	} // [SEGMENT TREE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		// TEST CASE
		for (int t = 0; t < T; t++) {
			long count = 0;
			
			List<Point> pointList = new ArrayList<>();
			
			TreeSet<Integer> xSet = new TreeSet<>();
			
			Map<Integer, Integer> xMap = new HashMap<>();
			
			int N = Integer.parseInt(br.readLine());
			for (int n = 0; n < N; n++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				Point point = new Point(x, y);
				pointList.add(point);
				xSet.add(x);
			}
			Collections.sort(pointList);
			
			int xIndex = 0;
			for (int x : xSet) {
				xMap.put(x, ++xIndex);
			}
			
			for (Point point : pointList) {
				point.x = xMap.get(point.x);
			}
			
			SegmentTree sTree = new SegmentTree(xIndex);
			
			for (Point point : pointList) {
				int x = point.x;
				count += sTree.rightSum(x);
				sTree.insert(x);
			}
			
			sb.append(count).append("\n");
		}// TEST CASE
		
		System.out.println(sb);
		
	} // [MAIN]
}