package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSNumber;

public class moveto extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        float y = ((PSNumber)interpreter.pop()).floatValue();
        float x = ((PSNumber)interpreter.pop()).floatValue();
        interpreter.gstate.moveto((int) x, (int)y);
    }
}
