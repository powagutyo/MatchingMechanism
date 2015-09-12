package object;

public class InitData {
	public final int M_STUDENT = 512 ; // 生徒の数 128
	public final int M_SCHOOL = 64;// 学校の数 21
	public final int M_UPPERLIMIT = 40;//学校の上限数
	public final int M_LOWERLIMIT = 0;// 学校の下限人数
	
	public int M_PARTLOWERLIMIT = 140; //要素的下限制約
	//TODO ここの部分の修正
	/*
	 * Regionを作成した際に要素的上限制約ではなく要素的が限制約をも
	 */
	public int M_UPPERLIMIT_FREESEAT = 40;
	
	public final double M_STUDENTBIAS = 0.6; //生徒の学校の選好順についての偏り 
		
	public final int M_HOPESTUDENTNUMBER = M_SCHOOL; // 生徒の選考リストの数
	public final int M_HOPESCHOOLNUMBER = M_STUDENT; //学校の選好リストの数
	
	public final boolean M_ISRANDOMSELECT_STUDENT = true;
	public final boolean M_ISRANDOMSELECT_SCHOOL = true;
	public final boolean M_ISRANDOMSELECT_MASTERLIST = false;// マスターリストをランダムで選択するかどうか
	
	/******************ファイル関係****************/
	public final String M_STUDENTDATANAME = "data/project.txt" ;
	public final String M_SCHOOLDATANAME = null;
	public final String M_OUTPUTFILENAME ="data/matching.xlsx";
	public final String M_SHEETNAME ="MSDARQ_E";
	public final String M_SHEETNAME_STUDENT =M_SHEETNAME + "_STUDENT";
	public final String M_SHEETNAME_SCHOOL =M_SHEETNAME + "_SCHOOL";
	
	/*************************************/
	
	public enum MATCHINGMODE {
		DA,
		ESDA,
		SDRQ,
		MSDARQ,
		MSDARQ_E,
		MSDARQ_E2;
	}
	
	public final MATCHINGMODE M_MATHINGMODE = MATCHINGMODE.MSDARQ_E;
	
	public long M_SEEDNUM = 0L; // 乱数のシード値
	
	public final boolean M_ONREPEAT = true; // 繰り返し行うかどうか
	public final int M_REPEATNUMBER = 100; //繰り返し回数
	
	public final boolean M_DEBAG = false; //デバッグモードの設定
	public final boolean M_SHOWRESULT = false;//結果を表示するかどうか
	public final boolean M_ISWRITEEXCEL = true; //excelファイルにデータ出力するかどうか
	


}
