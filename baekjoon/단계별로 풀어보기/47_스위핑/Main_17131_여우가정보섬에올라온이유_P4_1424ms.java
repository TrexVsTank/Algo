import java.io.*;
import java.util.*;

public class Main_17131_여우가정보섬에올라온이유_P4_1424ms {
	
	static int BIGINT = 1_000_000_007;
	
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
			};
			return o.y - this.y;
		}
	} // [POINT]
	
	// [SEGMENT TREE]
	private static class SegmentTree {
		long[] tree;
		int end;
		SegmentTree(int length) {
			int height = (int) Math.ceil(Math.log(length) / Math.log(2));
			int size = (int) Math.pow(2, height + 1);
			this.tree = new long[size];
			this.end = size / 2;
		}
		void insert(int num) {
			insert(num, 1, 1, end);
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
		long count(int num) {
			long leftSum = sum(1, num - 1, 1, 1, this.end);
			long rightSum = sum(num + 1, this.end, 1, 1, this.end);
			return (leftSum * rightSum) % BIGINT;
		}
		long sum(int start, int end, int index, int left, int right) {
			long result = 0;
			
			if (end < start || end < left || right < start) {
				return 0;
			}
			
			if (start <= left && right <= end) {
				return tree[index];
			}
			
			int mid = (left + right) / 2;
			
			result += sum(start, end, index * 2, left, mid);
			result += sum(start, end, index * 2 + 1, mid + 1, right);
			
			return result;
		}
	} // [SEGMENT TREE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		List<Point> pointList = new ArrayList<>();
		TreeSet<Integer> xSet = new TreeSet<>();
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			Point point = new Point(x, y);
			pointList.add(point);
			xSet.add(x);
		}
		Collections.sort(pointList);
		
		Map<Integer, Integer> xMap = new HashMap<>();
		int xIndex = 0;
		for (int x : xSet) {
			xMap.put(x, ++xIndex);
		}
		for (Point point : pointList) {
			point.x = xMap.get(point.x);
		}
		
		SegmentTree sTree = new SegmentTree(xIndex);
		
		int pointIndex = 0;
		int currentY = 200_001;
		List<Integer> xList;
		
		long answer = 0;
		
		while(pointIndex < N) {
			Point currentPoint = pointList.get(pointIndex);
			currentY = currentPoint.y;
			
			xList = new ArrayList<>();
			
			xList.add(currentPoint.x);
			
			while (pointIndex + 1 < N && pointList.get(pointIndex + 1).y == currentY) {
				Point nextPoint = pointList.get(++pointIndex);
				xList.add(nextPoint.x);
			}
			
			for (int x : xList) {
				answer = (answer + sTree.count(x)) % BIGINT;
			}
			for (int x : xList) {
				sTree.insert(x);
			}
			
			pointIndex++;
		}
		
		System.out.println(answer);
	} // [MAIN]
	
}