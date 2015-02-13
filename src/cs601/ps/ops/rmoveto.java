package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSNumber;

public class rmoveto extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        float dy = ((PSNumber)interpreter.pop()).floatValue();
        float dx = ((PSNumber)interpreter.pop()).floatValue();
        interpreter.gstate.rmoveto((int)dx, (int)dy);
    }
}
