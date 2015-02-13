package cs601.ps.ops;


import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSInt;
import cs601.ps.objects.PSProcedure;

public class forOp extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        // get start skip end procedure operands
        PSProcedure proc = (PSProcedure)interpreter.pop();
		PSInt stop = (PSInt)interpreter.pop();
		PSInt skip = (PSInt)interpreter.pop();
		PSInt start = (PSInt)interpreter.pop();
		int n = stop.intValue();
		for (int i=start.intValue(); i<=n; i+=skip.intValue()) {
			interpreter.push(new PSInt(i)); // pushing loop index but doesn't pop off automatically
			interpreter.execute(proc);
		}
	}
}
