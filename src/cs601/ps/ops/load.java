package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSNameLiteral;
import cs601.ps.objects.PSObject;

/** Search dictionary stack for key and return associated value. */
public class load extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        PSNameLiteral id = (PSNameLiteral)interpreter.pop();
		PSObject value = interpreter.lookup(id.getJavaValue());
        //System.out.println("loading "+id+"="+value);
        interpreter.push(value);
    }
}
