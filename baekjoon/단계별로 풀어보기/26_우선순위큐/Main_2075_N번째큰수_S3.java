import java.io.*;
import java.util.*;

public class Main_2075_N번째큰수_S3 {
	
	// [MAIN]
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		int[] array = new int[N * N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				array[i + j * N] = Integer.parseInt(st.nextToken());
			}
		}
		
		Arrays.sort(array);
		
		System.out.println(array[N * N - N]);
	} // [MAIN]
	
}