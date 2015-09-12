package object;

import java.util.ArrayList;

import javax.swing.text.AsyncBoxView.ChildState;

public class Region {

	private Region m_Parent; // 親の地域

	private ArrayList<Region> m_Children; // 子供の地域

	private ArrayList<Tree_School> m_schools; // 地域に所属している学校

	private int m_UpperLimit; // 上限

	private int m_LowerLimit;// 下限
	private int m_SurplusLimit; // 余剰枠
	
	private int m_FirstLowerLimit;
	private int m_FirstUpperLimit;

	public Region(Region parent, int ul, int ll, int sl) {
		m_Parent = parent;
		m_Children = new ArrayList<Region>();
		m_schools = new ArrayList<Tree_School>();

		m_UpperLimit = ul;
		m_LowerLimit = ll;
		m_SurplusLimit = sl;

	}

	/**
	 * 上限、下限、要素的下限の初期化を行うメソッド
	 */
	public void initLimitNumber() {
		int uNum = 0;
		int lNum = 0;

		if (m_Children.size() == 0) {// 葉ノード
			m_UpperLimit = m_schools.get(0).m_UpperLimit
					- m_schools.get(0).m_Students.size();
			m_LowerLimit = m_schools.get(0).m_LowerLimit
					- m_schools.get(0).m_Students.size() + m_SurplusLimit;
			if (m_UpperLimit < 0)
				m_UpperLimit = 0;
			if (m_LowerLimit < 0)
				m_LowerLimit = 0;
			m_SurplusLimit = m_LowerLimit;
			
			m_FirstLowerLimit = m_LowerLimit;
			m_FirstUpperLimit = m_UpperLimit;
			return;
		} else {
			for (Region re : getChildren()) {
				re.initLimitNumber();
				uNum += re.m_UpperLimit;
				lNum += re.m_LowerLimit;
			}
		}
		calcLimit(uNum, lNum);
		//Limitの更新
		m_FirstLowerLimit = m_LowerLimit;
		m_FirstUpperLimit = m_UpperLimit;
	
	}
	

	public void calcLimit(int uNum, int lNum) {
		if (m_UpperLimit == -1) {
			m_UpperLimit = uNum;
		} else {
			if (uNum < m_UpperLimit)
				m_UpperLimit = uNum;
		}
		if (lNum > m_LowerLimit) {
			m_LowerLimit = lNum + m_SurplusLimit;
		}
		
		m_SurplusLimit = m_LowerLimit - lNum;
	}

	/**
	 * 葉ノードRegionから更新を行うメソッド
	 */
	public void upDateLimitNumber() {
		m_UpperLimit--;
		if (m_Children.size() == 0) {// 葉ノード
			if (m_LowerLimit > 0)
				m_LowerLimit--;
			m_SurplusLimit = m_LowerLimit;
			m_Parent.upDateLimitNumber();
		} else {
			int num = 0;
			for (Region r : m_Children) {
				num += r.m_LowerLimit;
			}
			// 下限制約
			if (m_LowerLimit - 1 < num) {
				m_LowerLimit = num;
			} else {
				if (m_LowerLimit > 0)
					m_LowerLimit--;
			}

			m_SurplusLimit = m_LowerLimit - num;

			if (m_SurplusLimit < 0)
				m_SurplusLimit = 0;

			if (m_Parent != null)
				m_Parent.upDateLimitNumber();
		}
	}

	public boolean canAssain() {
		if (m_Parent != null) {
			if (m_SurplusLimit > 0) {
				return true;
			}
			return m_Parent.canAssain();
		}
		return false;
	}
	/**
	 * 自分の割り当てれている学校の数を返すメソッドｓ
	 * @return
	 */
	public int getAssainedStudent(){
		int num = 0;
		if(m_Children.size() == 0){
			for(School sch : m_schools){
				num = sch.getStudentsSize();
			}
			return num;
		}
		for(Region r : m_Children){
			num += r.getAssainedStudent();
		}
		return num;	
	}
	

	public int getUpperLimit() {
		return m_UpperLimit;
	}

	public int getLowerLimit() {
		return m_LowerLimit;
	}

	public int getSurplusLimit() {
		return m_SurplusLimit;
	}

	public void assainRegion(School sch) {
		m_schools.add((Tree_School) sch);
	}

	public void addChildren(Region child) {
		m_Children.add(child);
	}

	public Region getParent() {
		return m_Parent;
	}

	public void setParent(Region parent) {
		this.m_Parent = parent;
	}

	public ArrayList<Region> getChildren() {
		return m_Children;
	}

	public void setUpperLimit(int upperLimit) {
		this.m_UpperLimit = upperLimit;
	}

	public void setLowerLimit(int lowerLimit) {
		this.m_LowerLimit = lowerLimit;
	}

	public void setSurplusLimit(int surplusLimit) {
		this.m_SurplusLimit = surplusLimit;
	}
	public int getFirstLowerLimit() {
		return m_FirstLowerLimit;
	}

	public int getFirstUpperLimit() {
		return m_FirstUpperLimit;
	}

}
