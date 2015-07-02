package matching;

import object.InitData;
import object.Problem;
import object.School;

public class DAMatching extends Matching {

	public DAMatching() {
	}
	@Override
	public void studentSelect(Problem pro) {
		for (int stuNum : pro.getM_MasterList()) {
			if (!pro.getM_Student()[stuNum].IsAssign()) {
				int hope = pro.getM_Student()[stuNum].putHopeInSchool();
				pro.getM_Student()[stuNum].assign(hope);
				pro.getM_School()[hope].assign(stuNum);
			}
		}
		
	}
	@Override
	public void schoolSelect(Problem pro) {
		for(School s : pro.getM_School()){
			while(true){
				if(!s.isExceedRestriction()){
					break;
				}
				int SNum = s.refuse();
				pro.getM_Student()[SNum].refused();
			}
		}
		
	}





}
