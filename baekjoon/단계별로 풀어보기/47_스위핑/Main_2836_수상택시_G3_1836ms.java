import java.io.*;
import java.util.*;

public class Main_2836_수상택시_G3_1836ms{
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		Map<Integer, Integer> startMap = new HashMap<>();
		TreeMap<Integer, List<Integer>> coorMap = new TreeMap<>();
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			startMap.put(n, start);
			
			if (coorMap.get(start) == null) {
				coorMap.put(start, new ArrayList<>());
			}
			if (coorMap.get(end) == null) {
				coorMap.put(end, new ArrayList<>());
			}
			coorMap.get(start).add(n);
			coorMap.get(end).add(-n);
		}
		
		int left = M;
		int right = -1;
		
		long answer = M;
		
		for (int coor : coorMap.keySet()) {
			if (coor == right) {
				answer += 2 * (right - left);
				left = M;
				right = -1;
			}
			
			for (int n : coorMap.get(coor)) {
				if (n < 0 && coor < startMap.get(-n)) {
					right = Math.max(right, startMap.get(-n));
					left = Math.min(left, coor);
				}
			}
		}
		
		System.out.println(answer);
	} // [MAIN]
	
}