import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserSolution {
	
	// [DECLARATION]
	static class Student implements Comparable<Student> {
		int id;
		int score;
		public Student(int id, int score) {
			super();
			this.id = id;
			this.score = score;
		}
		@Override
		public int compareTo(Student o) {
			return this.score == o.score ? this.id - o.id : this.score - o.score;
		}
	}
	
	static int getGroup(int grade, char gender[]) {
		return String.valueOf(gender).trim().equals("male") ? grade : grade + 3;
	}
	
	static int getIndex(List<Student> list, Student student) {
		int index = Collections.binarySearch(list, student);
		return index < 0 ? -index - 1 : index;
	}
	
	static List<List<Student>> studentList; // M1 / M2 / M3 / F1 / F2 / F3
	static Map<Integer, int[]> studentMap; // Id : { Group, Score }
	// [DECLARATION]
	
	// 100. INIT
	public void init() {
		studentList = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			studentList.add(new ArrayList<>());
		}
		studentMap = new HashMap<>();
	} // 100. INIT
	
	// 200. ADD
	public int add(int mId, int mGrade, char mGender[], int mScore) {
		Student newStudent = new Student(mId, mScore);
		int group = getGroup(mGrade, mGender);
		int index = getIndex(studentList.get(group), newStudent);
		
		studentList.get(group).add(index, newStudent);
		studentMap.put(mId, new int[] {group, mScore});
		
		int last = studentList.get(group).size() - 1;
		int result = studentList.get(group).get(last).id;
		return result;
	} // 200. ADD
	
	// 300. REMOVE
	public int remove(int mId) {
		// Case 1. Not Exist > (0)
		if (studentMap.get(mId) == null) {
			return 0;
		}
		
		int group = studentMap.get(mId)[0];
		Student tempStudent = new Student(mId, studentMap.get(mId)[1]);
		int index = getIndex(studentList.get(group), tempStudent);
		
		studentList.get(group).remove(index);
		studentMap.remove(mId);
		
		// Case 2-1. Empty Group > (0)
		if (studentList.get(group).isEmpty()) {
			return 0;
		}
		
		// Case 2-2. Not Empty > (First One's Id)
		return studentList.get(group).get(0).id;
	} // 300. REMOVE
	
	// 400. QUERY
	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		List<Student> list = new ArrayList<>();
		
		for (int i = 0; i < mGradeCnt; i++) {
			for (int j = 0; j < mGenderCnt; j++) {
				int group = getGroup(mGrade[i], mGender[j]);
				Student tempStudent = new Student(0, mScore);
				int index = getIndex(studentList.get(group), tempStudent);
				if (index < studentList.get(group).size()) {
					Student student = studentList.get(group).get(index);
					list.add(getIndex(list, student), student);
				}
				
			}
		}
		
		// Case 1. Empty > (0)
		if (list.isEmpty()) {
			return 0;
		}
		
		// Case 2. Not Empty > (First One's Id)
		return list.get(0).id;
	} // 400. QUERY
	
}