package cs601.ps.ops;

import cs601.ps.PSInterpreter;

/**
 * Created by ivy on 2/16/15.
 */
public class print extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        System.out.println(interpreter.pop().toString());
    }
}
