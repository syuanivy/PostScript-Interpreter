package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSString;

/**
 * Created by ivy on 2/15/15.
 */
public class end extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        interpreter.dictionaryStack.pop();
    }
}