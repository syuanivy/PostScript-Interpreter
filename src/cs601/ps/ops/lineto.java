package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSNumber;
import cs601.ps.objects.PSString;

/**
 * Created by ivy on 2/15/15.
 */
public class lineto extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        float dy = ((PSNumber)interpreter.pop()).floatValue();
        float dx = ((PSNumber)interpreter.pop()).floatValue();
        interpreter.gstate.lineto((int)dx, (int)dy);
    }
}