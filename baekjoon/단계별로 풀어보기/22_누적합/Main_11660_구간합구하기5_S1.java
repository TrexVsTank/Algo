import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_11660_구간합구하기5_S1 {
	public static void main(String[] args) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] arr = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= N; j++) {
				arr[i][j] = -arr[i-1][j-1]+arr[i-1][j]+arr[i][j-1]+Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			sb.append(arr[x2][y2] + arr[x1-1][y1-1] - arr[x1-1][y2] - arr[x2][y1-1]).append("\n");
		}
		
//		for (int i = 0; i < N+1; i++) {
//			System.out.println(Arrays.toString(arr[i]));
//		}
		
		System.out.println(sb);
		
	}
}