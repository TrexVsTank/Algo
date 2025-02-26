import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class UserSolution {
	
	// [Declaration]
	static class Player implements Comparable<Player> {
		int id;
		int ability;
		public Player(int id, int ability) {
			this.id = id;
			this.ability = ability;
		}
		@Override
		public int compareTo(Player o) {
			if (this.ability == o.ability) {
				return this.id - o.id;
			}
			return o.ability - this.ability;
		}
	}
	
	static int binarySearch(List<Player> list, Player player) {
		int result = Collections.binarySearch(list, player);
		if (result < 0) {
			result = -result - 1;
		}
		return result;
	}
	
	static int N;
	static int L;

	static int len;
	static int middle;
	static List<List<Player>> league;
	// [Declaration]
	
	// 100. INIT
    void init(int n, int l, int mAbility[]) {
    	N = n;
    	L = l;
    
    	len = N / L;
    	middle = len / 2;
    	league = new ArrayList<>();
    	
    	for (int i = 0; i < L; i++) {
			league.add(new ArrayList<>());
		}
    	
    	for (int id = 0; id < N; id++) {
    		league.get(id / len).add(new Player(id, mAbility[id]));
    	}
    	
    	for (int i = 0; i < L; i++) {
			Collections.sort(league.get(i));
		}
    } // 100. INIT
    
    // 200. MOVE
    int move() {
    	Player[] promotionFrom = new Player[L];
    	Player[] relegationFrom = new Player[L];
    	
    	int idSum = 0;
    	
    	for (int l = 0; l < L; l++) {
    		promotionFrom[l] = league.get(l).get(0);
    		relegationFrom[l] = league.get(l).get(len - 1);
    		
    		idSum += promotionFrom[l].id;
    		idSum += relegationFrom[l].id;
		}
    	
    	idSum -= promotionFrom[0].id;
    	idSum -= relegationFrom[L - 1].id;
    	
    	for (int l = 0; l < L; l++) {
			if (l == 0) {
				league.get(l).remove(len - 1);
				
				Player pro = promotionFrom[l + 1];
				int proIndex = binarySearch(league.get(l), pro);
				league.get(l).add(proIndex, pro);
			} else if (l < L - 1) {
				league.get(l).remove(len - 1);
				league.get(l).remove(0);
				
				Player pro = promotionFrom[l + 1];
				int proIndex = binarySearch(league.get(l), pro);
				league.get(l).add(proIndex, pro);
				
				Player rel = relegationFrom[l - 1];
				int relIndex = binarySearch(league.get(l), rel);
				league.get(l).add(relIndex, rel);
			} else {
				league.get(l).remove(0);
				
				Player rel = relegationFrom[l - 1];
				int relIndex = binarySearch(league.get(l), rel);
				league.get(l).add(relIndex, rel);
			}
		}
    	
        return idSum;
    } // 200. MOVE
    
    // 300. TRADE
    int trade() {
    	Player[] promotionFrom = new Player[L];
    	Player[] relegationFrom = new Player[L];
    	
    	int idSum = 0;
    	
    	for (int l = 0; l < L; l++) {
    		promotionFrom[l] = league.get(l).get(0);
    		relegationFrom[l] = league.get(l).get(middle);
    		
    		idSum += promotionFrom[l].id;
    		idSum += relegationFrom[l].id;
		}
    	
    	idSum -= promotionFrom[0].id;
    	idSum -= relegationFrom[L - 1].id;
    	
    	for (int l = 0; l < L; l++) {
			if (l == 0) {
				league.get(l).remove(middle);
				
				Player pro = promotionFrom[l + 1];
				int proIndex = binarySearch(league.get(l), pro);
				league.get(l).add(proIndex, pro);
			} else if (l < L - 1) {
				league.get(l).remove(middle);
				league.get(l).remove(0);
				
				Player pro = promotionFrom[l + 1];
				int proIndex = binarySearch(league.get(l), pro);
				league.get(l).add(proIndex, pro);
				
				Player rel = relegationFrom[l - 1];
				int relIndex = binarySearch(league.get(l), rel);
				league.get(l).add(relIndex, rel);
			} else {
				league.get(l).remove(0);
				
				Player rel = relegationFrom[l - 1];
				int relIndex = binarySearch(league.get(l), rel);
				league.get(l).add(relIndex, rel);
			}
		}
    	
        return idSum;
    } // 300. TRADE
}