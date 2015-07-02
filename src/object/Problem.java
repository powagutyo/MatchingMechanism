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
		init();

	}

	public void init() {
		// TODO 学校の上限数とと下限数をここに決める場合の変更
		// TODO マスターリストの順番は生徒順

		for (int i = 0; i < m_Data.M_STUDENT; i++) {
			m_Student[i] = new Student(m_Data.M_SCHOOL);
			m_MasterList[i] = i;

		}
		for (int i = 0; i < m_Data.M_SCHOOL; i++) {
			m_School[i] = new School(m_Data.M_STUDENT, m_Data.M_RESTRICTION,
					m_Data.M_LIMITTHEMINIMUM);
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
		/********** ファイル読み込み **********/
		try {
			if (m_Data.M_STUDENTDATANAME != null)
				readStudentKindSeat(m_Data.M_STUDENTDATANAME, m_Student);
			if (m_Data.M_SCHOOLDATANAME != null)
				readStudentKindSeat(m_Data.M_SCHOOLDATANAME, m_School);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		/*************************************/
		if (m_Data.DEBAG) {
			System.out.println("生徒のリスト");
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				System.out.print(i + "盤目の生徒 : ");
				m_Student[i].displayKindSeat();
				;
			}
			System.out.println("学校のリスト");
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				System.out.print(i + "盤目の学校ｓ : ");
				m_School[i].displayKindSeat();

			}
		}
	}

	/**
	 * 生徒の希望読み込み
	 *
	 * @throws IOException
	 */
	public void readStudentKindSeat(String fileName, Obj[] obj)
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
							Integer.parseInt(stringsplits[i]));
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
			if (m_Student[i].getKindSeat()[i] == -1)
				break;
			pos = i;
		}
		for (int i = 0; i < pos+1; i++) {
			System.out.print("第" + (i+1) + "希望の生徒人数は  ");
			int count = 0;
			for (int j = 0; j < m_Data.M_STUDENT; j++) {
				if(m_Student[j].isSatisfy(i))	
					count++;
			}
			System.out.println(count+"人です");
		}
	}

	public Student[] getM_Student() {
		return m_Student;
	}

	public School[] getM_School() {
		return m_School;
	}

	public int[] getM_MasterList() {
		return m_MasterList;
	}

	public InitData getM_Data() {
		return m_Data;
	}
}
