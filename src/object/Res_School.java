package object;

import java.util.ArrayList;

public class Res_School extends School {
	protected ArrayList<Student> m_UpperLimitList;// 上限用のリスト
	protected ArrayList<Student> m_LowerLimitList;// 下限用のリスト
	public Res_School(int kindSeatNum, int restriction, int limittheMinimum,
			int id) {
		super(kindSeatNum, restriction, limittheMinimum, id);
		m_UpperLimitList = new ArrayList<Student>();
		m_LowerLimitList = new ArrayList<Student>();
	}

	@Override
	public void assign(Obj obj) {
		Student stu = (Student) obj;
		switch (stu.getState()) {
		case NORMALMODE:// 通常枠
			m_LowerLimitList.add(stu);
			break;
		case EXTENSIONMODE:// 拡張枠
			m_UpperLimitList.add(stu);
			break;
		default:
			break;
		}
	}

	@Override
	public Student refuse() {
		if (m_LowerLimitList.size() > m_LowerLimit) {// 下限制約の拒否
			sortKindSeatOrder(m_LowerLimitList);
			return m_LowerLimitList.remove(m_LowerLimitList.size() - 1);
		} else {//上限制約の拒否
			sortKindSeatOrder(m_UpperLimitList);
			return m_UpperLimitList.remove(m_UpperLimitList.size() - 1);
		}
	}
	@Override
	public void displayAssign() {
		m_Students.clear();
		for(Student s: m_LowerLimitList){
			m_Students.add(s);
		}
		for(Student s: m_UpperLimitList){
			m_Students.add(s);
		}
		super.displayAssign();
	}


	@Override
	public boolean isRestriction() {
		if (m_LowerLimitList.size() > m_LowerLimit)// 通常枠の処理
			return true;
		if (m_UpperLimit - m_LowerLimit < m_UpperLimitList.size())// 拡張枠の処理
			return true;
		return false;
	}
	/**
	 * 拡張枠に配属されている学生に対して、学校側の一番評価の低い学生の順位を返すメソッド
	 * @return
	 */
	public int getWorstStudentPos() {
		int pos = 0;
		for (Student stu : m_UpperLimitList) {
			int num = getStudentkindSeatPos(stu);
			if(pos < num){
				pos = num;
			}
		}
		return pos;
	}
	/**
	 * 受け取った生徒の学校側の順位を返すメソッド
	 * @param stu
	 * @return
	 */
	public int getStudentkindSeatPos(Student stu){
		int size = m_KindSeat.length;
		for(int i= 0;i< size;i++){
			if(m_KindSeat[i] == stu){
				return i;
			}
		}
		return -1;
	}
	
	public int getStudentsSize() {
		return m_LowerLimitList.size() + m_UpperLimitList.size();
	}

	public int getUpperLimitStudentsSize() {
		return m_UpperLimitList.size();
	}
}
