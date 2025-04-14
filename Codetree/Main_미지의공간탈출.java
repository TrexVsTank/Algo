import java.io.*;
import java.util.*;

public class Main_미지의공간탈출 {
	
	// STATIC
	static int[][] dir = new int[][] { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };
	static int N;
	static int M;
	static int[] ASS1;
	static int[] ASS2;
	static int[] START;
	
	// FLOOR CHECKER
	private static boolean floorChecker(int row, int col) {
		return 0 <= row && row < N && 0 <= col && col < N;
	}
	
	// TELEPORTER
	private static int[] teleporter(int row, int col, int dimension) {
		int dimensionNext = dimension;
		int rowNext = row;
		int colNext = col;
		if (dimension == 3) {
			if (col == M + 1) {
				// E
				dimensionNext = 2;
				rowNext = 1;
				colNext = M + 1 - row + M;
			} else if (col == 0) {
				// W
				dimensionNext = 2;
				rowNext = 1;
				colNext = row + M * 3;
			} else if (row == M + 1) {
				// S
				dimensionNext = 2;
				rowNext = 1;
				colNext = col;
			} else if(row == 0) {
				// N
				dimensionNext = 2;
				rowNext = 1;
				colNext = M + 1 - col + M * 2;
			}
		}
		if (dimension == 2) {
			if (col == 0) {
				colNext = M * 4;
			} else if (col == M * 4 + 1) {
				colNext = 1;
			}
			if (row == 0) {
				if (col <= M) {
					// S
					dimensionNext = 3;
					rowNext = M;
					colNext = col;
				} else if (col <= M * 2) {
					// E
					dimensionNext = 3;
					rowNext = M + 1 - (col - M);
					colNext = M;
				} else if (col <= M * 3) {
					// N
					dimensionNext = 3;
					rowNext = 1;
					colNext = M + 1 - (col - M * 2);
				} else {
					// W
					dimensionNext = 3;
					rowNext = col - M * 3;
					colNext = 1;
				}
			} else if (row == M + 1) {
				if (row == ASS2[0] && col == ASS2[1]) {
					dimensionNext = 1;
					rowNext = ASS1[0];
					colNext = ASS1[1];
				} else {
					return START;
				}
			}
		}
		return new int[] {rowNext, colNext, dimensionNext};
	}
	// [MAIN]
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    	
    	// N / M / F
    	N = Integer.parseInt(st.nextToken());
    	M = Integer.parseInt(st.nextToken());
    	int F = Integer.parseInt(st.nextToken());
    	
    	// END
    	int[] end = new int[3];
    	
    	// FLOOR
    	int[][] floor = new int[N][N];
    	boolean[][] visitedFloor = new boolean[N][N];
    	
    	for (int i = 0; i < N; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				floor[i][j] = Integer.parseInt(st.nextToken());
			}
		}
    	
    	// SOUTH EAST NORTH WEST
    	int[][] SENW = new int[M + 2][M * 4 + 2];
    	boolean[][] visitedSENW = new boolean[M + 2][M * 4 + 2];
    	// EAST
    	for (int i = 1; i <= M; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= M; j++) {
				SENW[i][j + M] = Integer.parseInt(st.nextToken());
			}
		}
    	// WEST
    	for (int i = 1; i <= M; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
    		for (int j = 1; j <= M; j++) {
				SENW[i][j + M * 3] = Integer.parseInt(st.nextToken());
			}
		}
    	// SOUTH
    	for (int i = 1; i <= M; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
    		for (int j = 1; j <= M; j++) {
				SENW[i][j] = Integer.parseInt(st.nextToken());
			}
		}
    	// NORTH
    	for (int i = 1; i <= M; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
    		for (int j = 1; j <= M; j++) {
				SENW[i][j + M * 2] = Integer.parseInt(st.nextToken());
			}
		}
    	
    	// START
    	START = new int[3];
    	
    	// CEIL
    	int[][] ceil = new int[M + 2][M + 2];
    	boolean[][] visitedCeil = new boolean[M + 2][M + 2];
    	for (int i = 1; i <= M; i++) {
    		st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j <= M; j++) {
				ceil[i][j] = Integer.parseInt(st.nextToken());
				if (ceil[i][j] == 2) {
					START = new int[] {i, j, 3};
				}
			}
		}
    	
    	// FUCK
    	List<int[]> fuckList = new ArrayList<>();
    	for (int i = 0; i < F; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int[] fuck = new int[4];
			for (int j = 0; j < 4; j++) {
				fuck[j] = Integer.parseInt(st.nextToken());
			}
			floor[fuck[0]][fuck[1]] = 1;
			fuckList.add(fuck);
		}
    	
    	// ASS FINDER
    	int[] assFinder = new int[] {0, 500, 0, 500};
    	
    	// FIND ASS
    	ASS1 = new int[2];
    	ASS2 = new int[2];
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (floor[i][j] == 4) {
    				end = new int[] {i, j};
    			}
    			if (floor[i][j] == 3) {
    				assFinder[0] = Math.max(assFinder[0], j);
    				assFinder[1] = Math.min(assFinder[1], j);
    				assFinder[2] = Math.max(assFinder[2], i);
    				assFinder[3] = Math.min(assFinder[3], i);
    			}
    		}
    	}
    	af : for (int col = assFinder[1] - 1; col <= assFinder[0] + 1; col++) {
    		for (int row = assFinder[3] - 1; row <= assFinder[2] + 1; row++) {
    			if (floor[row][col] == 0) {
    				ASS1 = new int[] {row, col};
    				int assCol = 0;
    				if (col == assFinder[0] + 1) {
    					// E
    					assCol = 1 + (row - assFinder[2]) + M;
    					SENW[M + 1][assCol] = 100;
    				} else if (col == assFinder[1] - 1) {
    					// W
//    					System.out.println(123);
//    					System.out.println(row);
    					assCol = 1 + (row - assFinder[3]) + M * 3;
    					SENW[M + 1][assCol] = 100;
    				} else if (row == assFinder[2] + 1) {
    					// S
    					assCol = 1 + (col - assFinder[1]);
    					SENW[M + 1][assCol] = 100;
    				} else {
    					// N
    					assCol = M - (col - assFinder[1]) + M * 2;
    					SENW[M + 1][assCol] = 100;
    				}
    				ASS2 = new int[] {M + 1, assCol};
    				break af;
    			}
    		}
    	}
    	
