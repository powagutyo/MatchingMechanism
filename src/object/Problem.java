package object;

import io.FileManager;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import object.InitData.MATCHINGMODE;

public class Problem {

	protected Student[] m_Student;// 生徒集合
	protected School[] m_School;// 学校集合
	protected int[] m_MasterList;// マスターリスト
	protected InitData m_Data;// 初期データ
	protected int[] m_ResultAssained;// 最終的な割り当ての結果
	protected int[] m_ResultAssained_school;// 最終的な割り当ての結果
	protected final Random M_RANDOM;
	protected Region m_ParentRegion; // 一番大きいRegion地域

	protected int m_RequestFreeSeat;// 空きシートの数
	protected int m_notSatisfyStudent;// 妥当な不満を持つ生徒
	protected long m_Time;// かかった時間
	
	protected int m_ProvisionalDANumber; // 仮DAマッチングの回数

	public Problem(InitData data) {
		m_Data = data;
		m_Student = new Student[m_Data.M_STUDENT];
		m_School = new School[m_Data.M_SCHOOL];
		m_MasterList = new int[m_Data.M_STUDENT];
		m_ResultAssained = new int[m_Data.M_HOPESTUDENTNUMBER];
		m_ResultAssained_school = new int[m_Data.M_HOPESCHOOLNUMBER];
		M_RANDOM = new Random(m_Data.M_SEEDNUM);
		m_ParentRegion = new Region(null, m_Data.M_STUDENT, -1,
				m_Data.M_PARTLOWERLIMIT);
		m_ProvisionalDANumber = 0;
		init(data);
	}

