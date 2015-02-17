package cs601.ps.ops;


import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSObject;

public class dup extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        interpreter.push( interpreter.peek() );
    }
}
