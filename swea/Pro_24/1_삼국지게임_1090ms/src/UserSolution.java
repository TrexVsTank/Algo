import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 유저솔루션
class UserSolution {
	
	static 
	
	// (군주)
	class Monarch {
		int row, col, sol;
		String name, leader;
		Monarch() {
		}
		Monarch(int row, int col, int sol, String name, String leader) {
			super();
			this.row = row;
			this.col = col;
			this.sol = sol;
			this.name = name;
			this.leader = leader;
		}
	} // (군주 끝)
	
	// (연합)
	class Union {
		Monarch leader;
		Set<Monarch> ally;
		Set<Union> enemy;
		Union (Monarch leader) {
			this.leader = leader;
			this.ally = new HashSet<>(); this.ally.add(leader);
			this.enemy = new HashSet<>();
		}
		Union (Monarch leader, Set<Monarch> ally, Set<Union> enemy) {
			this.leader = leader;
			this.ally = ally;
			this.enemy = enemy;
		}
	} // (연합 끝)
	
	static Map<String, Monarch> uMonarch = new HashMap<>(); // 장수 배열
	static Map<String, Union> uUnion = new HashMap<>(); // 연합 맵
	
	// [시작]
    void init(int N, int mSoldier[][], char mMonarch[][][]) {
    	for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				String name = String.valueOf(mMonarch[r][c]).trim();
				Monarch A = new Monarch(r, c, mSoldier[r][c], name, name);
				uMonarch.put(name, A);
				uUnion.put(name, new Union(A));
			}
		}
    } // [시작 끝]
    
    // [모집]
    int recruit(char mMonarch[], int mNum, int mOption) {
    	// 옵션 0 : 모집 후 병사 수 리턴
    	String Aname = String.valueOf(mMonarch).trim();
    	Monarch A = uMonarch.get(Aname);
    	if (mOption == 0) {
    		A.sol += mNum;
    		return A.sol;
    	}
    	// 옵션 1 : 연합단위 모집 후 병사 수 리턴
		Union AU = uUnion.get(A.leader);
		int result = 0;
		for (Monarch a : AU.ally) {
			a.sol += mNum;
			result += a.sol;
		}
    	return result;
    } // [모집 끝]
    
    // [동맹]
    int ally(char mMonarchA[], char mMonarchB[]) {
    	// 리더가 같으면 -1 리턴
    	String Aname = String.valueOf(mMonarchA).trim();
    	String Bname = String.valueOf(mMonarchB).trim();
    	Monarch A = uMonarch.get(Aname);
    	Monarch B = uMonarch.get(Bname);
    	String ALname = A.leader;
    	String BLname = B.leader;
    	if (ALname.equals(BLname)) {
    		return -1;
    	}
    	// 적대관계면 -2 리턴
    	Union AU = uUnion.get(ALname);
    	for (Union eu : AU.enemy) {
    		if (eu.leader.name.equals(BLname)) {
    			return -2;
    		}
    	}
    	// 동맹을 맺고 1 리턴
    	Union BU = uUnion.get(BLname);
    	AU.ally.addAll(BU.ally);
    	for (Monarch b : BU.ally) {
    		b.leader = ALname;
    	}
    	AU.enemy.addAll(BU.enemy);
    	for (Union eu : BU.enemy) {
    		eu.enemy.add(AU);
    		eu.enemy.remove(BU);
    	}
    	uUnion.remove(BLname);
    	return 1;
    } // [동맹 끝]
    
    // [전투]
    int attack(char mMonarchA[], char mMonarchB[], char mGeneral[]) {
    	// 리더가 같으면 -1 리턴
    	String Aname = String.valueOf(mMonarchA).trim();
    	String Bname = String.valueOf(mMonarchB).trim();
    	Monarch A = uMonarch.get(Aname);
    	Monarch B = uMonarch.get(Bname);
    	String ALname = A.leader;
    	String BLname = B.leader;
    	if (ALname.equals(BLname)) {
    		return -1;
    	}
    	// 인접한 영토가 없으면 -1 리턴
    	int row = B.row;
    	int col = B.col;
    	Union AU = uUnion.get(ALname);
    	List<Monarch> Aattack = new ArrayList<>();
    	for (Monarch a : AU.ally) {
    		int R = a.row;
    		int C = a.col;
    		if (Math.abs(row - R) <= 1 && Math.abs(col - C) <= 1) {
    			Aattack.add(a);
    		}
    	}
    	if (Aattack.isEmpty()) {
    		return -2;
    	}
    	// 전투 발생
    	Union BU = uUnion.get(BLname);
    	AU.enemy.add(BU);
    	BU.enemy.add(AU);
    	List<Monarch> Bdefence = new ArrayList<>();
    	for (Monarch b : BU.ally) {
    		int R = b.row;
    		int C = b.col;
    		if (!Bname.equals(b.name) && Math.abs(row - R) <= 1 && Math.abs(col - C) <= 1) {
    			Bdefence.add(b);
    		}
    	}
    	for (Monarch a : Aattack) {
    		int sol = a.sol/2;
    		B.sol -= sol;
    		a.sol -= sol;
    	}
    	for (Monarch b : Bdefence) {
    		int sol = b.sol/2;
    		B.sol += sol;
    		b.sol -= sol;
    	}
    	// 방어에 성공하면 0 리턴
    	if (B.sol >= 0) {
    		return 0;
    	}
    	// 공격에 성공하면 1 리턴
    	if (BU.ally.size() == 1) {
    		AU.enemy.remove(BU);
    		uUnion.remove(BLname);
    	} else if (Bname.equals(BLname)) {
    		Monarch H = new Monarch();
    		BU.ally.remove(B);
    		for (Monarch b : BU.ally) {
    			H = b;
    		}
    		String Hname = H.name;
    		BU.leader = H;
    		for (Monarch b : BU.ally) {
    			b.leader = Hname;
    		}
    		uUnion.put(Hname, BU);
    		uUnion.remove(Bname);
    		AU.enemy.remove(BU);
    		AU.enemy.add(uUnion.get(Hname));
    	} else {
    		BU.ally.remove(B);
    	}
    	String Gname = String.valueOf(mGeneral).trim();
    	Monarch G = new Monarch(row, col, -B.sol, Gname, ALname);
    	AU.ally.add(G);
    	uMonarch.remove(Bname);
    	uMonarch.put(Gname, G);
    	return 1;
    } // [전투 끝]
    
    // [파괴]
    void destroy() {
//    	uMonarch.clear();
//    	uUnion.clear();
    } // [파괴 끝]
    
} // 유저솔루션 끝