	public void init(InitData data) {
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
		case SDRQ:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMAL);
				m_MasterList[i] = i;
			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new Tree_School(m_Data.M_STUDENT,
						m_Data.M_UPPERLIMIT, m_Data.M_LOWERLIMIT, (i + 1));
			}
			break;
		case MSDARQ:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMAL);
				m_MasterList[i] = i;
			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new Tree_School(m_Data.M_STUDENT,
						m_Data.M_UPPERLIMIT, m_Data.M_LOWERLIMIT, (i + 1));
			}
			break;
		case MSDARQ_E:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMAL);
				m_MasterList[i] = i;
			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new Tree_School(m_Data.M_STUDENT,
						m_Data.M_UPPERLIMIT, m_Data.M_LOWERLIMIT, (i + 1));
			}
			break;
		case MSDARQ_E2:
			for (int i = 0; i < m_Data.M_STUDENT; i++) {
				m_Student[i] = new Student(m_Data.M_SCHOOL, (i + 1),
						StudentState.NORMAL);
				m_MasterList[i] = i;
			}
			for (int i = 0; i < m_Data.M_SCHOOL; i++) {
				m_School[i] = new Tree_School(m_Data.M_STUDENT,
						m_Data.M_UPPERLIMIT, m_Data.M_LOWERLIMIT, (i + 1));
			}
			break;

		default:
			break;
		}
		// ランダムマスターリスト
		if (data.M_ISRANDOMSELECT_MASTERLIST) {
			initRandomMaterList(data);
		}

		/********** ファイル読み込み **********/
		try {
			if (m_Data.M_ISRANDOMSELECT_STUDENT) {// ランダムinit
				// initRandomObject(data, m_Student, m_School);
				initStudentBias(data, m_Student, m_School);
			} else {
				if (m_Data.M_STUDENTDATANAME != null) {// ファイル読み込み
					FileManager.readStudentKindSeat(m_Data.M_STUDENTDATANAME,
							m_Student, m_School);
				} else {// 1から順に選ぶ
					for (int i = 0; i < m_Data.M_STUDENT; i++) {
						m_Student[i].initKindSeat(m_School);
					}
				}
			}
			if (m_Data.M_ISRANDOMSELECT_SCHOOL) {// ランダムinit
				initRandomObject(data, m_School, m_Student);
			} else {// ファイル読み込み
				if (m_Data.M_SCHOOLDATANAME != null) {
					FileManager.readStudentKindSeat(m_Data.M_SCHOOLDATANAME,
							m_School, m_Student);
				} else {// 1から順に選ぶ
					for (int i = 0; i < m_Data.M_SCHOOL; i++) {
						m_School[i].initKindSeat(m_Student);
					}
				}
			}
			// Regionの作成
			if (m_Data.M_MATHINGMODE == MATCHINGMODE.SDRQ
					|| m_Data.M_MATHINGMODE == MATCHINGMODE.MSDARQ
					|| m_Data.M_MATHINGMODE == MATCHINGMODE.MSDARQ_E
					|| m_Data.M_MATHINGMODE == MATCHINGMODE.MSDARQ_E2) {
				// TODO Region関係
				/**
				 * Regionが2分木しか作れないこと Regionの途中の要素に関して、上限下限の設定が出来ない
				 */

				initRegionVinaryTree(m_ParentRegion, 0, data.M_SCHOOL);
				int num = getRegionNumber(0, m_ParentRegion);// regionの数を把握
				ArrayList<Integer> pllList = getPartLowerLimitLit(num,
						m_Data.M_PARTLOWERLIMIT);
				setPllListToRegion(pllList, m_ParentRegion);
				// Regionの更新
				m_ParentRegion.initLimitNumber();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*************************************/
		if (m_Data.M_DEBAG)
			displayResult();
	}

	/**
	 * マスターリストをランダムで作成するメソッド
	 * 
	 * @param data
	 */
	public void initRandomMaterList(InitData data) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int size = data.M_STUDENT;
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		for (int i = size - 1; i >= 0; i--) {
			m_MasterList[i] = list.remove((int) ((i + 0.5) * M_RANDOM
					.nextDouble()));
		}
	}

	public int getRegionNumber(int counter, Region region) {
		if (region.getChildren().size() != 0) {// 葉ノードの場合
			counter = getRegionNumber(counter, region.getChildren().get(0));
			counter = getRegionNumber(counter, region.getChildren().get(1));
		}
		return counter + 1;
	}

	public ArrayList<Integer> getPartLowerLimitLit(int regionNumber,
			int partLowerLimit) {
		ArrayList<Integer> pllList = new ArrayList<Integer>(regionNumber);
		int rest = partLowerLimit % regionNumber;
		int quo = partLowerLimit / regionNumber;

		for (int i = 0; i < regionNumber; i++) {
			if (i < rest) {
				pllList.add(quo + 1);
			} else {
				pllList.add(quo);
			}
		}
		return pllList;
	}

	public void setPllListToRegion(ArrayList<Integer> pllList, Region region) {
		region.setSurplusLimit(pllList.remove(0));
		if (region.getChildren().size() != 0) {
			for (int i = 0; i < 2; i++) {
				setPllListToRegion(pllList, region.getChildren().get(i));
			}
		}
	}

	/**
	 * Regionを2分木で作成するメソッド
	 * 
	 * @param parent
	 * @param before
	 * @param after
	 */
	public void initRegionVinaryTree(Region parent, int before, int after) {
		for (int i = before; i < after; i++) {
			parent.assainRegion(m_School[i]);
		}
		int num = (after - before) / 2;
		if (num == 0) {
			Tree_School sch = (Tree_School) m_School[before];
			sch.setLeafNode(parent);
			return;
		}
		for (int i = 0; i < 2; i++) {
			parent.addChildren(new Region(parent, -1, -1, 0));

			/*
			 * parent.addChildren(new Region(parent, -1, -1,
			 * m_Data.M_PARTLOWERLIMIT));
			 */
		}
		initRegionVinaryTree(parent.getChildren().get(0), before, before + num);
		initRegionVinaryTree(parent.getChildren().get(1), before + num, after);

	}

	/**
	 * obj1にobj2を選考リストに割り当てるメソッド
	 * 
	 * @param data
	 * @param obj
	 * @param obj2
	 */
	public void initRandomObject(InitData data, Obj[] obj, Obj[] obj2) {
		int size, hope;
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (obj[0].getClass() == Student.class) {// Studentクラス
			size = data.M_STUDENT;
			hope = data.M_HOPESTUDENTNUMBER;
		} else if (obj[0].getClass() == School.class
				|| obj[0].getClass() == Tree_School.class
				|| obj[0].getClass() == Res_School.class) {// Schoolクラス
			size = data.M_SCHOOL;
			hope = data.M_HOPESCHOOLNUMBER;
		} else {
			return;
		}
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < hope; i++) {
				list.add(i);
			}
			for (int i = hope - 1; i >= 0; i--) {
				int num = list
						.remove((int) ((i + 0.5) * M_RANDOM.nextDouble()));
				obj[j].changeKindSeat(i, obj2[num]);
			}
		}
	}

	public ArrayList<Region> getLeafNode(ArrayList<Region> leafNodes,
			Region region) {
		if (region.getChildren().size() == 0) {
			leafNodes.add(region);
			return leafNodes;
		} else {
			for (int i = 0; i < region.getChildren().size(); i++) {
				leafNodes = getLeafNode(leafNodes, region.getChildren().get(i));
			}
		}
		return leafNodes;
	}

	public void initStudentBias(InitData data, Student[] student,
			School[] school) {
		double[] schoolBias = new double[data.M_SCHOOL];

		for (int i = 0; i < data.M_SCHOOL; i++)
			schoolBias[i] = M_RANDOM.nextDouble();

		double[] result = new double[data.M_SCHOOL];

		TreeMap<Double, Integer> tm = new TreeMap<Double, Integer>();

		for (int i = 0; i < m_Data.M_STUDENT; i++) {
			tm.clear();
			for (int j = 0; j < m_Data.M_SCHOOL; j++) {
				result[j] = schoolBias[j] * data.M_STUDENTBIAS
						+ (1 - data.M_STUDENTBIAS) * M_RANDOM.nextDouble();
				tm.put(result[j], j);
			}
			for (int j = 0; j < m_Data.M_SCHOOL; j++) {
				int counter = 0;
				for (Double num : tm.keySet()) {
					student[i].changeKindSeat(counter, school[tm.get(num)]);
					counter++;
				}
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

	public void displayStudentSatisfy(InitData data) {
		if (data.M_SHOWRESULT)
			System.out.println("生徒の満足度は");
		int assainStundet = 0;
		for (int i = 0; i < m_Data.M_HOPESTUDENTNUMBER; i++) {
			if (data.M_SHOWRESULT)
				System.out.print("第" + (i + 1) + "希望の生徒人数は  ");
			int count = 0;
			for (int j = 0; j < m_Data.M_STUDENT; j++) {
				if (m_Student[j].isAssignedSchoolPos(i))
					count++;
			}
			assainStundet += count;
			m_ResultAssained[i] = count;
			if (data.M_SHOWRESULT)
				System.out.println(count + "人です");
		}

		if (data.M_DEBAG)
			System.out.println(assainStundet);
	}

	public void calcSchoolSatisfy(InitData data) {
		for (int i = 0; i < m_Data.M_HOPESCHOOLNUMBER; i++) {
			m_ResultAssained_school[i] = 0;
		}
		for (int j = 0; j < m_Data.M_STUDENT; j++) {
			m_ResultAssained_school[m_Student[j].m_AssignedSchool.getSatisfy(m_Student[j])]++;
		}
	}

	public void displayComplaintsStudent() {
		if (this.getData().M_SHOWRESULT)
			System.out.print("妥当な不満を持つ生徒");
		int count = 0;
		int studentNum = this.getData().M_STUDENT;

		for (int i = 0; i < studentNum; i++) {
			Student s1 = this.getStudent()[i];
			int staisfy = s1.getSatisfy();
			if (staisfy <= 0) {
				continue;
			}
			boolean flag = false;
			for (int j = 0; j < staisfy; j++) {
				School sch = (School) s1.getKindSeat()[j];
				for (Student s2 : sch.m_Students) {
					if (sch.getSatisfy(s2) > sch.getSatisfy(s1)) {// つまりs1の方が第1希望に近かった場合
						count++;
						flag = true;
						break;
					}

				}
				if (flag)
					break;
			}

		}
		m_notSatisfyStudent = count;
		if (this.getData().M_SHOWRESULT)
			System.out.println(count + "人");
	}

	public void writeExcel() {
		FileManager.excel_FileWrite(this);
	}

	public int[] getResultAssained() {
		return m_ResultAssained;
	}
	public int[] getResultAssained_school() {
		return m_ResultAssained_school;
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

	public Region getParentRegion() {
		return m_ParentRegion;
	}

	public void setRequestFreeSeat(int m_RequestFreeSeat) {
		this.m_RequestFreeSeat = m_RequestFreeSeat;
	}

	public void setTime(long time) {
		this.m_Time = time;
	}

	public int getRequestFreeSeat() {
		return m_RequestFreeSeat;
	}

	public int getNotSatisfyStudent() {
		return m_notSatisfyStudent;
	}

	public long getTime() {
		return m_Time;
	}
	public int getProvisionalDANumber() {
		return m_ProvisionalDANumber;
	}

	public void setProvisionalDANumber(int provisionalDANumber) {
		this.m_ProvisionalDANumber = provisionalDANumber;
	}
}
