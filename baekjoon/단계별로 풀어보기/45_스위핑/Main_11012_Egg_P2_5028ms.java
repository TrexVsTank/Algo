import java.io.*;
import java.util.*;

public class Main_11012_Egg_P2_5028ms {
	
	// [SEGMENT TREE]
	private static class SegmentTree {
		int height;
		int length;
		int end;
		int[] tree;
		
		public SegmentTree(int n) {
			this.height = (int) Math.ceil(Math.log(n) / Math.log(2)) + 1;
			this.length = (int) Math.pow(2, height);
			this.end = length / 2;
			this.tree = new int[length];
		}
		
		public void update(int num, int start, int end) {
			update(num, start, end, 1, 1, this.end);
		}
		public void update(int num, int start, int end, int index, int left, int right) {
			if (right < start || end < left) {
				return;
			}
			
			if (start <= left & right <= end) {
				this.tree[index] += num;
				return;
			}
			
			int mid = (left + right) / 2;
			
			update(num, start, end, index * 2, left, mid);
			update(num, start, end, index * 2 + 1, mid + 1, right);
		}
		
		public long count(int num) {
			return count(num, 1, 1, this.end);
		}
		public long count(int num, int index, int left, int right) {
			if (right < num || num < left) {
				return 0;
			}
			
			long sum = 0;
			
			if (left <= num && num <= right) {
				sum += this.tree[index];
			}
			
			if (left == right) {
				return sum;
			}
			
			int mid = (left + right) / 2;
			
			sum += count(num, index * 2, left, mid);
			sum += count(num, index * 2 + 1, mid + 1, right);
			
			return sum;
		}
	} // [SEGMENT TREE]
	
	// [EGG]
	private static class Egg implements Comparable<Egg> {
		int x;
		int y;
		
		public Egg(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Egg o) {
			if (this.x == o.x) {
				return this.y - o.y;
			}
			return this.x - o.x;
		}
	} // [EGG]
	
	// [RECT SIDE]
	private static class RectSide implements Comparable<RectSide> {
		int x;
		int y1;
		int y2;
		int isStart;
		
		public RectSide(int x, int y1, int y2, int isStart) {
			this.x = x;
			this.y1 = y1;
			this.y2 = y2;
			this.isStart = isStart;
		}

		@Override
		public int compareTo(Main_11012_Egg_P2_5028ms.RectSide o) {
			if (this.x == o.x) {
				return o.isStart - this.isStart;
			}
			return this.x - o.x;
		}
	} // [RECT SIDE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		// Test Case Loop
		for (int testCase = 0; testCase < T; testCase++) {
			
			// 0. Declaration
			long answer = 0;
			
			List<Egg> eggList = new ArrayList<>();
			List<RectSide> rectSideList = new ArrayList<>();
			
			TreeSet<Integer> xSet = new TreeSet<>();
			TreeSet<Integer> ySet = new TreeSet<>();
			int xComp = 0;
			int yComp = 0;
			
			Map<Integer, Integer> xMap = new HashMap<>();
			Map<Integer, Integer> yMap = new HashMap<>();
			
			// 1. Input
			st = new StringTokenizer(br.readLine(), " ");
			
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			for (int n = 0; n < N; n++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				Egg egg = new Egg(x, y);
				eggList.add(egg);
				
				xSet.add(x);
				ySet.add(y);
			}
			
			for (int m = 0; m < M; m++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x1 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int y1 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				
				RectSide left = new RectSide(x1, y1, y2, 1);
				RectSide right = new RectSide(x2, y1, y2, -1);
				
				rectSideList.add(left);
				rectSideList.add(right);
				
				xSet.add(x1);
				xSet.add(x2);
				ySet.add(y1);
				ySet.add(y2);
			}
			
			Collections.sort(eggList);
			Collections.sort(rectSideList);
			
			// 2. Coordinate Compress
			for (int x : xSet) {
				xMap.put(x, ++xComp);
			}
			
			for (int y : ySet) {
				yMap.put(y, ++yComp);
			}
			
			for (Egg egg : eggList) {
				egg.x = xMap.get(egg.x);
				egg.y = yMap.get(egg.y);
			}
			
			for (RectSide rectSide : rectSideList) {
				rectSide.x = xMap.get(rectSide.x);
				rectSide.y1 = yMap.get(rectSide.y1);
				rectSide.y2 = yMap.get(rectSide.y2);
			}
			
			// 3. Calculate
			SegmentTree sTree = new SegmentTree(yComp);
			
			int eggIndex = 0;
			int rectSideIndex = 0;
			
			while (rectSideIndex < rectSideList.size() && eggIndex < eggList.size()) {
				int xCurr = rectSideList.get(rectSideIndex).x;
				
				while (eggIndex < eggList.size() && eggList.get(eggIndex).x < xCurr) {
					Egg egg = eggList.get(eggIndex);
					answer += sTree.count(egg.y);
					eggIndex++;
				}

				while (rectSideIndex < rectSideList.size() && rectSideList.get(rectSideIndex).x == xCurr && rectSideList.get(rectSideIndex).isStart == 1) {
					RectSide rectSide = rectSideList.get(rectSideIndex);
					sTree.update(rectSide.isStart, rectSide.y1, rectSide.y2);
					rectSideIndex++;
				}
				
				while (eggIndex < eggList.size() && eggList.get(eggIndex).x == xCurr) {
					Egg egg = eggList.get(eggIndex);
					answer += sTree.count(egg.y);
					eggIndex++;
				}
				
				while (rectSideIndex < rectSideList.size() && rectSideList.get(rectSideIndex).x == xCurr && rectSideList.get(rectSideIndex).isStart == -1) {
					RectSide rectSide = rectSideList.get(rectSideIndex);
					sTree.update(rectSide.isStart, rectSide.y1, rectSide.y2);
					rectSideIndex++;
				}
			}
			
			sb.append(answer).append("\n");
		} // Test Case Loop
		
		System.out.println(sb);
	} // [MAIN]
	
}