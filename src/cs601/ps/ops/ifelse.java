package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSBoolean;
import cs601.ps.objects.PSProcedure;

public class ifelse extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		PSProcedure elseClause = (PSProcedure)interpreter.pop();
		PSProcedure ifClause = (PSProcedure)interpreter.pop();
		PSBoolean cond = (PSBoolean)interpreter.pop();
		if ( cond.getJavaValue() ) {
			ifClause.execute(interpreter);
		}
		else {
			elseClause.execute(interpreter);
		}
	}
}
