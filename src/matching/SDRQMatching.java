package matching;

import object.InitData;
import object.Problem;
import object.Res_School;
import object.School;
import object.Student;
import object.Tree_School;

public class SDRQMatching extends TreeMatching {

	@Override
	public void studentSelect(Problem pro, int studentNum) {
		int k = pro.getData().M_STUDENT - studentNum;
		Student stu = pro.getStudent()[pro.getMasterList()[studentNum]];
		int kindSeatSize = pro.getData().M_SCHOOL;
		if (pro.getParentRegion().getLowerLimit() < k) {// 上限のみで考える
			for (int i = 0; i < kindSeatSize; i++) {
				Tree_School sch = (Tree_School)stu.getKindSeat()[i];
				if(sch.getLeafNode().getUpperLimit() > 0){// 上限枠を超えていないのでまだ割り当て出来る
					stu.assign(sch);
					sch.assign(stu);	
					sch.getLeafNode().upDateLimitNumber();
					return;
				}
			}		
		} else {// 下限のみで考える
			for (int i = 0; i < kindSeatSize; i++) {
				Tree_School sch = (Tree_School)stu.getKindSeat()[i];
				if(sch.getLeafNode().getUpperLimit() > 0 ){// 上限枠を超えていないのでまだ割り当て出来る
					if(sch.getLeafNode().canAssain()){
						stu.assign(sch);
						sch.assign(stu);
						sch.getLeafNode().upDateLimitNumber();
						return;
					}
				}
			}
		}
	}

	@Override
	public void schoolSelect(Problem pro) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	


}
