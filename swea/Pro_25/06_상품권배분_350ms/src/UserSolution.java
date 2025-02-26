import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserSolution {
	
	// [DECLARATION]
	static class Department {
		int id;
		int num;
		int parentId;
		List<Integer> childIdList;
		
		public Department(int id, int num, int parentId) {
			this.id = id;
			this.num = num;
			this.parentId = parentId;
			this.childIdList = new ArrayList<>();
		}
	}
	
	static void plus(Department department, int num) {
		int parentId = department.parentId;
		
		if (parentId == 0) {
			return;
		}
		
		Department parentDepartment = departmentMap.get(parentId);
		parentDepartment.num += num;
		plus(parentDepartment, num);
	}
	
	static void minus(Department department, int num) {
		int parentId = department.parentId;
		
		if (parentId == 0) {
			return;
		}
		
		Department parentdeDepartment = departmentMap.get(parentId);
		parentdeDepartment.num -= num;
		minus(parentdeDepartment, num);
	}
	
	static void delete(Department department) {
		for (int childId : department.childIdList) {
			Department childDepartment = departmentMap.get(childId);
			delete(childDepartment);
		}
		departmentMap.remove(department.id);
	}
	
	static int N;
	static Department[] topDepartments; 
	static Map<Integer, Department> departmentMap;
	// [DECLARATION]
	
	// 1. INIT
	public void init(int n, int mId[], int mNum[]) {
		N = n;
		topDepartments = new Department[N];
		departmentMap = new HashMap<>();
		
		for (int i = 0; i < N; i++) {
			Department department = new Department(mId[i], mNum[i], 0);
			topDepartments[i] = department;
			departmentMap.put(mId[i], department);
		}
	} // 1. INIT
	
	// 2. ADD
	public int add(int mId, int mNum, int mParent) {
		Department parentdeDepartment = departmentMap.get(mParent);
		
		// Case 1. Has Three Child > (-1)
		if (parentdeDepartment.childIdList.size() == 3) {
			return -1;
		}
		
		// Case 2. Add Child > Parents Num
		Department department = new Department(mId, mNum, mParent);
		parentdeDepartment.childIdList.add(mId);
		plus(department, mNum);
		departmentMap.put(mId, department);
		
		return parentdeDepartment.num;
	} // 2. ADD
	
	// 3. REMOVE
	public int remove(int mId) {
		Department department = departmentMap.get(mId);
		
		// Case 1. Dead Already > (-1)
		if (department == null) {
			return -1;
		}
		
		// Case 2. Delete
		Department parentDepartment = departmentMap.get(department.parentId);
		
		for (int i = 0; i < parentDepartment.childIdList.size(); i++) {
			if (departmentMap.get(parentDepartment.childIdList.get(i)).id == mId) {
				parentDepartment.childIdList.remove(i);
			}
		}
		
		minus(department, department.num);
		delete(department);
		
		return department.num;
	} // 3. REMOVE
	
	// 4. DISTRIBUTE
	public int distribute(int K) {
		int[] numArray = new int[N];
		int sum = 0;
		
		int maximum = 0;
		
		for (int i = 0; i < N; i++) {
			numArray[i] = topDepartments[i].num;
			sum += numArray[i];
			
			maximum = Math.max(maximum, numArray[i]);
		}
		
		// Case 1. More Then All > All
		if (sum <= K) {
			return maximum;
		}
		
		// Case 2. Less Then All > Maximum
		int left = 0;
		int right = K;
		int mid = (left + right) / 2;
		
		maximum = 0;
		
		while (left <= right) {
			int giftRemain = K;
			int maximumTemp = 0;
			
			for (int i = 0; i < N; i++) {
				numArray[i] = topDepartments[i].num;
				int gifted = Math.min(numArray[i], mid);
				giftRemain -= gifted;
				maximumTemp = Math.max(maximumTemp, gifted);
			}
			
			if (giftRemain < 0) {
				right = mid - 1;
				mid = (left + right) / 2;
			} else {
				left = mid + 1;
				mid = (left + right) / 2;
				
				maximum = Math.max(maximum, maximumTemp);
			}
		}
		
		return maximum;
	} // 4. DISTRIBUTE
	
}