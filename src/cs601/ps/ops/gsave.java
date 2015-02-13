package cs601.ps.ops;


import cs601.ps.GraphicsState;
import cs601.ps.PSInterpreter;

public class gsave extends PSOperator {
	public void execute(PSInterpreter interpreter) {
		interpreter.gstateStack.push(interpreter.gstate);
		interpreter.gstate = new GraphicsState(interpreter.gstate);
	}
}
