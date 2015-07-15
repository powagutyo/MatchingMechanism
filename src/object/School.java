package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class School extends Obj {

	protected int m_UpperLimit; // 上限人数

	protected final int m_LowerLimit; // 下限人数

	protected ArrayList<Student> m_Students;// 生徒群

	public School(int kindSeatNum, int ul, int ll, int id) {
		super(kindSeatNum, id);
		this.m_UpperLimit = ul;
		this.m_LowerLimit = ll;
		this.m_Students = new ArrayList<Student>();
	}
	
	@Override
	public void assign(Obj obj) {
		this.m_Students.add((Student)obj);
	}

	/**
	 * 学校から一番嫌いの生徒を拒否する時のメソッド
	 *
	 * @return 生徒番号
	 */
	public Student refuse() {
		sortKindSeatOrder(m_Students);
		Student stu  = m_Students.remove(m_Students.size() - 1);
		return stu;
	}

	/**
	 * 上限人数を超えているかを判定するメソッド
	 *
	 * @return
	 */
	public boolean isRestriction() {
		if (m_UpperLimit < m_Students.size())
			return true;
		return false;
	}

	@Override
	public void displayAssign() {
		System.out.print("所属生徒 : ");
		for(Student stu :m_Students){
			System.out.print(stu.getID() + " ");
		}
	}

	/**
	 * m_StudentsをkindSeatの大きい順番でソートを行う
	 */
	public void sortKindSeatOrder(ArrayList<Student> obj) {
		// TODO 毎回無名関数を定義してるから自作のソートクラスを作成をした方がいいかも
		Collections.sort(obj, new Comparator<Obj>() {
			@Override
			public int compare(Obj o1, Obj o2) {
				int seatPoint1 = -1;
				int seatPoint2 = -1;
				int size = m_KindSeat.length;
				for (int i = 0; i < size; i++) {
					if (m_KindSeat[i] == o1) {
						seatPoint1 = i;
					}
					if (m_KindSeat[i] == o2) {
						seatPoint2 = i;
					}
				}
				if (seatPoint2 < seatPoint1){
					return 1;
				}else{
					return -1;		
				}
			}
		});
	}
	
	public int getStudentsSize(){
		return m_Students.size();
	}
	public void setUpperLimit(int upperLimit) {
		this.m_UpperLimit = upperLimit;
	}
	
	public int getUpperLimit() {
		return m_UpperLimit;
	}

	public int getLowerLimit() {
		return m_LowerLimit;
	}

	

}
