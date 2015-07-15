package matching;

import object.InitData;
import object.Problem;

public class MainActicvity {
	public static void main(String[] args) {
		new MainActicvity().start();
	}

	public void start(){
		InitData data = new InitData();
		Matching Matching = new DAMatching();
		switch (data.M_MATHINGMODE) {
		case DA:
			Matching = new DAMatching();	
			break;
		case ESDA:
			Matching = new ESDAMatching();
			break;

		default:
			break;
		}
		Problem pro = new Problem(data);
		MatchingStart(data, Matching, pro);
	}

	private void MatchingStart(InitData data, Matching Matching, Problem pro) {
		int step = 1;
		boolean debug = data.DEBAG;
		boolean mistake = false;
		try{
			while (!Matching.checkAllStudentAssigned(pro)) {
				if (debug)
					System.out.println(step + "step目");
				
				for(int num : pro.getMasterList()){
					/*********** 生徒の学校選択 ******************/
					Matching.studentSelect(pro,num);
					/*********** 学校側の生徒選択 *******************/
					Matching.schoolSelect(pro);
					
				}
				if(debug){
					pro.displayResult();
				}
				step++;
			}		
		}catch(NullPointerException e){
			mistake = true;
			System.out.println("割り当ての失敗");
		}finally{
			if(!mistake){
				System.out.println("マッチング結果は");
				pro.displayResult();
				pro.displayStudentSatisfy();
				System.out.println(step + "step目");
				System.out.println("空きシートの要求数は" + Matching.getRequestFreeSeat(pro)); 
			}
		}
	}
	
}
