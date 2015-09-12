package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class School extends Obj {

	private final int m_Restriction; // 上限人数

	private final int m_LimittheMinimum; // 下限人数

	private ArrayList<Integer> m_Students;// 生徒群

	public School(int kindSeatNum, int restriction, int limittheMinimum) {
		super(kindSeatNum);
		this.m_Restriction = restriction;
		this.m_LimittheMinimum = limittheMinimum;
		this.m_Students = new ArrayList<Integer>();
	}

	@Override
	public void assign(int StudentNum) {
		addStudents(StudentNum);
	}

	/**
	 * 学校から一番嫌いの生徒を拒否する時のメソッド
	 *
	 * @return 生徒番号
	 */
	public int refuse() {
		sortKindSeatOrder();
		int stuNum = -1;
		stuNum = m_Students.remove(m_Students.size() - 1);
		return stuNum;
	}

	/**
	 * 上限人数を超えているかを判定するメソッド
	 *
	 * @return
	 */
	public boolean isExceedRestriction() {
		if (m_Restriction < m_Students.size())
			return true;
		return false;
	}

	@Override
	public void displayAssign() {
		System.out.print("所属生徒 : ");
		for(int num :m_Students){
			System.out.print(num + " ");
		}
	}

	/**
	 * m_StudentsをkindSeatの大きい順番でソートを行う
	 */
	public void sortKindSeatOrder() {
		// TODO 毎回無名関数を定義してるから自作のソートクラスを作成をした方がいいかも
		Collections.sort(m_Students, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
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
				if (seatPoint2 < seatPoint1)
					return 1;
				return -1;
			}
		});
	}

	/**
	 * 生徒の番号
	 *
	 * @param SNum
	 */
	public void addStudents(int SNum) {
		this.m_Students.add(SNum);
	}

	public int getM_Restriction() {
		return m_Restriction;
	}

	public int getM_LimittheMinimum() {
		return m_LimittheMinimum;
	}

}
