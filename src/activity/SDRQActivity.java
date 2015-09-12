package activity;

import matching.DAMatching;
import matching.Matching;
import matching.SDRQMatching;
import object.InitData;
import object.Problem;

public class SDRQActivity extends Activty {

	@Override
	public Problem matchingStart(InitData data, Matching matching, Problem pro) {
		boolean debug = data.M_DEBAG;
		boolean mistake = false;
		try {
			for (int i = 0; i < pro.getData().M_STUDENT; i++) {
				/*********** 生徒の学校選択 ******************/
				matching.studentSelect(pro, i);
			}
			if (debug) {
				pro.displayResult();
			}

		} catch (NullPointerException e) {
			mistake = true;
			System.out.println("割り当ての失敗");
		} finally {
			if (!mistake) {
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
