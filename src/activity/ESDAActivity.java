package activity;

import matching.Matching;
import object.InitData;
import object.Problem;

public class ESDAActivity extends Activty {
	public Problem matchingStart(InitData data, Matching matching, Problem pro) {
		boolean debug = data.M_DEBAG;
		boolean mistake = false;
		try{
			while (!matching.checkAllStudentAssigned(pro)) {
				for(int i : pro.getMasterList()){
					/*********** 生徒の学校選択 ******************/
					matching.studentSelect(pro,i);
					/*********** 学校側の生徒選択 *******************/
					matching.schoolSelect(pro);
				}
				if(debug){
					pro.displayResult();
				}
			}		
		}catch(NullPointerException e){
			mistake = true;
			System.out.println("割り当ての失敗");
		}finally{
			if(!mistake){
				result(data, matching, pro);
			}
		}
		return pro;
	}


	@Override
	public void preprocessingPretreatment(Problem pro, InitData data) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
