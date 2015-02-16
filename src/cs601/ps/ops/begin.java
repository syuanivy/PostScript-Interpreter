package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSString;

/**
 * Created by ivy on 2/15/15.
 */
public class begin extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        interpreter.push(
                new PSString(interpreter.pop().toString())
        );
    }
}
