import java.io.*;
import java.util.*;

public class Main_1085_직사각형에서탈출_B3 {
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder();
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		
		int min = x;
		
		int[] arr = {y, w-x, h-y};
		
		for (int i = 0; i < 3; i++) {
			if (arr[i] < min) {
				min = arr[i];
			}
		}
		
		System.out.println(min);
		
	} // end of main
} // end of class

