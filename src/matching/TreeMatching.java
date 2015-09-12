package matching;

import object.InitData;
import object.Problem;
import object.Region;
import object.Student;
import object.Tree_School;

public class TreeMatching extends Matching {

	@Override
	public void studentSelect(Problem pro, int studentNum) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void schoolSelect(Problem pro) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * 空きシートの要求件数を返すメソッド
	 * 
	 * @param pro
	 * @return
	 */
	public int getRequestFreeSeat(Problem pro) {
		int counter = 0;
		for (Student s : pro.getStudent()) {
			int satissfy = s.getSatisfy();
			if (satissfy != 0) {// 最大満足度以外の時
				Tree_School sch2 = (Tree_School) s.getAssignedSchool();
				for (int i = 0; i < satissfy; i++) {
					Tree_School sch1 = (Tree_School) s.getKindSeat()[i];
					if (canAssainUpperLimit(pro, sch1.getLeafNode())
							&& canAssainLowerLimit(pro, sch2.getLeafNode())) {
						counter++;
					}

				}
			}
		}
		return counter;
	}

	public boolean canAssainUpperLimit(Problem pro, Region region) {
		if (region.getParent() == null) {
			return true;
		} else {
			if (region.getAssainedStudent() < region.getFirstUpperLimit()) {
				return canAssainUpperLimit(pro, region.getParent());
			}
			return false;
		}
	}

	public boolean canAssainLowerLimit(Problem pro, Region region) {

		if (region.getParent() == null) {
			return true;
		} else {
			if (region.getAssainedStudent() > region.getFirstLowerLimit()) {
				return canAssainLowerLimit(pro, region.getParent());
			}
			return false;
		}
	}

}
