package matching;

import object.InitData;
import object.School;

public class DAMatching extends Matching {

	public DAMatching(InitData data) {
		super(data);

	}
	@Override
	public void start() {
		int step = 1;
		while (!checkAllStudentAssigned()) {
			if (m_Data.DEBAG)
				System.out.println(step + "step目");
			/*********** 生徒の学校選択 ******************/
			for (int stuNum : m_MasterList) {
				if (!m_Student[stuNum].IsAssign()) {
					int hope = m_Student[stuNum].putHopeInSchool();
					m_Student[stuNum].assign(hope);
					m_School[hope].assign(stuNum);
				}
			}
			/*********** 学校側の生徒選択 *******************/
			for(School s : m_School){
				while(true){
					if(!s.isExceedRestriction()){
						break;
					}
					int SNum = s.refuse();
					m_Student[SNum].refused();
				}
			}
			if(m_Data.DEBAG){
				displayResult();
			}
			step++;
		}
		System.out.println("マッチング結果は");
		displayResult();
	}





}
