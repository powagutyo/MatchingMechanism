package activity;

import java.util.ArrayList;

import dp.DP;
import matching.MSDARQMatching;
import matching.Matching;
import object.InitData;
import object.Problem;
import object.Region;

public class MSDARQ_E2Activity extends MSDARQ_EActivity {
	
	private int m_DP_result;
	private DP dp;
	
	public MSDARQ_E2Activity() {
		super();
		dp = new DP();
	}
	
	@Override
	public Problem matchingStart(InitData data, Matching matching, Problem pro) {
		boolean debug = data.M_DEBAG;

		MSDARQMatching m = (MSDARQMatching) matching;
		// DAマッチングの部分
		final int C_STUDNTNUMBER = pro.getData().M_STUDENT;
		E0 = C_STUDNTNUMBER;			
		E1 = pro.getParentRegion().getLowerLimit();
		
		startPoint = 0;
		endPoint = E0 - E1 + m_DP_result; 
		
		pro = startDA(pro, debug, m, C_STUDNTNUMBER, data);

		m.modeChange();

		pro = startSDRQ(pro, debug, m, C_STUDNTNUMBER);

		m.modeChange();

		return pro;
	}
	
	@Override
	public void preprocessingPretreatment(Problem pro, InitData data){
		ArrayList<Region> leafRegion = pro.getLeafNode(new ArrayList<Region>(),
				pro.getParentRegion());// 葉ノードを集める
		int[] lowerRegions = new int[leafRegion.size()];
		int[] upperRegions = new int[leafRegion.size()];
		
		for (int i = 0; i < leafRegion.size(); i++) {
			lowerRegions[i] = leafRegion.get(i).getLowerLimit();
			upperRegions[i] = leafRegion.get(i).getUpperLimit();
		}		
		final int C_STUDNTNUMBER = pro.getData().M_STUDENT;
		
		int num = C_STUDNTNUMBER;			
		int num1 = pro.getParentRegion().getLowerLimit();
		
		
		m_DP_result = dp.startDP(lowerRegions, upperRegions, (num - num1));
	}
	

}
