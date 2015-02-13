package cs601.ps.ops;


import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSInt;
import cs601.ps.objects.PSObject;
import cs601.ps.objects.PSProcedure;

public class repeat extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        // get count procedure operands
        PSProcedure proc = (PSProcedure)interpreter.pop();
		PSInt count = (PSInt)interpreter.pop();
		for (int i=1; i<=count.intValue(); i++) {
			interpreter.execute(proc);
		}
	}
}
