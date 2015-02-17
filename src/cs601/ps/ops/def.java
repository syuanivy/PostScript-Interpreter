package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSObject;
import cs601.ps.objects.PSString;

/**
 * Created by ivy on 2/15/15.
 */
public class def  extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        PSObject value = interpreter.operandStack.pop();
        String key = interpreter.operandStack.pop().toString();
        interpreter.define(key, value);
    }
}