package activity;

import matching.MSDARQMatching;
import matching.Matching;
import object.InitData;
import object.Problem;

public class MSDARQ_EActivity extends MSDARQActivity {

	protected Problem startDA(Problem pro, boolean debug, MSDARQMatching m,
			final int C_STUDNTNUMBER, InitData data) {
		boolean first = true;
		int N_provisonalDA = 0;
		while (true) {
			if (first) {
				if (oneCycleDA(pro, debug, m, C_STUDNTNUMBER, startPoint,
						endPoint)) {
					break;
				}
				first = false;
			} else {
				try {
					Problem p = new Problem(data);

					boolean flag = oneCycleDA(pro, debug, m, C_STUDNTNUMBER, 0,
							endPoint);
					N_provisonalDA++;
					
					if (E0 < E1) {
						flag = oneCycleDA(pro, debug, m, C_STUDNTNUMBER,
								startPoint, endPoint);
						if (flag) {
							break;
						}
						continue;
					}

					if (flag) {
						break;
					}
					pro = p;

				} catch (Exception e) {// マッチングの失敗
					System.out.println("マッチング失敗");
				}
			}
		}
		//仮DAマッチング回数の保存
		pro.setProvisionalDANumber(N_provisonalDA);
		
		return pro;
	}
}