package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSNumber;

public class scale extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		float sy = ((PSNumber)interpreter.pop()).floatValue();
		float sx = ((PSNumber)interpreter.pop()).floatValue();
		interpreter.gstate.scaleX = sx;
		interpreter.gstate.scaleY = sy;
	}
}
