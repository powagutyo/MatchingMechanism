package object;

public abstract class Obj {
	protected int[] m_KindSeat; // 好みのシート
		public Obj() {
		m_KindSeat = null;
	}

	public Obj(int kindSeatNum) {
		this.m_KindSeat = new int[kindSeatNum];
		for (int i = 0; i < kindSeatNum; i++) {
			m_KindSeat[i] = -1;
		}
	}
	/**
	 * KindSeatの場所を変更するsetterメソッド
	 * @param pos 場所
	 * @param number　番号
	 */

	public void changeKindSeat(int pos, int number){
		m_KindSeat[pos] = number;
	}
	/**
	 * 配属するメソッド
	 * @param num
	 */
	public abstract void assign(int num);


	/**
	 * 自分のKindSeatの表示
	 */
	public void displayKindSeat(){
		int size = m_KindSeat.length;
		for(int i= 0;i< size;i++ ){
			System.out.print(i+" : " + m_KindSeat[i] + " ");
		}
		System.out.println();
	}
	/**
	 * 自分の所属をコンソール表示するメソッド
	 */
	public abstract void displayAssign();


	public int[] getKindSeat() {
		return m_KindSeat;
	}



}
