package matching;

import object.Problem;
import object.School;
import object.Res_School;
import object.Student;

public class ESDAMatching extends Matching {

	public ESDAMatching() {
	}

	@Override
	public void studentSelect(Problem pro, int studentNum) {
		Student stu = (Student) pro.getStudent()[studentNum];
		if (!stu.IsAssign()) {
			Res_School hope = (Res_School) pro.getStudent()[studentNum]
					.getHopeSchool();
			pro.getStudent()[studentNum].assign(hope);
			hope.assign(pro.getStudent()[studentNum]);
		}
	}

	@Override
	public void schoolSelect(Problem pro) {
		int ul_Size = pro.getData().M_STUDENT;// 拡張枠の人数の大きさを表している
		/**************通常枠と拡張枠の個々の処理******************/
		for (School sch : pro.getSchool()) {// 通常枠と拡張枠ともの処理
			Res_School r_sch = (Res_School) sch;
			while (true) {
				if (!r_sch.isRestriction()) {
					break;
				}
				Student stu = (Student) r_sch.refuse();
				stu.refused();
				stu.changeState();
			}
			/********生徒の数 - (sum(学校側の下限人数 )+ sum(拡張枠にどのくらい生徒が割り当てられているか))*********/
			ul_Size -= (r_sch.getLowerLimit() + r_sch.getUpperLimitStudentsSize());
		}
		/********************拡張枠の人数のOVER分の処理*********************/
		if (ul_Size < 0) {// 拡張枠の大きさにはみ出していた場合
			ul_Size = -ul_Size;
			int pos = 0;
			int sch_num = 0;
			for (int i = 0; i < ul_Size; i++) {
				int counter = 0;
				for (School sch : pro.getSchool()) {// 通常枠と拡張枠ともの処理
					Res_School r_sch = (Res_School) sch;
					int w_pos = r_sch.getWorstStudentPos();
					if(pos  < w_pos){
						sch_num = counter;
						pos = w_pos;
					}
					counter++;
				}
				Res_School r_sch = (Res_School)pro.getSchool()[sch_num];
				Student stu = (Student) r_sch.refuse();
				stu.refused();
				stu.changeState();
			}
		}
	}
}
