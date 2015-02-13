package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSObject;

public class exch extends PSOperator {
    public void execute(PSInterpreter interpreter) {
		PSObject o2 = interpreter.pop();
		PSObject o1 = interpreter.pop();
		interpreter.push(o2);
		interpreter.push(o1);
    }
}
