package matching;

import object.Problem;

public class MSDARQMatching extends TreeMatching {

	private Matching DA;
	private Matching SDRQ;
	public enum Mode{
		DA,
		SDRQ;
	}
	private Mode mode;
	
	public MSDARQMatching() {
		super();
		DA = new DAMatching();
		SDRQ = new SDRQMatching();
		mode = Mode.DA;
	}
	@Override
	public void studentSelect(Problem pro, int studentNum) {
		switch (mode) {
		case DA:
			DA.studentSelect(pro, studentNum);
			break;
		case SDRQ:
			SDRQ.studentSelect(pro, studentNum);
			break;
		default:
			break;
		}
	}
	@Override
	public void schoolSelect(Problem pro) {
		switch (mode) {
		case DA:
			DA.schoolSelect(pro);
			break;
		case SDRQ:
			SDRQ.schoolSelect(pro);
			break;
		default:
			break;
		}		
	}
	
	public void modeChange(){
		if(mode == Mode.DA){
			mode = Mode.SDRQ;
		}else{
			mode = Mode.DA;
		}
	}

}
