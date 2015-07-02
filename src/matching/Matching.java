package matching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import object.InitData;
import object.Obj;
import object.School;
import object.Student;

public abstract class Matching {
	protected Student[] m_Student;// 生徒集合
	protected School[] m_School;// 学校集合
	protected int[] m_MasterList;// マスターリスト
	protected InitData m_Data;// 初期データ


	public Matching(InitData data) {
		m_Data = data;
		m_Student = new Student[m_Data.M_STUDENT];
		m_School = new School[m_Data.M_SCHOOL];
		m_MasterList = new int[m_Data.M_STUDENT];
		init();
	}
	/**
	 * マッチングアルゴリズムを書くメソッド
	 */
	public abstract void start();


	public void init() {
		// TODO 学校の上限数とと下限数をここに決める場合の変更
		// TODO マスターリストの順番は生徒順

		for (int i = 0; i < m_Data.M_STUDENT; i++) {
			m_Student[i] = new Student(m_Data.M_SCHOOL);
			m_MasterList[i] = i;

		}
		for (int i = 0; i < m_Data.M_SCHOOL; i++) {
			if(i == 2){
				m_School[i] = new School(m_Data.M_STUDENT, m_Data.M_RESTRICTION +1,
						m_Data.M_LIMITTHEMINIMUM);

			}else{
				m_School[i] = new School(m_Data.M_STUDENT, m_Data.M_RESTRICTION,
						m_Data.M_LIMITTHEMINIMUM);

			}
		}


		/********** ファイル読み込み **********/
		try {
			readStudentKindSeat(m_Data.M_STUDENTDATANAME, m_Student);
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
	public void displayResult(){
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
	/**
	 * 全ての生徒が配属されているかどうかを返すメソッド
	 *
	 * @return
	 */
	public boolean checkAllStudentAssigned() {
		for (Student stu : m_Student) {
			if (!stu.IsAssign()) {
				return false;
			}
		}
		return true;

	}


}
