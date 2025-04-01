import java.io.*;
import java.util.*;

public class Main_2170_선긋기_G5_1700ms{
	
	// [POINT]
	private static class Point implements Comparable<Point>{
		int coordinate;
		int checker;
		
		Point(int coordinate, int checker) {
			this.coordinate = coordinate;
			this.checker = checker;
		}
		
		@Override
		public int compareTo(Point o) {
			return this.coordinate == o.coordinate ? o.checker - this.checker : this.coordinate - o.coordinate;
		}
	} // [POINT]
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		List<Point> list = new ArrayList<>();
		
		
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine(), " ");

			int left = Integer.parseInt(st.nextToken());
			Point pointStart = new Point(left, 1);
			list.add(pointStart);
			
			int right = Integer.parseInt(st.nextToken());
			Point pointEnd = new Point(right, -1);
			list.add(pointEnd);
		}
		
		Collections.sort(list);
		
		int length = 0;
		int before = 0;
		int checker = 0;
		int index = 0;
		
		while (index < N * 2) {
			Point currPoint = list.get(index);
			
			if (0 < checker) {
				length += currPoint.coordinate - before;
			}
			
			checker += currPoint.checker;
			before = currPoint.coordinate;
			
			while (N * 2 < index + 1 && list.get(index + 1).coordinate == before) {
				index++;
				currPoint = list.get(index);
				checker += currPoint.checker;
			}
			
			index++;
		}
		
		System.out.println(length);
	} // [MAIN]
	
}