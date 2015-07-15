package object;

public class InitData {
	public final int M_STUDENT = 128 ; // 生徒の数
	public final int M_SCHOOL = 21;// 学校の数
	public final int M_UPPERLIMIT =10;//学校の上限数
	public final int M_LOWERLIMIT = 1;// 学校の下限人数

	public final String M_STUDENTDATANAME = "data/project.txt" ;
	public final String M_SCHOOLDATANAME = null;
	
	public enum MATCHINGMODE {
		DA,
		ESDA;
	}
	public final MATCHINGMODE M_MATHINGMODE = MATCHINGMODE.ESDA;
	
	public final long M_SEEDNUM = 1L; // 乱数のシード値

	public final boolean DEBAG = true; //デバッグモードの設定


}
