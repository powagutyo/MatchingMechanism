package matching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import object.InitData;
import object.Obj;
import object.Problem;
import object.School;
import object.Student;

public abstract class Matching {
	

	public Matching() {
		}
	/**
	 * 生徒の選択
	 * @param pro 問題
	 */
	public abstract void studentSelect(Problem pro);
	/**
	 * 学校の選択
	 */
	public abstract void schoolSelect(Problem pro);



	/**
	 * 全ての生徒が配属されているかどうかを返すメソッド
	 *
	 * @return
	 */
	public boolean checkAllStudentAssigned(Problem pro) {
		for (Student stu : pro.getM_Student()) {
			if (!stu.IsAssign()) {
				return false;
			}
		}
		return true;

	}


}
