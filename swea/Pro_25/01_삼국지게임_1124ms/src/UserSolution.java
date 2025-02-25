import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class UserSolution {
	
	// [DECLARATION]
	static int[][] around = {
			{-1, -1},	{-1, 0},	{-1, 1},
			{0, -1},				{0, 1},
			{1, -1},	{1, 0},		{1, 1}};
	
	static int N;
	
	static Map<String, int[]> monarchCoordinate;
	
	static String[][] monarchArray;
	static int[][] soldierArray;
	static String[][] leaderArray;
	
	static Map<String, Set<String>> allyMap;
	static Map<String, Set<String>> enemyMap;
	
	static boolean inBound(int row, int col) {
		return 0 <= row && row < N && 0 <= col && col < N;
	}
	// [DECLARATION]
	
	// 100. INIT
    void init(int n, int mSoldier[][], char mMonarch[][][]) {
    	N = n;
    	
    	monarchCoordinate = new HashMap<>();
    	
    	monarchArray = new String[N][N];
    	soldierArray = new int[N][N];
    	leaderArray = new String[N][N];
    	
    	allyMap = new HashMap<>();
    	enemyMap = new HashMap<>();
    	
    	for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				String monarchName = String.valueOf(mMonarch[row][col]).trim();
				
				monarchCoordinate.put(monarchName, new int[] {row, col});
				
				monarchArray[row][col] = monarchName;
				soldierArray[row][col] = mSoldier[row][col];
				leaderArray[row][col] = monarchName;
				
				allyMap.put(monarchName, new HashSet<>()); allyMap.get(monarchName).add(monarchName);
				enemyMap.put(monarchName, new HashSet<>());
			}
		}
    } // 100. INIT
    
    // 200. DESTROY
    void destroy() {
    } // 200. DESTROY
    
    // 300. ALLY
    int ally(char mMonarchA[], char mMonarchB[]) {
    	String nameA = String.valueOf(mMonarchA).trim();
    	String nameB = String.valueOf(mMonarchB).trim();
        
    	if (monarchCoordinate.get(nameA) == null || monarchCoordinate.get(nameB) == null) return 0;
    	
    	int rowA = monarchCoordinate.get(nameA)[0];
    	int colA = monarchCoordinate.get(nameA)[1];
    	int rowB = monarchCoordinate.get(nameB)[0];
    	int colB = monarchCoordinate.get(nameB)[1];
    	
    	String leaderA = leaderArray[rowA][colA];
    	String leaderB = leaderArray[rowB][colB];
    	
    	// Case 1. Same or Ally Already > -1
    	if (leaderA.equals(leaderB)) {
    		return -1;
    	}
    	
    	// Case 2. Enemy > -2
    	if (enemyMap.get(leaderA).contains(leaderB)) {
    		return -2;
    	}
    	
    	// Case 3. Ally > 1
    	for (String monarch : allyMap.get(leaderB)) {
    		allyMap.get(leaderA).add(monarch);
    		int row = monarchCoordinate.get(monarch)[0]; int col = monarchCoordinate.get(monarch)[1];
    		leaderArray[row][col] = leaderA;
    	}
    	allyMap.remove(leaderB);
    	
    	for (String enemyLeader : enemyMap.get(leaderB)) {
    		enemyMap.get(leaderA).add(enemyLeader);
    		enemyMap.get(enemyLeader).add(leaderA); enemyMap.get(enemyLeader).remove(leaderB);
    	}
    	enemyMap.remove(leaderB);
    	
    	return 1;
    } // 300. ALLY
    
    // 400. ATTACK
    int attack(char mMonarchA[], char mMonarchB[], char mGeneral[]) {
    	String nameA = String.valueOf(mMonarchA).trim();
    	String nameB = String.valueOf(mMonarchB).trim();
    	
    	if (monarchCoordinate.get(nameA) == null || monarchCoordinate.get(nameB) == null) return 0;
    	
    	int rowA = monarchCoordinate.get(nameA)[0];
    	int colA = monarchCoordinate.get(nameA)[1];
    	int rowB = monarchCoordinate.get(nameB)[0];
    	int colB = monarchCoordinate.get(nameB)[1];
    	
    	String leaderA = leaderArray[rowA][colA];
    	String leaderB = leaderArray[rowB][colB];
    	
    	// Case 1. Ally > -1
    	if (leaderA.equals(leaderB)) {
    		return -1;
    	}
    	
    	Set<int[]> nearA = new HashSet<>();
    	Set<int[]> nearB = new HashSet<>();
    	
    	for (int[] near : around) {
    		int rowN = rowB + near[0];
    		int colN = colB + near[1];
    		
    		if (!inBound(rowN, colN)) {
    			continue;
    		}
    		
    		if (leaderArray[rowN][colN].equals(leaderA)) {
    			nearA.add(new int[] {rowN, colN});
    		} else if (leaderArray[rowN][colN].equals(leaderB)) {
    			nearB.add(new int[] {rowN, colN});
    		}
    	}
    	
    	// Case 2. Far > -2
    	if (nearA.isEmpty()) {
    		return -2;
    	}
    	
    	// Case 3. War Occurred!;
    	enemyMap.get(leaderA).add(leaderB);
    	enemyMap.get(leaderB).add(leaderA);
    	
    	int solA = 0;
    	for (int[] a : nearA) {
    		int row = a[0]; int col = a[1];
    		int sol = soldierArray[row][col] / 2;
    		soldierArray[row][col] -= sol;
    		solA += sol;
    	}
    	
    	int solB = 0;
    	for (int[] b : nearB) {
    		int row = b[0]; int col = b[1];
    		int sol = soldierArray[row][col] / 2;
    		soldierArray[row][col] -= sol;
    		solB += sol;
    	}
    	
    	soldierArray[rowB][colB] = soldierArray[rowB][colB] - solA + solB;
    	
    	// Case 3-1. Defended > 0
    	if (0 <= soldierArray[rowB][colB]) {
    		return 0;
    	}
    	
    	// Case 3-2. Defeated > 1
    	String nameG = String.valueOf(mGeneral).trim();
    	
    	monarchCoordinate.put(nameG, new int[] {rowB, colB});
    	monarchCoordinate.remove(nameB);
    	
    	monarchArray[rowB][colB] = nameG;
    	soldierArray[rowB][colB] *= -1;
    	leaderArray[rowB][colB] = leaderA;
    	
    	allyMap.get(leaderA).add(nameG);
    	
    	// Case 3-2-1. Eliminated
    	if (allyMap.get(leaderB).size() == 1) {
    		allyMap.remove(nameB);
    		
    		for (String enemy : enemyMap.get(leaderB)) {
    			enemyMap.get(enemy).remove(leaderB);
    		}
    		enemyMap.remove(leaderB);
    		
    		return 1;
    	}
    	
    	// Case 3-2-2. Remain
    	allyMap.get(leaderB).remove(nameB);
    	
    	// Case 3-2-2-1. Not Leader
    	if (!nameB.equals(leaderB)) {
    		return 1;
    	}
    	
    	// Case 3-2-2-2. Leader Executed
    	String newLeaderB = allyMap.get(leaderB).iterator().next();
    	
    	allyMap.put(newLeaderB, new HashSet<>());
    	for (String ally : allyMap.get(leaderB)) {
    		int row = monarchCoordinate.get(ally)[0];
    		int col = monarchCoordinate.get(ally)[1];
    		leaderArray[row][col] = newLeaderB;
    		allyMap.get(newLeaderB).add(ally);
    	}
    	allyMap.remove(leaderB);
    	
    	enemyMap.put(newLeaderB, new HashSet<>());
    	for (String enemy : enemyMap.get(leaderB)) {
    		enemyMap.get(newLeaderB).add(enemy);
    		enemyMap.get(enemy).remove(leaderB); enemyMap.get(enemy).add(newLeaderB);
    	}
    	enemyMap.remove(leaderB);
    	
        return 1;
    } // 400. ATTACK
    
    // 500. RECRUIT
    int recruit(char mMonarch[], int mNum, int mOption) {
    	String name = String.valueOf(mMonarch).trim();
    	
    	if (monarchCoordinate.get(name) == null) return 0;
    	
    	int row = monarchCoordinate.get(name)[0];
    	int col = monarchCoordinate.get(name)[1];
    	
    	// Case 1. Option is 0 : Alone
    	if (mOption == 0) {
    		soldierArray[row][col] += mNum;
    		return soldierArray[row][col];
    	}
    	
    	// Case 2. Option is 1 : Ally 
    	int allySol = 0;
    	
    	String leaderName = leaderArray[row][col];
    	
    	for (String monarch : allyMap.get(leaderName)) {
    		int r = monarchCoordinate.get(monarch)[0];
        	int c = monarchCoordinate.get(monarch)[1];
        	soldierArray[r][c] += mNum;
        	allySol += soldierArray[r][c];
    	}
        return allySol;
    } // 500. RECRUIT
}