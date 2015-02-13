package cs601.ps.ops;

import cs601.ps.PSInterpreter;

public class stroke extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		interpreter.gstate.stroke();
	}
}
