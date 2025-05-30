import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// [클래스] Main
public class Main_17386_선분교차1_G3 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static double[][] point;
	
	// [메서드] main
	public static void main(String[] args) throws IOException {
		
		// [point]
		point = new double[4][2];
		for (int i = 0; i < 2; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 2; j++) {
				point[j + i*2][0] = Double.parseDouble(st.nextToken());
				point[j + i*2][1] = Double.parseDouble(st.nextToken());
			}
		}
		
		if (CCW(0, 1, 2)*CCW(0, 1, 3) < 0 && CCW(2, 3, 0)*CCW(2, 3, 1) < 0) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}
		
	} // [메서드] main
	
	// [메서드] CCW
	private static double CCW(int n1, int n2, int n3) {
		double[][] arr = new double[3][2];
		arr[0] = point[n1];
		arr[1] = point[n2];
		arr[2] = point[n3];
		double a = 0;
		double b = 0;
		for (int i = 0; i < 3; i++) {
			a += arr[i][0] * arr[(i+1)%3][1];
			b += arr[(i+1)%3][0] * arr[i][1];
		}
		return a - b;
	} // [메서드] CCW
	
} // [클래스] Main
