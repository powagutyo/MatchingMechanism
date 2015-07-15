package object;

public abstract class Obj {
	protected Obj[] m_KindSeat; // 好みのシート
	protected final int M_ID;//番号
	/**
	 * デフォルトコンストラクタ
	 */
	public Obj() {
		m_KindSeat = null;
		M_ID = -1;
	}

	public Obj(int kindSeatNum, int id) {
		this.m_KindSeat = new Obj[kindSeatNum];
		this.M_ID = id;
	}
	/**
	 * 希望シートに対して、与えられたオブジェクトを割り当てるメソッド
	 * @param objs
	 */
	public void initKindSeat(Obj[] objs) {
		int size = objs.length;
		for (int i = 0; i < size; i++) {
			m_KindSeat[i] = null;
			m_KindSeat[i] = objs[i];
		}
	}

	/**
	 * KindSeatの場所を変更するsetterメソッド
	 * 
	 * @param pos
	 *            場所
	 * @param obj
	 *            　変更するobj
	 */
	public void changeKindSeat(int pos, Obj obj) {
		m_KindSeat[pos] = null;
		m_KindSeat[pos] = obj;
	}

	/**
	 * 配属するメソッド
	 * 
	 * @param num
	 */
	public abstract void assign(Obj obj);

	/**
	 * 自分のKindSeatの表示
	 */
	public void displayKindSeat() {
		int size = m_KindSeat.length;
		for (int i = 0; i < size; i++) {
			if(m_KindSeat[i] != null){
				System.out.print(i + " : " + m_KindSeat[i].getID() + " ");			
			}
		}
		System.out.println();
	}

	/**
	 * 自分の所属をコンソール表示するメソッド
	 */
	public abstract void displayAssign();

	public Obj[] getKindSeat() {
		return m_KindSeat;
	}

	public int getID() {
		return M_ID;
	}

}
