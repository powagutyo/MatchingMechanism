package matching;

import object.InitData;
import object.Problem;

public class MainActicvity {
	public static void main(String[] args) {
		new MainActicvity().start();
	}

	public void start(){
		InitData data = new InitData();
		DAMatching da = new DAMatching();
		Problem pro = new Problem(data);
		int step = 1;
		boolean debug = data.DEBAG;
		boolean mistake = false;
		try{
			while (!da.checkAllStudentAssigned(pro)) {
				if (debug)
					System.out.println(step + "step目");
				/*********** 生徒の学校選択 ******************/
				da.studentSelect(pro);
				/*********** 学校側の生徒選択 *******************/
				da.schoolSelect(pro);
				if(debug){
					pro.displayResult();
				}
				step++;
			}		
		}catch(IndexOutOfBoundsException e){
			mistake = true;
			System.out.println("割り当ての失敗");
		}finally{
			if(!mistake){
				System.out.println("マッチング結果は");
				pro.displayResult();
				pro.displayStudentSatisfy();
				System.out.println(step + "step目");
				
			}
		}
	}
}
