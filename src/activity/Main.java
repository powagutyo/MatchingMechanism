package activity;

import io.FileManager;
import matching.DAMatching;
import matching.ESDAMatching;
import matching.MSDARQMatching;
import matching.Matching;
import matching.SDRQMatching;
import object.InitData;
import object.Problem;

public class Main {
	public static void main(String[] args) {
		new Main().start();
	}

	public void start() {
		InitData data = new InitData();
		Matching matching = null;
		Activty activty = null;
		switch (data.M_MATHINGMODE) {
		case DA:
			matching = new DAMatching();
			activty = new DAActivity();
			break;
		case ESDA:
			matching = new ESDAMatching();
			activty = new ESDAActivity();
		case SDRQ:
			matching = new SDRQMatching();
			activty = new SDRQActivity();
			break;
		case MSDARQ:
			matching = new MSDARQMatching();
			activty = new MSDARQActivity();
			break;
		case MSDARQ_E:
			matching = new MSDARQMatching();
			activty = new MSDARQ_EActivity();
			break;
		case MSDARQ_E2:
			matching = new MSDARQMatching();
			activty = new MSDARQ_E2Activity();
			break;
		default:
			matching = new DAMatching();
			activty = new DAActivity();
			break;
		}
		/*
		if (data.M_ONREPEAT) {
			for (int i = 1; i <= data.M_REPEATNUMBER; i++) {
				Problem pro = null;
				data.M_SEEDNUM = (long) i;
				pro = new Problem(data);
				activty.matchingStart(data, matching, pro);
			}
		} else {
			Problem pro = new Problem(data);
			activty.matchingStart(data, matching, pro);
		}
		 */
		doMSDA(matching, activty, data);
	}

	public static void doMSDA(Matching m, Activty a, InitData data) {
		double[] result = new double[data.M_SCHOOL]; // 希望調査の数
		double[] result_school = new double[data.M_STUDENT]; // 希望調査の数
		
		double requestFreeSeat;// 空きシートの数
		double notSatisfyStudent;// 妥当な不満を持つ生徒
		double n_ProvisonalDA; //仮DAマッチングの回数

		if (data.M_ONREPEAT) {
			for (int i = 64; i < 448; i++) {// 要素的下限制約の数(論文参照)
				System.out.println(i);
				
				requestFreeSeat = 0;// 空きシートの数
				notSatisfyStudent = 0;// 妥当な不満を持つ生徒
				n_ProvisonalDA = 0;
				for (int j = 0; j < data.M_HOPESTUDENTNUMBER; j++) {
					result[j] = 0;
				}
				for (int j = 0; j < data.M_HOPESCHOOLNUMBER; j++) {
					result_school[j] = 0;
				}
				data.M_PARTLOWERLIMIT = i; // 要素的下限制約をset
				Problem pro  = new Problem(data);
				
				a.preprocessingPretreatment(pro, data);// ここで前処理を行うマッチング
				
				long time = System.currentTimeMillis();
				for (int j = 1; j <= data.M_REPEATNUMBER; j++) {
					pro = a.matchingStart(data, m, pro);
					pro = a.result(data, m, pro);
					requestFreeSeat += pro.getRequestFreeSeat();
					notSatisfyStudent += pro.getNotSatisfyStudent();
					n_ProvisonalDA += pro.getProvisionalDANumber();
					for (int l = 0; l < data.M_HOPESTUDENTNUMBER; l++) {
						result[l] += pro.getResultAssained()[l];
					}
					for (int l = 0; l < data.M_HOPESCHOOLNUMBER; l++) {
						result_school[l] += pro.getResultAssained_school()[l];
					}
					data.M_SEEDNUM = (long) j;
					pro = new Problem(data);
					
				}
				time =(System.currentTimeMillis() - time) / (long)(data.M_REPEATNUMBER);
				
				requestFreeSeat = requestFreeSeat / data.M_REPEATNUMBER;
				notSatisfyStudent = notSatisfyStudent / data.M_REPEATNUMBER;
				n_ProvisonalDA = n_ProvisonalDA / data.M_REPEATNUMBER;
				
				for (int l = 0; l < data.M_HOPESTUDENTNUMBER; l++) {
					result[l] = result[l] / data.M_REPEATNUMBER;
				}
				for (int l = 0; l < data.M_HOPESCHOOLNUMBER; l++) {
					result_school[l] = result_school[l] / data.M_REPEATNUMBER;
				}
				
				if(data.M_ISWRITEEXCEL){
					FileManager.excel_FileWrite_2(data, requestFreeSeat, notSatisfyStudent, result,time, n_ProvisonalDA);
					FileManager.excel_FileWrite_3(data, result_school);
				}
			}	
			

		} else {
			Problem pro = new Problem(data);
			a.preprocessingPretreatment(pro, data);
			pro = a.matchingStart(data, m, pro);
			pro = a.result(data, m, pro);
			
		}

	}

}
