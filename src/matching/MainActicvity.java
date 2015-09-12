package matching;

import object.InitData;

public class MainActicvity {
	public static void main(String[] args) {
		new MainActicvity().start();
	}

	public void start(){
		InitData data = new InitData();
		DAMatching da = new DAMatching(data);
		da.start();


	}
}
