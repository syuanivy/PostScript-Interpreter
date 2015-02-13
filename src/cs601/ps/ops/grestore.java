package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSObject;

public class grestore extends PSOperator {
	public void execute(PSInterpreter interpreter) {
		interpreter.gstate = interpreter.gstateStack.pop();
	}
}
