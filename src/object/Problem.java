package object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Problem {

	protected Student[] m_Student;// 生徒集合
	protected School[] m_School;// 学校集合
	protected int[] m_MasterList;// マスターリスト
	protected InitData m_Data;// 初期データ

	public Problem(InitData data) {
		m_Data = data;
		m_Student = new Student[m_Data.M_STUDENT];
		m_School = new School[m_Data.M_SCHOOL];
		m_MasterList = new int[m_Data.M_STUDENT];
		init(data);

	}

	public void init(InitData data) {
		// TODO 学校の上限数とと下限数をここに決める場合の変更
		// TODO マスターリストの順番は生徒順
		switch (data.M_MATHINGMODE) {
		case DA:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMAL);
				m_MasterList[i] = i;

			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new School(m_Data.M_STUDENT, m_Data.M_UPPERLIMIT,
						m_Data.M_LOWERLIMIT, (i + 1));
				/*
				 * if (i == 2) { m_School[i] = new School(m_Data.M_STUDENT,
				 * m_Data.M_RESTRICTION + 1, m_Data.M_LIMITTHEMINIMUM);
				 * 
				 * } else { m_School[i] = new School(m_Data.M_STUDENT,
				 * m_Data.M_RESTRICTION, m_Data.M_LIMITTHEMINIMUM);
				 * 
				 * }
				 */
			}

			break;
		case ESDA:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMALMODE);
				m_MasterList[i] = i;

			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new Res_School(m_Data.M_STUDENT,
						m_Data.M_UPPERLIMIT, m_Data.M_LOWERLIMIT, (i + 1));
			}

			break;

		default:
			break;
		}
		/********** ファイル読み込み **********/
		try {
			if (m_Data.M_STUDENTDATANAME != null) {
				readStudentKindSeat(m_Data.M_STUDENTDATANAME, m_Student,
						m_School);
			} else {
				for (int i = 0; i < m_Data.M_STUDENT; i++) {
					m_Student[i].initKindSeat(m_School);
				}
			}
			if (m_Data.M_SCHOOLDATANAME != null) {
				readStudentKindSeat(m_Data.M_SCHOOLDATANAME, m_School,
						m_Student);
			} else {
				for (int i = 0; i < m_Data.M_SCHOOL; i++) {
					m_School[i].initKindSeat(m_Student);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*************************************/
		if (m_Data.DEBAG)
			displayResult();

	}

	/**
	 * 生徒の希望読み込み
	 *
	 * @throws IOException
	 */
	public void readStudentKindSeat(String fileName, Obj[] obj, Obj[] obj2)
			throws IOException {
		File file = new File(fileName);
		BufferedReader bf = null;
		String mes = "";
		String[] stringsplits = null;
		int counter = 0;
		try {
			bf = new BufferedReader(new FileReader(file));
			while (true) {
				mes = bf.readLine();
				if (mes == null)
					break;
				stringsplits = mes.split(",");
				int size = stringsplits.length;
				for (int i = 0; i < size; i++) {
					obj[counter].changeKindSeat(i,
							obj2[Integer.parseInt(stringsplits[i])]);
				}
				counter++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (bf != null) {
				bf.close();
			}
		}
	}

	/**
	 * 結果を表示するメソッド
	 */
	public void displayResult() {
		System.out.println("生徒のリスト");

		for (int i = 0; i < m_Data.M_STUDENT; i++) {
			System.out.print(i + "盤目の生徒 : ");
			m_Student[i].displayAssign();
			System.out.println();
		}
		System.out.println("学校のリスト");
		for (int i = 0; i < m_Data.M_SCHOOL; i++) {
			System.out.print(i + "盤目の学校ｓ : ");
			m_School[i].displayAssign();
			System.out.println();
		}
	}

	public void displayStudentSatisfy() {
		System.out.println("生徒の満足度は");
		int pos = 0;
		for (int i = 0; i < m_Data.M_SCHOOL; i++) {
			if (m_Student[i].getKindSeat()[i] == null)// 希望調査表の大きさが大きくない時の処理
				break;
			pos = i;
		}
		for (int i = 0; i < pos + 1; i++) {
			System.out.print("第" + (i + 1) + "希望の生徒人数は  ");
			int count = 0;
			for (int j = 0; j < m_Data.M_STUDENT; j++) {
				if (m_Student[j].isAssignedSchoolPos(i))
					count++;
			}
			System.out.println(count + "人です");
		}
	}

	public Student[] getStudent() {
		return m_Student;
	}

	public School[] getSchool() {
		return m_School;
	}

	public int[] getMasterList() {
		return m_MasterList;
	}

	public InitData getData() {
		return m_Data;
	}

}
