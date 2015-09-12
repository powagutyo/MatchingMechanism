package object;

public class Tree_School extends School{

	//TODO もしかしたらもう一つ子クラスを作るかも？
		protected Region leafNode;// 葉ノードのregion

	public Tree_School(int kindSeatNum, int ul, int ll, int id) {
		super(kindSeatNum, ul, ll, id);
		leafNode = null;
	}
	
	public Region getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(Region leafNode) {
		this.leafNode = leafNode;
	}
}
