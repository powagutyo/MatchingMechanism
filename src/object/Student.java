package object;

public class Student extends Obj {

	protected boolean m_IsAssign;// 学校に配属されているか
	protected int m_KindSeatPointer;// 自分の見た学校?(KindSeat)を記憶した番号
	protected School m_AssignedSchool; // 対応する学校 or生徒
	protected StudentState m_State;// 通常枠か拡張枠かの判定用の状態
	protected boolean m_AssignmentDecision;// 配属が決定されている場合
	/**
	 * コンストラクタ
	 * @param kindSeatNum
	 * @param id
	 * @param state
	 */
	public Student(int kindSeatNum, int id, StudentState state) {
		super(kindSeatNum, id);
		this.m_AssignedSchool = null;
		m_State = state;
		m_AssignmentDecision = false;
	}
	@Override
	public void assign(Obj obj) {
		this.m_IsAssign = true;
		if (m_State == StudentState.NORMAL || m_State == StudentState.EXTENSIONMODE)// 通常枠の時の判定
			kindSeatPointerPLus();
		this.m_AssignedSchool = (School) obj;
	}

	/**
	 * 学校側に拒否された時に実行するメソッド
	 */
	public void refused() {
		this.m_IsAssign = false;
		this.m_AssignedSchool = null;
	}

	@Override
	public void displayAssign() {
		System.out.print("所属している学校 : ");
		if (m_AssignedSchool != null)
			System.out.print(m_AssignedSchool.getID());
	}

	/**
	 * 満足度を返すメソッド
	 * @return 満足度
	 */
	public int getSatisfy() {
		int size = m_KindSeat.length;
		for (int i = 0; i < size; i++) {
			if (m_KindSeat[i] == m_AssignedSchool) {
				return i;
			}
		}
		return -1;
	}
	

	/**
	 *@param pos 割り当てられた学校の場所
	 */
	public boolean isAssignedSchoolPos(int pos) {
		if (m_KindSeat[pos] == m_AssignedSchool) {
			return true;
		}
		return false;
	}
	
	public void changeState() {
		switch (m_State) {
		case NORMALMODE:
			m_State = StudentState.EXTENSIONMODE;
			break;
		case EXTENSIONMODE:
			m_State = StudentState.NORMALMODE;	
			break;
		default:
			break;
		}			
	}
	
	public boolean isAssignmentDecision() {
		return m_AssignmentDecision;
	}
	public void changeAssignmentDecision() {
		m_AssignmentDecision = !m_AssignmentDecision;
	}
	
	/**
	 * KindSeatPointerのポイントに割り当てられている学校を返すメソッド
	 * @return
	 */
	public School getHopeSchool() {
		return (School) m_KindSeat[m_KindSeatPointer];
	}

	public void kindSeatPointerPLus() {
		this.m_KindSeatPointer++;
	}
	public StudentState getState() {
		return m_State;
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

	public School getAssignedSchool() {
		return m_AssignedSchool;
	}

}
