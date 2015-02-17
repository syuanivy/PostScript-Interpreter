package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSDictionary;
import cs601.ps.objects.PSString;

/**
 * Created by ivy on 2/15/15.
 */
public class begin extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        PSDictionary dict = (PSDictionary)interpreter.operandStack.pop();
        interpreter.dictionaryStack.push(dict);
    }
}
