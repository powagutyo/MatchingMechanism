package activity;

import matching.MSDARQMatching;
import matching.Matching;
import object.InitData;
import object.Problem;
import object.Tree_School;

public class MSDARQActivity extends Activty {
	public int E0;
	public int E1;

	public int startPoint;
	public int endPoint;

	@Override
	public Problem matchingStart(InitData data, Matching matching, Problem pro) {
		boolean debug = data.M_DEBAG;

		MSDARQMatching m = (MSDARQMatching) matching;
		// DAマッチングの部分
		final int C_STUDNTNUMBER = pro.getData().M_STUDENT;
		E0 = C_STUDNTNUMBER;
		E1 = pro.getParentRegion().getLowerLimit();

		startPoint = 0;
		endPoint = E0 - E1;
		
		pro = startDA(pro, debug, m, C_STUDNTNUMBER, data);
		
		m.modeChange();
		
		pro = startSDRQ(pro, debug, m, C_STUDNTNUMBER);

		m.modeChange();
		
		return pro;
	}

	protected Problem startDA(Problem pro, boolean debug, MSDARQMatching m,
			final int C_STUDNTNUMBER, InitData data) {
		while (true) {
			if(oneCycleDA(pro, debug, m, C_STUDNTNUMBER, startPoint, endPoint))
				break;
		}
		return pro;
	}

	protected boolean oneCycleDA(Problem pro, boolean debug, MSDARQMatching m,
			final int C_STUDNTNUMBER, int startPoint, int endPoint) {
		oneTimeDA(pro, debug, m, startPoint, endPoint);

		updateRegion(pro,startPoint, endPoint);

		updatePoints(pro, C_STUDNTNUMBER);
		
		if(E0 == E1)
			return true;
		
		return false;
	}

	protected void updatePoints(Problem pro, final int C_STUDNTNUMBER) {
		/*********** 更新式 ***********/
		E0 = E1;
		E1 = pro.getParentRegion().getLowerLimit();

		startPoint = endPoint;
		endPoint = C_STUDNTNUMBER - E1;
		/***************************/
	}

	protected void updateRegion(Problem pro, int startPoint, int endPoint) {
		// REGIONの更新
		for (int i = startPoint; i < endPoint; i++) {
			/*********** 生徒の学校選択 ******************/
			Tree_School ts = (Tree_School) pro.getStudent()[pro.getMasterList()[i]]
					.getAssignedSchool();
			ts.getLeafNode().upDateLimitNumber();
		}
		for (int i = startPoint; i < endPoint; i++) {
			pro.getStudent()[pro.getMasterList()[i]].changeAssignmentDecision();
		}
	}

	protected void oneTimeDA(Problem pro, boolean debug, MSDARQMatching m,
			int startPoint, int endPoint) {
		/****************** DAマッチング部分 ********************/
		while (!m.checkPartStudentAssaigned(pro, startPoint, endPoint)) {
			for (int i = startPoint; i < endPoint; i++) {
				/*********** 生徒の学校選択 ******************/
				m.studentSelect(pro, pro.getMasterList()[i]);
			}
			/*********** 学校側の生徒選択 *******************/
			m.schoolSelect(pro);
			
			if (debug) {
				pro.displayResult();
			}
		}
	}

	
	protected Problem startSDRQ(Problem pro, boolean debug, MSDARQMatching m,
			final int C_STUDNTNUMBER) {
		// SDRQの部分
		for (int i = endPoint; i < C_STUDNTNUMBER; i++) {
			/*********** 生徒の学校選択 ******************/
			m.studentSelect(pro, i);
		}
		if (debug) {
			pro.displayResult();
		}
		return pro;
	}


	@Override
	public void preprocessingPretreatment(Problem pro, InitData data) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
