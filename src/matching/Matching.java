package matching;

import object.InitData;
import object.Problem;
import object.School;
import object.Student;

public abstract class Matching {
	/**
	 * デフォルトコンストラクタ
	 */
	public Matching() {
	}

	/**
	 * 生徒の選択
	 * 
	 * @param pro
	 *            問題
	 * @param studentNum 生徒番号
	 */
	public abstract void studentSelect(Problem pro, int studentNum);

	/**
	 * 学校の選択
	 * 
	 * @param pro
	 */
	public abstract void schoolSelect(Problem pro);

	/**
	 * 全ての生徒が配属されているかどうかを返すメソッド
	 *
	 * @param pro
	 * @return 全ての生徒は配属された時 true
	 */
	public boolean checkAllStudentAssigned(Problem pro) {
		for (Student stu : pro.getStudent()) {
			if (!stu.IsAssign()) {
				return false;
			}
		}
		return true;

	}
	/**
	 * 空きシートの要求件数を返すメソッド
	 * @param pro
	 * @return
	 */
	public int getRequestFreeSeat(Problem pro) {
		InitData id = pro.getData();
		int rest = id.M_UPPERLIMIT;
		int counter = 0;
		for (Student s : pro.getStudent()) {
			int satissfy = s.getSatisfy();
			if (satissfy != 0) {// 最大満足度以外の時
				for (int i = 0; i < satissfy; i++) {
					School sc =(School)s.getKindSeat()[i]; 
					if (sc.getStudentsSize() != rest) {
						counter++;
					}
				}
			}
		}
		return counter;		
	}

}
