package cs601.ps.ops;


import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSObject;
import cs601.ps.objects.PSString;

/** Convert top of stack to printable string */
public class cvs extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        interpreter.push(
            new PSString(interpreter.pop().toString())
        );
    }
}
