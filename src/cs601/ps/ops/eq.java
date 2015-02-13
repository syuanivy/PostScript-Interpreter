package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSBoolean;
import cs601.ps.objects.PSNumber;

public class eq extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		PSNumber right = (PSNumber)interpreter.pop();
		PSNumber left = (PSNumber)interpreter.pop();
		interpreter.push(new PSBoolean(left.getJavaValue().equals(right.getJavaValue())));
	}
}