//    	System.out.println(Arrays.toString(assFinder));
//    	System.out.println(Arrays.toString(ASS1));
//    	System.out.println(Arrays.toString(ASS2));
    	
    	// TIME
    	int time = 0;
    	
    	// QUEUE
    	Queue<int[]> queue = new ArrayDeque<>();
    	queue.offer(START);
    	
    	// FIRST BFS LOOF
    	b1 : while (!queue.isEmpty()) {
    		time++;
    		
    		for (int fuckIdx = fuckList.size() - 1; fuckIdx >= 0; fuckIdx--) {
				int[] fuck = fuckList.get(fuckIdx);
				if (time % fuck[3] == 0) {
					int row = fuck[0];
					int col = fuck[1];
					int rowNext = row + dir[fuck[2]][0];
					int colNext = col + dir[fuck[2]][1];
					if (floorChecker(rowNext, colNext)) {
						if (floor[rowNext][colNext] == 0) {
							floor[rowNext][colNext] = 1;
							fuck[0] = rowNext;
							fuck[1] = colNext;
						} else {
							fuckList.remove(fuckIdx);
						}
					}
				}
			}
    		
    		int queueSize = queue.size();
    		
    		for (int i = 0; i < queueSize; i++) {
				int[] curr = queue.poll();
//				System.out.println(time-1 + " | " + Arrays.toString(curr));
				int currRow = curr[0];
				int currCol = curr[1];
				int currDim = curr[2];
				for (int[] d : dir) {
					int nextRow = currRow + d[0];
					int nextCol = currCol + d[1];
					int[] next = teleporter(nextRow, nextCol, currDim);
					
					
					nextRow = next[0];
					nextCol = next[1];
					int nextDim = next[2];
					if (nextDim == 1) {
						visitedFloor[ASS1[0]][ASS1[1]] = true;
						queue = new ArrayDeque<>();
						queue.offer(new int[] {nextRow, nextCol});
						break b1;
					} else if (nextDim == 2 && SENW[nextRow][nextCol] == 0 && !visitedSENW[nextRow][nextCol]) {
						visitedSENW[nextRow][nextCol] = true;
						queue.offer(next);
					} else if (nextDim == 3 && ceil[nextRow][nextCol] == 0 && !visitedCeil[nextRow][nextCol]) {
						visitedCeil[nextRow][nextCol] = true;
						queue.offer(next);
					}
				}
			}
    		
    	} // FIRST BFS LOOF
    	
    	// SECOND BFS LOOF
    	b2 : while (!queue.isEmpty()) {
    		time++;
    		
    		for (int fuckIdx = fuckList.size() - 1; fuckIdx >= 0; fuckIdx--) {
				int[] fuck = fuckList.get(fuckIdx);
				if (time % fuck[3] == 0) {
					int row = fuck[0];
					int col = fuck[1];
					int rowNext = row + dir[fuck[2]][0];
					int colNext = col + dir[fuck[2]][1];
					if (floorChecker(rowNext, colNext)) {
						if (floor[rowNext][colNext] == 0) {
							floor[rowNext][colNext] = 1;
							fuck[0] = rowNext;
							fuck[1] = colNext;
						} else {
							fuckList.remove(fuckIdx);
						}
					}
				}
			}
    		
    		int queueSize = queue.size();
    		
    		for (int i = 0; i < queueSize; i++) {
				int[] curr = queue.poll();
//				System.out.println(time-1 + " | " + Arrays.toString(curr));
				int currRow = curr[0];
				int currCol = curr[1];
				for (int[] d : dir) {
					int nextRow = currRow + d[0];
					int nextCol = currCol + d[1];
					if (floorChecker(nextRow, nextCol) && nextRow == end[0] && nextCol == end[1]) {
						visitedFloor[nextRow][nextCol] = true;
						break b2;
					} else if (floorChecker(nextRow, nextCol) && floor[nextRow][nextCol] == 0 && !visitedFloor[nextRow][nextCol]) {
						visitedFloor[nextRow][nextCol] = true;
						queue.offer(new int[] {nextRow, nextCol});
					}
				}
			}
    	} // SECOND BFS LOOF
    	
//    	for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(visitedFloor[i][j]);
//			}
//			System.out.println();
//		}
    	// PRINT
    	if (visitedFloor[end[0]][end[1]]) {
    		System.out.println(time);
    	} else {
    		System.out.println(-1);
    	}
    	
    } // [MAIN]
    
}
