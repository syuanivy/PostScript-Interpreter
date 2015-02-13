package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSBoolean;
import cs601.ps.objects.PSProcedure;

public class _if extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		PSProcedure ifclause = (PSProcedure)interpreter.pop();
		PSBoolean cond = (PSBoolean)interpreter.pop();
		if ( cond.getJavaValue() ) {
			interpreter.execute(ifclause);
		}
	}
}
