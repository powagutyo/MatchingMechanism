package matching;

import object.InitData;
import object.Obj;
import object.Problem;
import object.School;
import object.Student;

public class DAMatching extends Matching {

	public DAMatching() {
	}

	@Override
	public void studentSelect(Problem pro, int studentNum) {
		if (!pro.getStudent()[studentNum].IsAssign()) {
			School hope = pro.getStudent()[studentNum].getHopeSchool();
			pro.getStudent()[studentNum].assign(hope);
			hope.assign(pro.getStudent()[studentNum]);
		}
	}

	@Override
	public void schoolSelect(Problem pro) {
		for (School s : pro.getSchool()) {
			while (true) {
				if (!s.isRestriction()) {
					break;
				}
				Student stu = (Student) s.refuse();
				stu.refused();
			}
		}
	}

}
