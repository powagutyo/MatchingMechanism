package object;

public class Student extends Obj{

	private boolean m_IsAssign;//学校に配属されているか
	private int m_KindSeatPointer;//自分の見た学校?(KindSeat)を記憶した番号
	private int m_SchoolLocation; // 対応する学校 or生徒

	public Student(int kindSeatNum) {
		super(kindSeatNum);
		this.m_SchoolLocation = -1;
	}
	@Override
	/**
	 * 指定した学校に配属するメソッド
	 * @param schoolNum 学校番号
	 */
	public void assign(int schoolNum){
		this.m_IsAssign = true;
		kindSeatPointerPLus();
		this.m_SchoolLocation = schoolNum;
	}
	/**
	 * 学校側に拒否された時に実行するメソッド
	 */
	public void refused(){
		this.m_IsAssign = false;
		this.m_SchoolLocation = -1;

	}
	@Override
	public void displayAssign() {
		System.out.print("所属している学校 : " + m_SchoolLocation );
	}
	/**
	 * 満足度を返すメソッド
	 */
	public int getSatisfy(){
		int size = m_KindSeat.length;
		for(int i= 0;i< size;i++){
			if(m_KindSeat[i] == m_SchoolLocation){
				return i;
			}
		}
		return -1;
	}
	/**
	 * 満足度を返すメソッド
	 */
	public boolean isSatisfy(int pos){
		if(m_KindSeat[pos] == m_SchoolLocation){
			return true;
		}
		return false;
	}

	/**
	 * 学校に自分の出したところ以外で希望を出すメソッド
	 * @return
	 */
	public int putHopeInSchool(){
		return m_KindSeat[m_KindSeatPointer];
	}

	public void kindSeatPointerPLus(){
		this.m_KindSeatPointer++;
	}

	public boolean IsAssign() {
		return m_IsAssign;
	}

	public void setAssign(boolean m_IsAssain) {
		this.m_IsAssign = m_IsAssain;
	}
	public int getKindSeatPointer() {
		return m_KindSeatPointer;
	}

	public int getSchoolLocation() {
		return m_SchoolLocation;
	}




}
