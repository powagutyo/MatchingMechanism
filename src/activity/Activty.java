package activity;

import matching.Matching;
import object.InitData;
import object.Problem;

public abstract class Activty {

	public abstract Problem matchingStart(InitData data, Matching matching,
			Problem pro);
	/**
	 * 前処理を行うメソッド
	 */
	public abstract void preprocessingPretreatment(Problem pro, InitData data);
	
	public Activty() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public Problem result(InitData data, Matching matching, Problem pro) {
		if (data.M_SHOWRESULT)
			System.out.println("マッチング結果は");
		if (data.M_SHOWRESULT)
			pro.displayResult();
		pro.displayStudentSatisfy(data);
		pro.calcSchoolSatisfy(data);
		pro.displayComplaintsStudent();
		int num = matching.getRequestFreeSeat(pro);
		if (data.M_SHOWRESULT)
			System.out.println("空きシートの要求数は" + num);
		pro.setRequestFreeSeat(num);
		return pro;
	}
}
