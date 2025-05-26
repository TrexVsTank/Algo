import java.io.*;
import java.util.*;

public class Main_7626_직사각형_P1_2232ms {
	
	// STATIC
	static List<Integer> yList;
	
	// [RECT SIDE]
	private static class RectSide implements Comparable<RectSide> {
		int x;
		int y1;
		int y2;
		int isStart;
		
		RectSide(int x, int y1, int y2, int isStart) {
			this.x = x;
			this.y1 = y1;
			this.y2 = y2;
			this.isStart = isStart;
		}
		
		@Override
		public int compareTo(RectSide o) {
			if (this.x != o.x) {
				return this.x - o.x;
			}
			return o.isStart - this.isStart;
		}
	} // [RECT SIDE]
	
	// [SEGMENT TREE]
	private static class SegmentTree {
		long[] sumTree;
		int[] countTree;
		int end;
		
		SegmentTree(int n) {
			int height = (int) Math.ceil( Math.log(n) / Math.log(2) ) + 1;
			int size = (int) Math.pow(2, height);
			
			this.sumTree = new long[size];
			this.countTree = new int[size];
			this.end = size / 2;
		}
		
		void update(int isStart, int start, int end) {
			update(isStart, start, end, 1, 1, this.end);
		}
		void update(int isStart, int start, int end, int index, int left, int right) {
			if (right < start || end < left) {
				return;
			}
			
			if (start <= left && right <= end) {
				this.countTree[index] += isStart;
				
				if (this.countTree[index] == 0) {
					if (left == right) {
						this.sumTree[index] = 0;
						return;
					}
					this.sumTree[index] = this.sumTree[index * 2] + this.sumTree[index * 2 + 1];
				} else {
					this.sumTree[index] = yList.get(right) - yList.get(left - 1);
				}
				
				return;
			}
			
			int mid = (left + right) / 2;
			
			update(isStart, start, end, index * 2, left, mid);
			update(isStart, start, end, index * 2 + 1, mid + 1, right);
			
			if (this.countTree[index] == 0) {
				if (left == right) {
					this.sumTree[index] = 0;
					return;
				}
				this.sumTree[index] = this.sumTree[index * 2] + this.sumTree[index * 2 + 1];
			} else {
				this.sumTree[index] = yList.get(right) - yList.get(left - 1);
			}
		}
	} // [SEGMENT TREE]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 01. INPUT && COMP
		int N = Integer.parseInt(br.readLine());
		
		List<RectSide> rectSideList = new ArrayList<>();
		TreeSet<Integer> ySet = new TreeSet<>();
		Map<Integer, Integer> yMap = new HashMap<>();
		yList = new ArrayList<>();
		
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			RectSide leftSide = new RectSide(x1, y1, y2, 1);
			RectSide rightSide = new RectSide(x2, y1, y2, -1);
			
			rectSideList.add(leftSide);
			rectSideList.add(rightSide);
			
			ySet.add(y1);
			ySet.add(y2);
		}
		
		Collections.sort(rectSideList);
		
		int yComp = 0;
		
		for (int y : ySet) {
			yMap.put(y, yComp++);
			yList.add(y);
		}
		
		for (RectSide rectSide : rectSideList) {
			rectSide.y1 = yMap.get(rectSide.y1);
			rectSide.y2 = yMap.get(rectSide.y2);
		}
		
		// 02. CAL
		SegmentTree sTree = new SegmentTree(ySet.size());
		
		long answer = 0;
		
		int rectSideIndex = 0;
		int xCurr = 0;
		int xBef = rectSideList.get(0).x;
		
		while (rectSideIndex < N * 2) {
			RectSide curr = rectSideList.get(rectSideIndex++);
			xCurr = curr.x;

			long width = xCurr - xBef;
			long height = sTree.sumTree[1];
			answer += width * height;
			
			sTree.update(curr.isStart, curr.y1 + 1, curr.y2);
			
			while (rectSideIndex < N * 2 && curr.x == rectSideList.get(rectSideIndex).x) {
				curr = rectSideList.get(rectSideIndex++);
				sTree.update(curr.isStart, curr.y1 + 1, curr.y2);
			}
			
			xBef = xCurr;
		}
		
		// 03. PRINT
		System.out.println(answer);
	} // [MAIN]
	
}